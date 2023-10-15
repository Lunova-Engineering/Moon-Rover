package com.lunova.moonbot.movies.logging;

import net.dv8tion.jda.api.events.Event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Log manager.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon -Rover
 * @since 10.13.2023
 */
public class LogManager {

    private static final Map<LogEvent, ArrayList<LogMessage>> LOG_EVENTS = new HashMap<>();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);


    /**
     * Initialize.
     */
    public static void initialize() {
        EXECUTOR_SERVICE.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                flushLogs();
            }
        }, 0, 30, TimeUnit.MINUTES);
    }

    /**
     * Submit log.
     *
     * @param logEvent the log event
     * @param event    the event
     */
    public static <T extends Event> void submitLog(LogEvent logEvent, T event) {
        if(!LOG_EVENTS.containsKey(logEvent))
            LOG_EVENTS.put(logEvent, new ArrayList<>());
        LogMessage message = logEvent.getStrategy().getLogMessage(event);
        LOG_EVENTS.get(logEvent).add(message);
    }

    /**
     * Flush logs.
     */
    public static void flushLogs() {
        for (List<LogMessage> logMessages : LOG_EVENTS.values()) {
            // Sort log messages based on file paths
            logMessages.sort(Comparator.comparing(log -> log.getFile().getPath()));

            // Write each log message to its associated file
            for (LogMessage logMessage : logMessages) {
                try {
                    // Ensure parent directories exist
                    Path parentDir = logMessage.getFile().toPath().getParent();
                    if (Files.notExists(parentDir)) {
                        Files.createDirectories(parentDir);
                    }

                    // Write to the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(logMessage.getFile(), true))) {
                        writer.write(logMessage.getMessage());
                        writer.newLine();  // To ensure messages are on separate lines
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
