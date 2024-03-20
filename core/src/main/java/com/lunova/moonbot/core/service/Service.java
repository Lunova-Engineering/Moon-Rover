package com.lunova.moonbot.core.service;

import com.lunova.moonbot.core.service.executors.PausableExecutor;
import com.lunova.moonbot.core.service.tasks.CallableServiceTask;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

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

/*    public Service(String name, boolean critical, T executor) {
        this.name = name;
        this.critical = critical;
        this.executor = executor;
        this.serviceState = ServiceState.NOT_STARTED;
    }*/

    protected final void shutdownNow() {
        onShutdownNow();
        executor.shutdownNow();
        this.serviceState = ServiceState.AWAITING_RESTART;
    }

    protected final void shutdown() {
        onShutdown();
        executor.shutdown();
        this.serviceState = ServiceState.AWAITING_RESTART;
    }

    public boolean isPaused() {
        return serviceState.equals(ServiceState.PAUSED) && executor.isPaused();
    }

    public void pause() {
        logger.info("Service for {} has been paused.", name);
        onPause();
        executor.pause();
        serviceState = ServiceState.PAUSED;
    }

    public void resume() {
        logger.info("Service for {} has been resumed", name);
        onResume();
        executor.resume();
        serviceState = ServiceState.RUNNING;
    }

    public void restart() {
        onRestart();
    }

    protected void execute(RunnableServiceTask task) {
        task.setSubmissionTime(System.currentTimeMillis());
        executor.execute(task);
    }

    protected void submit(RunnableServiceTask task) {
        task.setSubmissionTime(System.currentTimeMillis());
        executor.submit(task);
    }

    protected <V> Future<V> submit(CallableServiceTask<V> task) {
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

    protected void onShutdownNow() {
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
