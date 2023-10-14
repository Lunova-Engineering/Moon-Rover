package com.lunova.moonbot.movies.logging;

import net.dv8tion.jda.api.entities.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.13.2023
 */
public class LogManager {

    private static final Map<User, Map<LogEvent, ArrayList<LogMessage>>> LOG_MESSAGES = new HashMap<>();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    private static final EnumSet<LogEvent> EVENTS = EnumSet.allOf(LogEvent.class);

    private static final String BASE_DIR = "data" + File.separator + "logs";

    public static void initialize() {
        EXECUTOR_SERVICE.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                flushLogs();
            }
        }, 0, 30, TimeUnit.MINUTES);
    }

    public static void submitLog(LogEvent event, LogMessage message) {
        User user = message.getUser();

        if(!LOG_MESSAGES.containsKey(user))
             LOG_MESSAGES.put(user, new HashMap<>());
        if(!LOG_MESSAGES.get(user).containsKey(event))
            LOG_MESSAGES.get(user).put(event, new ArrayList<>());

        LOG_MESSAGES.get(user).get(event).add(message);
    }

    public static void flushLogs() {
        // Iterate through each user
        for (Map.Entry<User, Map<LogEvent, ArrayList<LogMessage>>> userEntry : LOG_MESSAGES.entrySet()) {
            User user = userEntry.getKey();
            Map<LogEvent, ArrayList<LogMessage>> userLogs = userEntry.getValue();

            // User directory
            String userDirName = String.format("%s (%s)", user.getName(), user.getId());
            File userDir = new File(BASE_DIR, userDirName);

            // Ensure the user directory exists
            if (!userDir.exists()) {
                userDir.mkdirs();
            }

            // Iterate through each log event for the user
            for (Map.Entry<LogEvent, ArrayList<LogMessage>> logEntry : userLogs.entrySet()) {
                LogEvent event = logEntry.getKey();
                ArrayList<LogMessage> messages = logEntry.getValue();

                // Create StringBuilder for all log messages for this event
                StringBuilder sb = new StringBuilder();

                for (LogMessage logMessage : messages) {
                    String formattedLog = String.format("[%s] [%s] %s%n",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(logMessage.getTime())),
                            event.name(),
                            logMessage.getMessage()
                    );
                    sb.append(formattedLog);
                }

                // Write to file
                File eventDir = new File(userDir, event.name());
                if (!eventDir.exists()) {
                    eventDir.mkdir();
                }

                File logFile = new File(eventDir, user.getName() + "_" + event.name() + "_log.txt");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                    writer.write(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
