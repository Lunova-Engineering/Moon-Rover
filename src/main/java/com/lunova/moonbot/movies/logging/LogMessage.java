package com.lunova.moonbot.movies.logging;

import java.io.File;

/**
 * The type Log message.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon -Rover
 * @since 10.13.2023
 */
public class LogMessage {

    private final File file;
    private final String message;

    /**
     * Instantiates a new Log message.
     *
     * @param file    the file
     * @param message the message
     */
    public LogMessage(File file, String message) {
        this.file = file;
        this.message = message;
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
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
