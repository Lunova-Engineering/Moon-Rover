package com.lunova.moonbot.exceptions;

public class CommandNotFoundException extends IllegalStateException {
    public CommandNotFoundException() {
        this("Command not found!");
    }

    public CommandNotFoundException(String message) {
        super(message);
    }
}
