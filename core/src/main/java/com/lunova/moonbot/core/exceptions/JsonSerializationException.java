package com.lunova.moonbot.core.exceptions;

public class JsonSerializationException extends Exception {

    public JsonSerializationException(String message) {
        super(message);
    }

    public JsonSerializationException(String message, Throwable t) {
        super(message, t);
    }
}
