package com.lunova.moonbot.core.services.plugin;

import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.exceptions.PluginRequestException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.moonbot.core.services.BotService;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import com.lunova.moonbot.core.services.plugin.loader.PluginLoader;
import com.lunova.moonbot.core.utility.json.JsonHandler;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static spark.Spark.port;
import static spark.Spark.post;

/**
 * Service responsible for managing plugins' lifecycle including loading, registering, and managing
 * plugins. It handles the network endpoints for plugin actions and ensures that plugins are
 * appropriately initialized and maintained.
 */
public class PluginService extends BotService {

  /** The singleton instance of the PluginService. */
  private static final PluginService INSTANCE = new PluginService("Plugin Loader Service");

  /** Logger for the PluginService class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(PluginService.class);

  /**
   * Private constructor for PluginService to enforce the Singleton pattern.
   *
   * @param serviceName The name of the service being created.
   */
  private PluginService(String serviceName) {
    super(serviceName);
  }

  /**
   * Provides access to the singleton instance of the PluginService.
   *
   * @return The singleton instance of the PluginService.
   */
  public static PluginService getInstance() {
    return INSTANCE;
  }

  /**
   * Initializes the PluginService, setting up the network endpoints for handling plugin actions and
   * configuring the plugins' environment.
   *
   * @throws ServiceLoadingException If the initialization of the service fails.
   */
  @Override
  protected void initialize() throws ServiceLoadingException {
    try {
      //int port = Integer.parseInt(BotConfiguration.getProperty("WEB_SERVER_PORT"));
      int port = 8080;
      //TODO: Add specific configuration exception to catch block.
      port(port);
      post("/plugin-action",
          (request, response) -> {

            try {
              if(request.body().isBlank()) {
                throw new PluginRequestException("Plugin request body is empty.");
              }

              //TODO: Continue verifying information flow path. Clean up code, transition to non hard coded values for testing.
              //TODO: Ensure information is quickly passed in and out of function to avoid blocking incoming request, consider using Queues.
              PluginRequest pluginRequest = JsonHandler.deserialize(request.body(), PluginRequest.class);
              //PluginResolver resolver = PluginResolverUtils.createDefaultResolver();
              //resolver.downloadArtifact(pluginRequest.pluginGroupId(), pluginRequest.pluginArtifactId(), pluginRequest.pluginVersion());
              Plugin plugin = PluginLoader.loadPlugin(new File("/Users/drakeforness/Documents/Github/Moon-Bot/plugins/default/target/moon-bot-plugin-default-0.2.0-SNAPSHOT.jar").toURI().toURL());
              plugin.executeInstallRoutine(MoonBotService.getInstance().getBotSession(), "993720567729492080");

              //Execute request
              //check for plugin in class path + cache
              //if not found submit download request
              //once jar is obtained load jar
              //continue executing request
              //install, unisntall, disable, enable, on correct server

            } catch(JsonDeserializationException e) {
              response.status(500);
              LOGGER.error(e.getMessage());
              LOGGER.debug(e.getMessage(), e);
            } catch (PluginRequestException | ConstraintViolationException e) {
              response.status(400);
              LOGGER.error(e.getMessage());
              LOGGER.debug(e.getMessage(), e);
            }

            return response;
          });
        } catch (Exception e) {
          LOGGER.error(e.getMessage(), e);
          throw new ServiceLoadingException("Failed to start web server while initializing plugin service.");
        }
      }
    }
