package com.lunova.moonbot.core.service.tasks.common;

import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceManager;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ServiceShutdownTask extends RunnableServiceTask {

    private static final Logger logger = LoggerFactory.getLogger(ServiceShutdownTask.class);

    private final long duration;

    private final TimeUnit timeUnit;

    private final boolean restart;

    public ServiceShutdownTask(Service<?> originator) {
        this(originator, Constants.SERVICE_TIMEOUT_DURATION, Constants.SERVICE_TIME_UNIT, false);
    }

    public ServiceShutdownTask(Service<?> originator, boolean restart) {
        this(originator, Constants.SERVICE_TIMEOUT_DURATION, Constants.SERVICE_TIME_UNIT, restart);
    }

    public ServiceShutdownTask(Service<?> originator, long duration, TimeUnit timeUnit) {
        this(originator, duration, timeUnit, false);
    }

    public ServiceShutdownTask(Service<?> originator, long duration, TimeUnit timeUnit, boolean restart) {
        this(TaskPriority.IMMEDIATE, originator, duration, timeUnit, restart);
    }


    private ServiceShutdownTask(TaskPriority taskPriority, Service<?> originator, long duration, TimeUnit timeUnit, boolean restart) {
        super(taskPriority, originator);
        this.duration = duration;
        this.timeUnit = timeUnit;
        this.restart = restart;
    }

    @Override
    protected void onRun() {
        //TODO: If restart is true add logic to initiate restart of service by submitting a task for registering the service again which will re-initialize the service.
        if(getOriginator().getExecutor().isShutdown())
            return;
        try {
            logger.info("Attempting graceful shutdown of {}", getOriginator().getName());
            getOriginator().shutdown();
            boolean shutdown = getOriginator().getExecutor().awaitTermination(duration, timeUnit);
            if(!shutdown) {
                logger.warn("{} did not complete graceful shutdown within allotted time of {} {}. Executing forceful shutdown.", getOriginator().getName(), duration, timeUnit.name());
                getOriginator().shutdownNow();
            } else if(getOriginator().getExecutor().isTerminated()) {
                logger.info("Successfully completed graceful shutdown of {}!", getOriginator().getName());
                ServiceManager.deregister(getOriginator().getClass());
            } else {
                logger.warn("Encountered unexpected termination state for {}. Executor reports successful termination via awaitTermination but has false value for isTerminated. Executing forceful shutdown.", getOriginator().getName());
                getOriginator().getExecutor().shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("{} has been interrupted during graceful shutdown. Executing forceful shut down of service.", getOriginator().getName());
            Thread.currentThread().interrupt();
            getOriginator().shutdownNow();
        } catch (Exception e) {
            logger.error("{} failed to gracefully shutdown due to exception during shutdown process. Executing forceful shutdown.", getOriginator().getName(), e);
            logger.debug("Stack trace for {} failure due to exception: {}", getOriginator().getName(), e.getLocalizedMessage(), e);
            getOriginator().shutdownNow();
        }
    }

    public boolean needsRestart() {
        return restart;
    }

}
