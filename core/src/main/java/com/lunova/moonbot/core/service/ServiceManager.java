package com.lunova.moonbot.core.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.executors.DefaultUncaughtExceptionHandler;
import com.lunova.moonbot.core.service.executors.ExecutorBuilder;
import com.lunova.moonbot.core.service.executors.PausableExecutor;
import com.lunova.moonbot.core.service.executors.ServiceInfo;
import com.lunova.moonbot.core.service.tasks.common.ServiceShutdownTask;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private static final Map<Class<? extends Service<?>>, Service<? extends PausableExecutor>> services = new HashMap<>();

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler()).setNameFormat("Service Manager").build());

    private static boolean initialized = false;

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

    public static <T extends PausableExecutor> void initialize() {
        Reflections reflections = new Reflections(ServiceManager.class.getPackageName());
        Set<Class<Service<T>>> serviceClasses = reflections.getTypesAnnotatedWith(ServiceInfo.class)
                .stream()
                .filter(Service.class::isAssignableFrom)
                .map(service -> (Class<Service<T>>) service)
                .collect(Collectors.toSet());;

        serviceClasses.forEach(service -> {
                ServiceInfo serviceInfo = service.getAnnotation(ServiceInfo.class);

                try {

                    //TODO: Use an Executor builder wrapper class to hold the info and build when requested
                    PausableExecutor serviceExecutor = ExecutorBuilder.buildExecutor(service);
                    Service<T> instance = service.getConstructor(String.class, boolean.class, serviceExecutor.getClass())
                                            .newInstance(serviceInfo.name(), serviceInfo.critical(), serviceExecutor);


                    Optional.ofNullable(instance.initialize()).ifPresent(task -> {
                        try {
                            Future<?> future = instance.submit(task);
                            future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    register(service, instance);
                    instance.setServiceState(ServiceState.RUNNING);
                    logger.info("Registered {} - State: {}", instance.getName(), instance.getServiceState());
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    logger.error("Error while registering service. Name: {}, Critical: {}.", serviceInfo.name(), serviceInfo.critical(), e);
                    throw new RuntimeException(e);
                }
        });
        initialized = true;
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
