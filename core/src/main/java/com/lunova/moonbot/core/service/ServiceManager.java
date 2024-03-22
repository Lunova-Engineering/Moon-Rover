package com.lunova.moonbot.core.service;

import com.lunova.moonbot.core.service.files.FileService;
import com.lunova.moonbot.core.service.files.WriteFileTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;

public class ServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private static final Map<Class<? extends Service<?>>, Service<? extends ExecutorService>> services = new HashMap<>();

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void register(Class<? extends Service<? extends ExecutorService>> serviceClass, Service<?> service) {
        services.put(serviceClass, service);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Service<?>> T getService(Class<T> serviceClass) {
         return (T) services.get(serviceClass);
    }

    public static Collection<Service<?>> getServices() {
        return services.values();
    }

    public static void initialize() {
        Runtime.getRuntime().addShutdownHook(new ServiceShutdownHook());

        Reflections reflections = new Reflections("com.lunova.moonbot.core.service");
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(ServiceInfo.class);

        services.forEach(service -> {

            if (Service.class.isAssignableFrom(service)) {

                Class<? extends Service<? extends ThreadPoolExecutor>> casted = (Class<? extends Service<? extends ThreadPoolExecutor>>) service;
                ServiceInfo serviceInfo = service.getAnnotation(ServiceInfo.class);

                try {
                    Constructor<?> constructor = service.getConstructor(String.class, boolean.class);
                    Service<?> instance = (Service<?>) constructor.newInstance(serviceInfo.name(), serviceInfo.critical());
                    if(!FileService.class.isAssignableFrom(instance.getClass()))
                        return;;
                    register(casted, instance);
                    logger.debug("Registered " + instance.getName());
                    Optional.ofNullable(instance.initialize()).ifPresent(instance::execute);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                FileService fs = getService(FileService.class);
                Random r = new Random();
                for (int i = 0; i < 100; i++) {
                    fs.submit(new WriteFileTask(TaskPriority.values()[r.nextInt(TaskPriority.values().length)], fs, "This is a normal task..."));
                }
                if(fs.isPaused())
                    fs.resume();
                else
                    fs.pause();
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
/*        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                FileService fs = getService(FileService.class);
            }
        }, 0, 500, TimeUnit.MILLISECONDS);*/
       /* WriteFileTask task = new WriteFileTask(TaskPriority.NORMAL, getService(FileService.class), "This is a normal task...");
        for (int i = 0; i < 100; i++) {
            logger.debug("Submitted task number {}", i);
            getService(FileService.class).submit(task);
        }*/

    }

}
