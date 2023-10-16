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
public class LogManager extends Thread {

    private static final Map<LogEventType, ArrayList<LogMessage>> LOG_MESSAGES = new ConcurrentHashMap<>();

    private static final Queue<LogEvent> LOG_EVENTS = new ConcurrentLinkedQueue<>();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);


    /**
     * This method is run by the thread when it executes. Subclasses of {@code
     * Thread} may override this method.
     *
     * <p> This method is not intended to be invoked directly. If this thread is a
     * platform thread created with a {@link Runnable} task then invoking this method
     * will invoke the task's {@code run} method. If this thread is a virtual thread
     * then invoking this method directly does nothing.
     *
     * @implSpec The default implementation executes the {@link Runnable} task that
     * the {@code Thread} was created with. If the thread was created without a task
     * then this method does nothing.
     */
    @Override
    public void run() {
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
     * @param logEventType the log event
     * @param event    the event
     */
    public static <T extends Event> void submitLog(LogEventType logEventType, T event) {

        LOG_EVENTS.add(new LogEvent(logEventType, event));

        //LOG_MESSAGES.computeIfAbsent(logEventType, k -> new ArrayList<>());
        //LOG_MESSAGES.get(logEventType).add(logEventType.getStrategy().getLogMessage(event));
    }

    private static void processLogEvents() {
        while(LOG_EVENTS.poll() != null) {
            LOG_EVENTS.
        }
    }

    /**
     * Flush logs.
     */
    public static void flushLogs() {
        for (List<LogMessage> logMessages : LOG_MESSAGES.values()) {
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
