package com.lunova.moonbot.core.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.executors.DefaultUncaughtExceptionHandler;
import com.lunova.moonbot.core.service.executors.PausableExecutor;
import com.lunova.moonbot.core.service.files.FileFormat;
import com.lunova.moonbot.core.service.files.FileService;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import com.lunova.moonbot.core.service.tasks.common.ServiceShutdownTask;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class ServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private static final Map<Class<? extends Service<?>>, Service<? extends PausableExecutor>> services = new HashMap<>();

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler()).setNameFormat("Service Manager").build());

    public static void register(Class<? extends Service<? extends ExecutorService>> serviceClass, Service<?> service) {
        services.put(serviceClass, service);
    }

    public static <T extends Service<?>> void deregister(Class<T> serviceClass) {
        services.remove(serviceClass);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Service<?>> T getService(Class<T> serviceClass) {
         return (T) services.get(serviceClass);
    }

    public static Collection<Service<?>> getServices() {
        return services.values();
    }

    public static void initialize() {
        Reflections reflections = new Reflections(ServiceManager.class.getPackageName());
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(ServiceInfo.class);

        serviceClasses.forEach(service -> {

            if (Service.class.isAssignableFrom(service)) {

                Class<? extends Service<? extends ThreadPoolExecutor>> casted = (Class<? extends Service<? extends ThreadPoolExecutor>>) service;
                ServiceInfo serviceInfo = service.getAnnotation(ServiceInfo.class);

                try {
                    Constructor<?> constructor = service.getConstructor(String.class, boolean.class);
                    Service<?> instance = (Service<?>) constructor.newInstance(serviceInfo.name(), serviceInfo.critical());
                    register(casted, instance);
                    Optional.ofNullable(instance.initialize()).ifPresent(task -> {
                        try {
                            Future<?> future = instance.submit(task);
                            future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    logger.info("Registered " + instance.getName());
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    logger.error("Error while registering service. Name: {}, Critical: {}.", serviceInfo.name(), serviceInfo.critical(), e);
                    throw new RuntimeException(e);
                }
            }
        });
        Path p = FileService.TESTING;
        FileService fs = getService(FileService.class);
        Person person1 = new Person("John Doe", 30, true, 55000.00, Arrays.asList("Java", "Spring"));
        Person person2 = new Person("Jane Smith", 28, true, 60000.00, Arrays.asList("JavaScript", "React"));
        Person person3 = new Person("Alice Johnson", 35, false, 0, Arrays.asList("Management", "Sales"));
        Person person4 = new Person("Bob Brown", 40, true, 65000.00, Arrays.asList("Python", "Django", "Flask"));
        Person person5 = new Person("Charlie Davis", 25, true, 50000.00, Arrays.asList("C++", "C#", "Unity"));
        List<Person> people = new ArrayList<>(5);
        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);
        people.add(person5);

       people.forEach(person -> {
            try {
                fs.writeFileWithResult(p, person.getName(), FileFormat.JSON, person).get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error writing file", e);
                throw new RuntimeException(e);
            }
        });
        List<Future<Person>> newPeople = new ArrayList<>(5);
        people.forEach(person -> {
            newPeople.add(fs.readFile(TaskPriority.IMMEDIATE, p, person.getName(), FileFormat.JSON, Person.class));
        });

        newPeople.forEach(person -> {
            try {
                logger.info("Person: {}", person.get().getName());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error reading file", e);
                throw new RuntimeException(e);
            }
        });

    }

    public static void shutdownService(Class<? extends Service<?>> service) {
        if(services.containsKey(service)) {
            shutdownService(services.get(service));
        } else {
            logger.warn("No key for service with class name {} exists.", service.getName());
        }
    }

    private static void shutdownService(Service<?> service) {
        executor.execute(new ServiceShutdownTask(service));
    }


    public static boolean shutdownAllServices() {
        if(!services.isEmpty())
            services.values().forEach(ServiceManager::shutdownService);
        executor.shutdown();
        try {
            return executor.awaitTermination(Constants.SERVICE_TIMEOUT_DURATION, Constants.SERVICE_TIME_UNIT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
