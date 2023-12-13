package com.lunova.moonbot.core.services;

public enum ServiceState {
    NOT_INITIALIZED,
    INITIALIZING,
    INITIALIZED,
    INITIALIZATION_FAILED,
    SHUTTING_DOWN, //TODO: Future state for services that encounter a fatal error and need to shut down
    SHUT_DOWN;  //TODO: Future state for a service that has shut down and needs to be restarted.
}
