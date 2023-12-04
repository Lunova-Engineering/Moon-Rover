package com.lunova.moonbot.core.services;

import com.lunova.moonbot.core.exceptions.ServiceLoadingFailedException;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class ServiceManager {


    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);


    public static void initialize() {
        loadService(MoonBotService.getInstance());
    }

    private static void loadService(BotService botService) {
        try {
            LOGGER.info("Initializing " + botService.getServiceName() + ".");
            botService.initialize();
            LOGGER.info("Successfully initialized " + botService.getServiceName() + "!");
        } catch(ServiceLoadingFailedException exception) {
            LOGGER.error(exception.getMessage(), exception);
            if(exception.isFatal()) {
                System.exit(0);
            }
        }
    }
}
