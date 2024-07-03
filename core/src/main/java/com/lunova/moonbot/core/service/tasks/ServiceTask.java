package com.lunova.moonbot.core.service.tasks;

import com.lunova.moonbot.core.service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class ServiceTask implements PriorityTask {
    // Write two implementations for runnable and callable service tasks to hold common meta data
    // and
    // logs and whanot

    private static final Logger logger = LoggerFactory.getLogger(ServiceTask.class);

    private static final AtomicInteger taskCounter = new AtomicInteger(1);

    private final TaskPriority taskPriority;

    private final Service<?> originator;

    private TaskState taskState;

    private long submissionTime;

    private long startTime;

    private long completeTime;

    private final int taskId;

    public ServiceTask(TaskPriority taskPriority, Service<?> originator) {
        this.taskPriority = taskPriority;
        this.originator = originator;
        this.taskId = taskCounter.getAndIncrement();
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public Service<?> getOriginator() {
        return originator;
    }

    public long getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(long submissionTime) {
        this.submissionTime = submissionTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public int getPriority() {
        return taskPriority.ordinal();
    }
}
