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

    private final long time;
    private final User user;
    private final String message;

    /**
     * Instantiates a new Log message.
     *
     * @param time    the time
     * @param user    the user
     * @param message the message
     */
    public LogMessage(long time, User user, String message) {
        this.time = time;
        this.user = user;
        this.message = message;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public long getTime() {
        return time;
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
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
