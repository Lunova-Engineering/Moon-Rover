package com.lunova.moonbot.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lunova.moonbot.core.api.plugin.examples.Item;
import com.lunova.moonbot.core.services.ServiceManager;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the MoonBot Discord bot. Initializes and manages the bot's operations using the
 * JDA library. Responsible for configuring the bot, initializing services, and cleaning up previous
 * session commands. Utilizes {@link ServiceManager} for service handling and {@link MoonBotService}
 * for bot session management.
 *
 * <p>Upon start, sets the main thread's name, initializes core services, and manages bot session
 * and guild commands.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class MoonBot {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MoonBot.class);

  /**
   * Initializes the bot application. Sets the main thread's name, initializes services, and manages
   * bot session. Clears previous session's commands for a fresh start. Errors during initialization
   * and runtime are logged.
   *
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    Thread.currentThread().setName("Moon-Bot Main");
    ServiceManager.initializeServices();
    MoonBotService.getInstance()
        .getBotSession()
        .getGuilds()
        .forEach(
            guild ->
                guild.retrieveCommands().complete().forEach(command -> command.delete().queue()));
    Item item = new Item();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(item.defineSettingGroup());
    //System.out.println(item.defineSettingGroup().getOptions().stream().findAny().get().getInputDefinition().getInputDefinitionType().toString());
    System.out.println(json);
  }
}
