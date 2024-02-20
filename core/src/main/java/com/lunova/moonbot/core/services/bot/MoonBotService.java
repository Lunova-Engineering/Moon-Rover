package com.lunova.moonbot.core.services.bot;

import com.lunova.moonbot.core.BotConfiguration;
import com.lunova.moonbot.core.event.EventDispatcher;
import com.lunova.moonbot.core.exceptions.ConfigurationException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.services.BotService;
import java.util.EnumSet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * Singleton service responsible for managing the Discord bot session via the JDA library. This
 * service handles the initialization and settings of the bot session, including setting up
 * event listeners and enabling gateway intents for Discord events.
 *
 * <p>It extends {@link BotService}, providing specific functionality for the bot's operation within
 * the Discord environment.
 */
public class MoonBotService extends BotService {

  /** Singleton instance of MoonBotService. */
  private static MoonBotService instance;

  /** The JDA session representing the active Discord bot connection. */
  private JDA botSession;

  /**
   * Protected constructor for MoonBotService. Ensures singleton pattern by limiting instantiation
   * to within the class.
   *
   * @param serviceName The name of the service.
   * @param critical Flag indicating whether the service is critical.
   */
  protected MoonBotService(String serviceName, boolean critical) {
    super(serviceName, critical);
  }

  /**
   * Provides the singleton instance of the MoonBotService. If the instance does not exist, it is
   * created.
   *
   * @return The singleton instance of MoonBotService.
   */
  public static MoonBotService getInstance() {
    if (instance == null) {
      instance = new MoonBotService("Moon Bot Service", true);
    }
    return instance;
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
  public void initialize() throws ServiceLoadingException {
    try {
      botSession =
          JDABuilder.createDefault(BotConfiguration.getProperty("AUTH_TOKEN"))
              .enableIntents(EnumSet.allOf(GatewayIntent.class))
              .addEventListeners(new EventDispatcher())
              .build()
              .awaitReady();
    } catch (ConfigurationException | InterruptedException e) {
      getLogger().error(e.getMessage(), e);
      throw new ServiceLoadingException(
          "Failed to load Moon Bot Service. Shutting down.", isCritical());
    }
  }
}
