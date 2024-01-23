package com.lunova.moonbot.core.exceptions;

public class JsonDeserializationException extends Exception {

    public JsonDeserializationException(String message) {
        super(message);
    }

    public JsonDeserializationException(String message, Throwable t) {
        super(message, t);
    }

}
