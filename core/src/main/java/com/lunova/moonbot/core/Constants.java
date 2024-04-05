package com.lunova.moonbot.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public final class Constants {

    public static final long SERVICE_TIMEOUT_DURATION = 5;

    public static final TimeUnit SERVICE_TIME_UNIT = TimeUnit.MINUTES;

    public static final Path CORE_DATA_DIR = Paths.get(System.getProperty("user.dir"), "core", "data");
}
