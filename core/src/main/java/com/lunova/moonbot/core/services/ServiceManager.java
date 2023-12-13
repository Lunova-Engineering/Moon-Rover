package com.lunova.moonbot.core.services;

import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class ServiceManager {


    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);


    public static void initializeServices() {
        loadService(MoonBotService.getInstance());
    }

    private static void loadService(BotService service) {
        try {
            service.setServiceState(ServiceState.INITIALIZING);
            LOGGER.info("Initializing " + service.getServiceName() + ".");
            service.initialize();

            service.setServiceState(ServiceState.INITIALIZED);
            LOGGER.info("Successfully initialized " + service.getServiceName() + "!");
        } catch(ServiceLoadingException exception) {
            service.setServiceState(ServiceState.INITIALIZATION_FAILED);
            LOGGER.error(exception.getMessage(), exception);
            if(exception.isFatal()) {
                System.exit(0);
            }
        }
    }
}
