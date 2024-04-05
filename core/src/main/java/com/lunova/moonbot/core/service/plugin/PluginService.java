package com.lunova.moonbot.core.service.plugin;

import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.exceptions.PluginRequestException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.plugin.resolver.PluginResolver;
import com.lunova.moonbot.core.service.plugin.resolver.PluginResolverUtils;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import com.lunova.moonbot.core.service.files.json.JsonHandler;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

/**
 * Service responsible for managing plugins' lifecycle including loading, registering, and managing
 * plugins. It handles the network endpoints for plugin actions and ensures that plugins are
 * appropriately initialized and maintained.
 */
@ServiceInfo(name = "Plugin Service", critical = true)
public class PluginService extends Service<ServiceExecutor> {


  /** Logger for the PluginService class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(PluginService.class);

  public PluginService(String name, boolean critical) {
    super(name, critical);
  }

  /**
   * Initializes the PluginService, setting up the network endpoints for handling plugin actions and
   * configuring the plugins' environment.
   *
   * @throws ServiceLoadingException If the initialization of the service fails.
   */
  @Override
  protected RunnableServiceTask initialize() {
    return new RunnableServiceTask(TaskPriority.IMMEDIATE, this) {
      @Override
      protected void onRun() {
            try {
              //int port = Integer.parseInt(BotConfiguration.getProperty("WEB_SERVER_PORT"));
              int port = 8080;
              //TODO: Add specific settings exception to catch block.
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
                      PluginResolver resolver = PluginResolverUtils.createDefaultResolver();
                      resolver.downloadArtifact(pluginRequest.pluginGroupId(), pluginRequest.pluginArtifactId(), pluginRequest.pluginVersion());

                      //TODO: need to refactor plugin installation routine and registry after downloading due to massive API changes.
                      //Plugin plugin = PluginLoader.loadPlugin(new File("/Users/drakeforness/Documents/Github/Moon-Bot/plugins/default/target/moon-bot-plugin-default-0.2.0-SNAPSHOT.jar").toURI().toURL());
                      //plugin.executeInstallRoutine(MoonBotService.getInstance().getBotSession(), "993720567729492080");

                      //Execute request
                      //check for plugin in class path + cache
                      //if not found submit download request
                      //once jar is obtained load jar
                      //continue executing request
                      //install, uninstall, disable, enable, on correct server

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
                  //throw new ServiceLoadingException("Failed to start web server while initializing plugin service.");
                }
          }
        };
      }

  @Override
  protected void onShutdown() {
    stop();
    super.onShutdown();
  }

  @Override
  protected ServiceExecutor createExecutor() {
    return new ServiceExecutor(1, 1, Integer.MAX_VALUE, TimeUnit.MINUTES, new PriorityBlockingQueue<>());
  }

}
