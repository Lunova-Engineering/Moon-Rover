package com.lunova.moonbot.core;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lunova.moonbot.core.api.plugin.examples.Item;
import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.FeatureSerializer;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroupSerializer;
import com.lunova.moonbot.core.exceptions.JsonSerializationException;
import com.lunova.moonbot.core.services.ServiceManager;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import com.lunova.moonbot.core.utility.json.JsonHandler;
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
/*    MoonBotService.getInstance()
        .getBotSession()
        .getGuilds()
        .forEach(
            guild ->
                guild.retrieveCommands().complete().forEach(command -> command.delete().queue()));*/
    Item item = new Item("Item Feature");
    SimpleModule module = new SimpleModule();
    module.addSerializer(Feature.class, new FeatureSerializer());
    module.addSerializer(SettingGroup.class, new SettingGroupSerializer());
    JsonHandler.registerModule(module);
      try {
          String json = JsonHandler.serialize(item);
        System.out.println(item.getSettingGroup().get().getSettings().stream().findFirst().get().getInput().getType().getDataType().getClazz());
        System.out.println(item.getSettingGroup().get().getSettings().stream().findFirst().get().getReturnType().getType().toString());
        System.out.println(json);
      } catch (JsonSerializationException e) {
          throw new RuntimeException(e);
      }
  }

}
