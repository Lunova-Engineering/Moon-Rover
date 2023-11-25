package com.lunova.moonbot.core;

import com.lunova.moonbot.core.exceptions.PropertyNotFoundException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the MoonBot Discord bot.
 *
 * <p>This class initializes and runs the Discord bot using the JDA library. It configures the bot
 * based on properties loaded by {@link BotConfiguration}.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class MoonBot {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MoonBot.class);

  /** JDA instance representing the Discord bot. */
  private static JDA instance;

  /**
   * The entry point of the application.
   *
   * <p>This method sets the main thread's name and initializes the Discord bot.
   *
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    Thread.currentThread().setName("Moon-Bot Main");
    instance = constructBot();
  }

  /**
   * Constructs and returns a JDA instance for the Discord bot.
   *
   * <p>This method retrieves the bot's authorization token from the configuration and uses it to
   * build the JDA instance. If the token is not found, it logs an error and shuts down the
   * application.
   *
   * @return The constructed JDA instance, or null if an error occurs.
   */
  private static JDA constructBot() {
    try {
      return JDABuilder.createDefault(BotConfiguration.getProperty("AUTH_TOKEN")).build();
    } catch (PropertyNotFoundException e) {
      LOGGER.error(e.getMessage(), e);
      LOGGER.error("Unable to locate bot authorization token. shutting down!");
      System.exit(0);
      return null;
    }
  }
}
