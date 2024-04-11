package com.lunova.moonbot.core.service.bot;

import com.lunova.moonbot.core.BotConfiguration;
import com.lunova.moonbot.core.event.EventDispatcher;
import com.lunova.moonbot.core.exceptions.ConfigurationException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.executors.ExecutorConfig;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ServiceInfo;
import com.lunova.moonbot.core.service.executors.ThreadFactoryConfig;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;


@ServiceInfo(name = "Moon Bot Service", critical = true)
@ExecutorConfig(corePoolSize = 0, maximumPoolSize = 1, keepAliveTime = 0, unit = TimeUnit.NANOSECONDS, daemon = false, priority = Thread.NORM_PRIORITY)
@ThreadFactoryConfig(nameFormat = "Plugin-Service", daemon = false, priority = Thread.NORM_PRIORITY)
public class MoonBotService extends Service<ServiceExecutor> {

  private static final Logger logger = LoggerFactory.getLogger(MoonBotService.class);



  /** The JDA session representing the active Discord bot connection. */
  private JDA botSession;

  public MoonBotService(String name, boolean critical, ServiceExecutor executor) {
    super(name, critical, executor);
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
            logger.error("Failed to start Moon-Bot service due to an {}.", e.getClass().getSimpleName(), e);
        }
      }
    };
  }


  @Override
  protected void onShutdown() {
    botSession.shutdown();
    super.onShutdown();
  }

}
