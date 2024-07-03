package com.lunova.moonbot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class Constants {

    private static final Logger logger = LoggerFactory.getLogger(Constants.class);

    public static final long SERVICE_TIMEOUT_DURATION = 5;

    public static final TimeUnit SERVICE_TIME_UNIT = TimeUnit.MINUTES;

    public static final Path CORE_DATA_PATH =
            Paths.get(System.getProperty("user.dir"), "core", "data");

    public static final Path PLUGIN_PATH = Paths.get(CORE_DATA_PATH.toString(), "plugins");

    public static final Path SERVICE_PATH = Paths.get(CORE_DATA_PATH.toString(), "services");

    public static final Path LOGS_PATH = Paths.get(CORE_DATA_PATH.toString(), "logs");

    public static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();
}
