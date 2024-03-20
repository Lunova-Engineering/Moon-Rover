package com.lunova.moonbot.core.service.bot;

import com.lunova.moonbot.core.BotConfiguration;
import com.lunova.moonbot.core.event.EventDispatcher;
import com.lunova.moonbot.core.exceptions.ConfigurationException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import com.lunova.moonbot.core.servold.BotService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Singleton service responsible for managing the Discord bot session via the JDA library. This
 * service handles the initialization and settings of the bot session, including setting up
 * event listeners and enabling gateway intents for Discord events.
 *
 * <p>It extends {@link BotService}, providing specific functionality for the bot's operation within
 * the Discord environment.
 */
@ServiceInfo(name = "Moon Bot Service", critical = true)
public class MoonBotService extends Service<ServiceExecutor> {

  private static final Logger logger = LoggerFactory.getLogger(MoonBotService.class);



  /** The JDA session representing the active Discord bot connection. */
  private JDA botSession;

  /**
   * Protected constructor for MoonBotService. Ensures singleton pattern by limiting instantiation
   * to within the class.
   *
   * @param serviceName The name of the service.
   * @param critical Flag indicating whether the service is critical.
   */
  public MoonBotService(String serviceName, boolean critical) {
    super(serviceName, critical);
  }


  /**
   * Returns the current JDA session for the Discord bot.
   *
   * @return The JDA session.
   */
  public JDA getBotSession() {
    return botSession;
  }

  /**
   * Initializes the bot by creating and configuring the JDA session for the Discord bot. This
   * method uses the AUTH_TOKEN property from the BotConfiguration class to authenticate the bot
   * with Discord, enables gateway intents, adds event listeners, and awaits the ready state of the
   * bot session.
   *
   * @throws ServiceLoadingException If there is an error during the initialization process, such as
   *     misconfiguration or interrupted execution.
   */
  @Override
  public RunnableServiceTask initialize() {
    return new RunnableServiceTask(TaskPriority.IMMEDIATE, this) {
      @Override
      protected void onRun() {
        try {
          botSession =
                  JDABuilder.createDefault(BotConfiguration.getProperty("AUTH_TOKEN"))
                          .enableIntents(EnumSet.allOf(GatewayIntent.class))
                          .addEventListeners(new EventDispatcher())
                          .build()
                          .awaitReady();
        } catch (ConfigurationException | InterruptedException e) {
          //getLogger().error(e.getMessage(), e);
          //throw new ServiceLoadingException(
          //  "Failed to load Moon Bot Service. Shutting down.", isCritical());
        }
      }
    };
  }

  @Override
  protected ServiceExecutor createExecutor() {
    return new ServiceExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
  }

}
