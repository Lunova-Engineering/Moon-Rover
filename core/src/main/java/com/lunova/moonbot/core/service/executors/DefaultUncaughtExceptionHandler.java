package com.lunova.moonbot.core.service.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.warn("Uncaught exception in " + t.getThreadGroup().getName() + " - " + t.getName() + ".", e);
    }

}
