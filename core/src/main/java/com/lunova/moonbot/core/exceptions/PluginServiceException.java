package com.lunova.moonbot.core.exceptions;

/**
 * Custom exception for signaling issues specific to the plugin service operations. This exception
 * is used within the plugin system to indicate problems related to plugin management, loading,
 * installing, or other service-level issues. It provides constructors to specify a message and
 * optionally chain a cause for detailed error tracking.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class PluginServiceException extends Exception {

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized,
     * and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *     {@link #getMessage()} method.
     */
    public PluginServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically
     * incorporated in this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link
     *     #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()}
     *     method). (A {@code null} value is permitted, and indicates that the cause is nonexistent
     *     or unknown.)
     * @since 1.4
     */
    public PluginServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
