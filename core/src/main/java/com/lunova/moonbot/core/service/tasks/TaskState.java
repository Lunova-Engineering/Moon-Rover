package com.lunova.moonbot.core.service.tasks;

public enum TaskState {
    QUEUED,
    RUNNING,
    COMPLETED,
    COMPLETED_WITH_ERRORS,
    FAILED;
}
