package com.lunova.moonbot.movies.logging;

import net.dv8tion.jda.api.entities.User;

/**
 * The type Log message.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon -Rover
 * @since 10.13.2023
 */
public class LogMessage {

    private final User user;
    private final LogEvent logEvent;
    private final String message;

    /**
     * Instantiates a new Log message.
     *
     * @param user     the user
     * @param logEvent the log event
     * @param message  the message
     */
    public LogMessage(User user, LogEvent logEvent, String message) {
        this.user = user;
        this.logEvent = logEvent;
        this.message = message;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets log event.
     *
     * @return the log event
     */
    public LogEvent getLogEvent() {
        return logEvent;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
