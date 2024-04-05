package com.lunova.moonbot.core.service;

import com.lunova.moonbot.core.service.executors.PausableExecutor;
import com.lunova.moonbot.core.service.tasks.CallableServiceTask;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.ServiceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public abstract class Service<T extends PausableExecutor> {

    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    private final String name;

    private final boolean critical;

    private final T executor;

    private ServiceState serviceState;

    public Service(String name, boolean critical) {
        this.name = name;
        this.critical = critical;
        this.executor = createExecutor();
        this.serviceState = ServiceState.NOT_STARTED;
    }

    public final void shutdownNow() {
        List<ServiceTask> cancelledTasks = executor.shutdownNow().stream()
                .filter(runnable -> runnable instanceof ServiceTask)
                .map(runnable -> (ServiceTask) runnable)
                .toList();
        onShutdownNow(cancelledTasks);
        this.serviceState = ServiceState.SHUTTING_DOWN;
    }

    public final void shutdown() {
        executor.shutdown();
        onShutdown();
        if (isPaused())
            resume();
        this.serviceState = ServiceState.SHUTTING_DOWN;
    }

    public final boolean isPaused() {
        return serviceState.equals(ServiceState.PAUSED) && executor.isPaused();
    }

    public final void pause() {
        executor.pause();
        onPause();
        serviceState = ServiceState.PAUSED;
        logger.info("Service for {} has been paused.", name);
    }

    public final void resume() {
        executor.resume();
        onResume();
        serviceState = ServiceState.RUNNING;
        logger.info("Service for {} has been resumed", name);
    }

    public final void restart() {
        onRestart();
    }

    protected final void execute(RunnableServiceTask task) {
        task.setSubmissionTime(System.currentTimeMillis());
        try {
            executor.execute(task);
        } catch (RejectedExecutionException e) {
            logger.warn("Exception while submitting task with id {} to {}.", task.getTaskId(), this.name, e);
        } catch (NullPointerException e) {
            logger.warn("Exception while submitting task to {}. Task is null.", this.getName(), e);
        }
    }


    protected final Future<?> submit(RunnableServiceTask task) throws RejectedExecutionException, NullPointerException {
        task.setSubmissionTime(System.currentTimeMillis());
        return executor.submit(task);
    }

    protected final <V> Future<V> submit(CallableServiceTask<V> task) throws RejectedExecutionException, NullPointerException {
        task.setSubmissionTime(System.currentTimeMillis());
        return executor.submit(task);
    }

    protected void onPause() {
        //empty method for hook to pausing method
    }

    protected void onResume() {
        //empty method for hook to resuming method
    }

    protected void onRestart() {
        //empty method for hook to restart
    }

    protected void onShutdown() {
        //empty method for hook to shut down
    }

    protected void onShutdownNow(List<ServiceTask> cancelledTasks) {
        //empty method for hook to immediate shutdown
    }

    protected RunnableServiceTask initialize() {
        return null;
    }

    protected abstract T createExecutor();

    public T getExecutor() {
        return executor;
    }

    public String getName() {
        return name;
    }

    public boolean isCritical() {
        return critical;
    }

    public ServiceState getServiceState() {
        return serviceState;
    }

    public void setServiceState(ServiceState serviceState) {
        this.serviceState = serviceState;
    }

}
