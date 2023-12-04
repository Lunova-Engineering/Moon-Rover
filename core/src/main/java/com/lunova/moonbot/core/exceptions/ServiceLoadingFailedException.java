package com.lunova.moonbot.core.exceptions;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class ServiceLoadingFailedException extends Exception {

    private final boolean fatal;

    public ServiceLoadingFailedException(String message) {
        this(message, false);
    }

    public ServiceLoadingFailedException(String message, boolean fatal) {
        super(message);
        this.fatal = fatal;
    }

    public boolean isFatal() {
        return fatal;
    }

}
