package com.lunova.moonbot.core;

import com.lunova.moonbot.core.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHook extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    @Override
    public void run() {
        Thread.currentThread().setName("Shutdown-Hook-Thread");
        logger.error("Unexpected shutdown the JVM has occurred. Invoking Shutdown Hook procedure.");
        ServiceManager.shutdownAllServices();
        logger.info("Successfully completed Shutdown Hook procedures.");
    }

}
