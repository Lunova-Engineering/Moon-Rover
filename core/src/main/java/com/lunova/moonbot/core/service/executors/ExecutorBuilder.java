package com.lunova.moonbot.core.service.executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecutorBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorBuilder.class);

    public static <T extends PausableExecutor> PausableExecutor buildExecutor(Class<Service<T>> serviceClass) {
        if (!serviceClass.isAnnotationPresent(ExecutorConfig.class) && !serviceClass.isAnnotationPresent(ThreadFactoryConfig.class)) {
            return new ServiceExecutor(0, 1, 0, TimeUnit.NANOSECONDS, new PriorityBlockingQueue<>(), Executors.defaultThreadFactory());
        }
        ThreadFactory threadFactory = buildThreadFactory(serviceClass);

        return buildExecutor(serviceClass, threadFactory);
    }

    private static <T extends PausableExecutor> ThreadFactory buildThreadFactory(Class<Service<T>> serviceClass) {
        if(serviceClass.isAnnotationPresent(ThreadFactoryConfig.class)) {
            ThreadFactoryConfig factoryConfig = serviceClass.getAnnotation(ThreadFactoryConfig.class);
            return new ThreadFactoryBuilder()
                    .setNameFormat(factoryConfig.nameFormat())
                    .setPriority(factoryConfig.priority())
                    .setDaemon(factoryConfig.daemon())
                    .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler())
                    .build();
        }
        return Executors.defaultThreadFactory();
    }

    private static <T extends PausableExecutor> PausableExecutor buildExecutor(Class<Service<T>> serviceClass, ThreadFactory threadFactory) {
        if(serviceClass.isAnnotationPresent(ExecutorConfig.class)) {
            ExecutorConfig config = serviceClass.getAnnotation(ExecutorConfig.class);
            return config.scheduled() ? new ScheduledServiceExecutor(config.corePoolSize(), threadFactory) : new ServiceExecutor(config.corePoolSize(), config.maximumPoolSize(), config.keepAliveTime(), config.unit(), new PausablePriorityBlockingQueue<>(), threadFactory);
        }
        return new ServiceExecutor(0, 1, 0, TimeUnit.NANOSECONDS, new PriorityBlockingQueue<>(), threadFactory);
    }

}
