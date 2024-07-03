package com.lunova.moonbot.core.service.tasks;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.FutureTask;

public class PriorityFutureTask<V> extends FutureTask<V> implements PriorityTask {

    private static final Logger logger = LoggerFactory.getLogger(PriorityFutureTask.class);

    private final ServiceTask task;

    public PriorityFutureTask(@NotNull CallableServiceTask<V> callable) {
        super(callable);
        task = callable;
    }

    public PriorityFutureTask(@NotNull RunnableServiceTask runnable, V result) {
        super(runnable, result);
        task = runnable;
    }

    public ServiceTask getTask() {
        return task;
    }

    @Override
    public int getPriority() {
        return task.getPriority();
    }
}
