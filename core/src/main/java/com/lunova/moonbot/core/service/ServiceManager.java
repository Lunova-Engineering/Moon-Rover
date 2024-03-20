package com.lunova.moonbot.core.service;

import com.lunova.moonbot.core.service.files.FileService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

        //Register all servold marked with @ServiceInfo and initialize them
        services.put(FileService.class, new FileService("File Service", false));

        Reflections reflections = new Reflections("com.lunova.moonbot.core.service");
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(ServiceInfo.class);
        services.forEach(service -> {
            if (Service.class.isAssignableFrom(service)) {
                Class<? extends Service<? extends ThreadPoolExecutor>> casted = (Class<? extends Service<? extends ThreadPoolExecutor>>) service;
                ServiceInfo serviceInfo = service.getAnnotation(ServiceInfo.class);
                try {
                    Constructor<?> constructor = service.getConstructor(String.class, boolean.class);
                    Service<?> instance = (Service<?>) constructor.newInstance(serviceInfo.name(), serviceInfo.critical());
                    register(casted, instance);
                    logger.debug("Registered " + instance.getName());
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        //start a new scheduled executor checking service health every xxxx TimeUnit, loops through servold and checks thread status and thread state
        executor.scheduleWithFixedDelay(() -> getService(FileService.class).writeFile("I am a task running in file servold!"), 5, 5, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(() -> {
            Service<?> service =  getService(FileService.class);
            if(service.isPaused())
                service.resume();
            else
                service.pause();
        }, 7, 7, TimeUnit.SECONDS);
    }

}
