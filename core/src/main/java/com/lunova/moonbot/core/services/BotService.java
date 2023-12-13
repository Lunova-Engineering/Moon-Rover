package com.lunova.moonbot.core.services;


import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import net.dv8tion.jda.internal.requests.FunctionalCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public abstract class BotService {

    public static final Logger LOGGER = LoggerFactory.getLogger(BotService.class);

    private ServiceState serviceState = ServiceState.NOT_INITIALIZED;
    private final String serviceName;
    private final boolean critical;

    protected BotService(String serviceName) {
        this(serviceName, false);
    }

    protected BotService(String serviceName, boolean critical) {
        this.serviceName = serviceName;
        this.critical = critical;
    }

    public String getServiceName() {
        return serviceName;
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

    protected abstract void initialize() throws ServiceLoadingException;

    public void getServiceShutdownHook() {
        getLogger().info("Gracefully shutting down " + serviceName);
    }
    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
