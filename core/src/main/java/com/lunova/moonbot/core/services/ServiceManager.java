package com.lunova.moonbot.core.services;

import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import com.lunova.moonbot.core.services.files.FileService;
import com.lunova.moonbot.core.services.plugin.PluginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the initialization and state of various services in the bot application. This class is
 * responsible for orchestrating the startup sequence of core services, such as the MoonBotService
 * and PluginService, and handling any errors that occur during the initialization phase.
 *
 * <p>It utilizes a static initialization pattern to manage the lifecycle of services and ensure
 * they are ready before the bot becomes operational. The manager handles logging and error
 * reporting for each service it attempts to initialize.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class ServiceManager {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

  /**
   * Initializes all necessary services for the bot. This is typically called at the application
   * start-up and will sequentially initialize each service, logging the progress and any errors
   * encountered.
   */
  public static void initializeServices() {
    loadService(MoonBotService.getInstance());
    loadService(PluginService.getInstance());
    loadService(FileService.getInstance());
  }

  /**
   * Handles the initialization of a single BotService. It sets the service state, calls the
   * initialize method of the service, and updates the state based on the outcome. Errors during
   * initialization are logged and may lead to application termination if deemed fatal.
   *
   * @param service The BotService to load.
   */
  private static void loadService(BotService service) {
    try {
      service.setServiceState(ServiceState.INITIALIZING);
      LOGGER.info("Initializing " + service.getServiceName() + ".");
      service.initialize();

      service.setServiceState(ServiceState.INITIALIZED);
      LOGGER.info("Successfully initialized " + service.getServiceName() + "!");
    } catch (ServiceLoadingException exception) {
      service.setServiceState(ServiceState.INITIALIZATION_FAILED);
      LOGGER.error(exception.getMessage(), exception);
      if (exception.isFatal()) {
        System.exit(0);
      }
    }
  }
}
