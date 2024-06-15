package com.lunova.moonbot.core.service.executors;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class DefaultUncaughtExceptionHandler
        implements Thread.UncaughtExceptionHandler, RejectedExecutionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(DefaultUncaughtExceptionHandler.class);

    private final Service<?> service;

    public DefaultUncaughtExceptionHandler() {
        super();
        this.service = null;
    }

    public DefaultUncaughtExceptionHandler(Service<?> service) {
        super();
        this.service = service;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.error("Task {} rejected from {}", r.toString(), executor.toString());
    }

    public Optional<Service<?>> getService() {
        return Optional.fromNullable(service);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!getService().isPresent()
                || (getService().isPresent()
                        && getService().get().getServiceState().equals(ServiceState.SHUTTING_DOWN)))
            return;
        logger.warn(
                "Uncaught exception in " + t.getThreadGroup().getName() + " - " + t.getName() + ".",
                e);
        logger.error(e.getMessage(), e);
        assert service != null;
        service.pause();
        getService()
                .toJavaUtil()
                .ifPresent(
                        service -> {
                            // TODO: logic here to register the service as requiring restart or if
                            // fatal shutdown
                            // i guess
                            service.shutdown();
                            logger.error(
                                    "Shutting down {} due to uncaught error: {}",
                                    service.getName(),
                                    e.getLocalizedMessage(),
                                    e);
                        });
    }
}
