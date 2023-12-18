package com.lunova.moonbot.core.exceptions;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class PluginException extends Exception {

    public PluginException(String message, Exception e) {
        super(message, e);
    }
}
