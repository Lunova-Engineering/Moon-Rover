package com.lunova.moonbot.core.service.tasks;

import com.lunova.moonbot.core.service.Service;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServiceTask implements Comparable<TaskPriority> {
//Write two implementations for runnable and callable service tasks to hold common meta data and logs and whanot

    private static final Logger logger = LoggerFactory.getLogger(ServiceTask.class);

    private final TaskPriority taskPriority;

    private final Service<?> originator;

    private TaskState taskState;

    private long submissionTime;

    private long startTime;

    private long completeTime;


    public ServiceTask(TaskPriority taskPriority, Service<?> originator) {
        this.taskPriority = taskPriority;
        this.originator = originator;
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

    @Override
    public int compareTo(@NotNull TaskPriority o) {
        return Integer.compare(o.ordinal(), taskPriority.ordinal());
    }

}
