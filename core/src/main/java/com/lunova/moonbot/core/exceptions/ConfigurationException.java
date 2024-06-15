package com.lunova.moonbot.core.exceptions;

/**
 * Custom exception thrown when a required property is not found in the settings.
 *
 * <p>This exception is used to indicate that a key expected in the bot settings is missing. It
 * extends {@link Exception}, making it a checked exception that must be either caught or declared
 * in the method signature.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class ConfigurationException extends Exception {

    /** Constructs a {@code PropertyNotFoundException} with a default message. */
    public ConfigurationException() {
        this("Property not found.");
    }

    /**
     * Constructs a {@code PropertyNotFoundException} with the specified detail message.
     *
     * <p>The detail message is saved for later retrieval by the {@link Throwable#getMessage()}
     * method.
     *
     * @param message the detail message which provides more information about the reason for the
     *     exception
     */
    public ConfigurationException(String message) {
        super(message);
    }
}
