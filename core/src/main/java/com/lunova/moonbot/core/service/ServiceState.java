package com.lunova.moonbot.core.service;

public enum ServiceState {
    NOT_STARTED,
    RUNNING,
    PAUSED,
    SHUTTING_DOWN,
    AWAITING_RESTART;
}
