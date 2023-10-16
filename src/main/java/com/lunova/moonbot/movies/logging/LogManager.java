package com.lunova.moonbot.movies.logging;

import net.dv8tion.jda.api.events.Event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

/**
 * The type Log manager.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon -Rover
 * @since 10.13.2023
 */
public class LogManager {

    private static final Map<LogEventType, ArrayList<LogMessage>> LOG_MESSAGES = new ConcurrentHashMap<>();

    private static final Queue<LogEvent> LOG_EVENTS = new ConcurrentLinkedQueue<>();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    /**
     * Initializes the log manager by setting up scheduled tasks to process and flush logs.
     * Additionally, ensures that any remaining logs are processed and flushed when the JVM
     * shuts down, and the executor service is shut down gracefully.
     */
    public static void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            processLogEvents();
            flushLogs();
            EXECUTOR_SERVICE.shutdown();
            try {
                if (!EXECUTOR_SERVICE.awaitTermination(5, TimeUnit.MINUTES)) {
                    EXECUTOR_SERVICE.shutdownNow();
                }
            } catch (InterruptedException e) {
                EXECUTOR_SERVICE.shutdownNow();
            }
        }));
        EXECUTOR_SERVICE.scheduleWithFixedDelay(LogManager::processLogEvents, 0, 15, TimeUnit.SECONDS);
        EXECUTOR_SERVICE.scheduleWithFixedDelay(LogManager::flushLogs, 0, 30, TimeUnit.MINUTES);
    }


    /**
     * Submits a new log event for processing.
     *
     * @param <T>          The type of event extending from {@link Event}
     * @param logEventType The type of log event to be processed.
     * @param event        The actual event data to be logged.
     */
    public static <T extends Event> void submitLog(LogEventType logEventType, T event) {
        LOG_EVENTS.add(new LogEvent(logEventType, event));
    }

    /**
     * Flushes all accumulated log messages to their associated files on disk.
     * This method ensures each log message is written to the appropriate file.
     * Any log messages that have been successfully written are removed from the accumulated log messages.
     */
    public static void flushLogs() {
        for (List<LogMessage> logMessages : LOG_MESSAGES.values()) {
            logMessages.sort(Comparator.comparing(log -> log.getFile().getPath()));

            Iterator<LogMessage> iterator = logMessages.iterator();
            while (iterator.hasNext()) {
                LogMessage logMessage = iterator.next();
                try {
                    Path parentDir = logMessage.getFile().toPath().getParent();
                    if (Files.notExists(parentDir)) {
                        Files.createDirectories(parentDir);
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(logMessage.getFile(), true))) {
                        writer.write(logMessage.getMessage());
                        writer.newLine();
                    }
                    iterator.remove();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Processes all queued log events and creates log messages from them.
     * These log messages are grouped by their log event type and stored for flushing to disk.
     */
    private static void processLogEvents() {
        LogEvent event;
        while ((event = LOG_EVENTS.poll()) != null) {
            LogMessage message = event.getLogEventType().getStrategy().getLogMessage(event.getEvent());
            LOG_MESSAGES.computeIfAbsent(event.getLogEventType(), k -> new ArrayList<>());
            LOG_MESSAGES.get(event.getLogEventType()).add(message);
        }
    }


}
