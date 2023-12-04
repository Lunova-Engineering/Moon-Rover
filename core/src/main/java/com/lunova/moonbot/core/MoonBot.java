package com.lunova.moonbot.core;

import com.lunova.moonbot.core.services.ServiceManager;
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


  /**
   * The entry point of the application.
   *
   * <p>This method sets the main thread's name and initializes the Discord bot.
   *
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    Thread.currentThread().setName("Moon-Bot Main");
    ServiceManager.initialize();
  }

}
