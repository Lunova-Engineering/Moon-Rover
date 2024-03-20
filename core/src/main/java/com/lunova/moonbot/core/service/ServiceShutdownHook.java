package com.lunova.moonbot.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ServiceShutdownHook extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ServiceShutdownHook.class);

    @Override
    public void run() {
        Thread.currentThread().setName("Shutdown-Hook-Thread");
        logger.warn("Unexpected shutdown the JVM has occured. Invoking Service Shutdown Hook procedure.");

        ServiceManager.getServices().forEach(service -> {
            try {
                logger.info("Attempting graceful shutdown of " + service.getName());
                service.shutdown();
            } catch (Exception e) {
                logger.error("Error while attempting to gracefully shutdown " + service.getName() + ". Initiating forceful shutdown for service.");
                service.shutdownNow();
            }
        });

        ServiceManager.getServices().forEach(service -> {
            try {
                if(!service.getExecutor().awaitTermination(15, TimeUnit.SECONDS)) {
                    logger.error(service.getName() + " did not complete graceful shutdown within the minimum time. Initiating forceful shutdown of service.");
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                logger.error(service.getName() + " has been interrupted. Executing forceful shut down of service.");
                Thread.currentThread().interrupt();
                service.shutdownNow();
            }
        });

        logger.info("Successfully completed Service Shutdown Hook procedures.");
    }

}
