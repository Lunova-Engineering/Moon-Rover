package com.lunova.moonbot.movies.logging;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.13.2023
 */
public class LogManager {

    private static final Map<User, Map<LogEvent, ArrayList<LogMessage>>> LOG_MESSAGES = new HashMap<>();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    private static final EnumSet<LogEvent> EVENTS = EnumSet.allOf(LogEvent.class);

    public static void submitLog(LogMessage message) {
        User user = message.getUser();
        LogEvent event = message.getLogEvent();

        if(!LOG_MESSAGES.containsKey(user))
             LOG_MESSAGES.put(user, new HashMap<>());
        if(!LOG_MESSAGES.get(user).containsKey(event))
            LOG_MESSAGES.get(user).put(event, new ArrayList<>());

        LOG_MESSAGES.get(user).get(event).add(message);
    }

}
