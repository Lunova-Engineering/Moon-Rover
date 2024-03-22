package com.lunova.moonbot.core;

import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.service.ServiceManager;
import com.lunova.moonbot.core.service.bot.MoonBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the MoonBot Discord bot. Initializes and manages the bot's operations using the
 * JDA library. Responsible for configuring the bot, initializing servold, and cleaning up previous
 * session commands. Utilizes {} for service handling and {@link MoonBotService}
 * for bot session management.
 *
 * <p>Upon start, sets the main thread's name, initializes core servold, and manages bot session
 * and guild commands.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class MoonBot {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MoonBot.class);

  /**
   * Initializes the bot application. Sets the main thread's name, initializes servold, and manages
   * bot session. Clears previous session's commands for a fresh start. Errors during initialization
   * and runtime are logged.
   *
   * @param args The command-line arguments.
   */
  public static void main(String[] args) throws InterruptedException {
    Thread.currentThread().setName("Moon-Bot Main");
    ServiceManager.initialize();
    Thread.sleep(60000);
    System.exit(9);
/*    MoonBotService.getInstance()
        .getBotSession()
        .getGuilds()
        .forEach(
            guild ->
                guild.retrieveCommands().complete().forEach(command -> command.delete().queue()));*/
/*    Item item = new Item("Item Feature");
    Plugin base = new BasePlugin();
    base.getFeatureManager().registerFeature(item);
    base.getFeatureManager().seal();
      try {
          String json = JsonHandler.serialize(base);
          String uuids = JsonHandler.serialize(base.getFeatureManager().getFeatureMap());
          Setting<?> name = base.getFeatureManager().getFeatures().stream().findFirst().get().getSettingGroup().get().getSettings().stream().findFirst().get();
         // foo(base, "Minecraft");
        //System.out.println(name);
        System.out.println(json);
        System.out.println("\n\n\n\n");
        System.out.println(uuids);
        FileService.getInstance().writeFile(uuids, Paths.get(System.getProperty("user.dir"), "data", File.separator, "plugins", File.separator, "example", File.separator, "settings", "uuid.json"));
      } catch (JsonSerializationException e) {
          throw new RuntimeException(e);
      }*/
  }

  public static void foo(Plugin base, Object res) throws ClassNotFoundException {
    Class<?> output = Class.forName("net.dv8tion.jda.api.entities.Role"); //Object.getOutputClassName(); can also do assignment during JSON deserialization
    DataType dataType = DataType.STRING; // Object.getDataType();
    Class<?> roleClass = Class.forName("net.dv8tion.jda.api.entities.Role");
    System.out.println(roleClass.descriptorString());
    //here is the key for the plugin and container and feauter and setting to find it in the map or whatever in the server registry;
    //lets say we found it Plugin plugin = getPluginFromKey(); getFeatureManager(); getFeatures() or getFeature(FEATURE_KEY); getSetting(SETTING_KEY);
    getT(base.getFeatureManager().getFeatures().stream().findFirst().get().getSettingGroup().get().getSettings().stream().findFirst().get(), dataType, res, output);

    //after deserializing the web response (or during serialization or loading of a plugin?)


  }

  public static void doT(Setting<?> setting, Object obj) throws ClassNotFoundException {
  }

  private static <I, O> void getT(Setting<?> setting, DataType dataType, I response, Class<O> output) {
    Setting<O> castedSetting = (Setting<O>) setting;
    Input<I, O> castedInput = (Input<I, O>) castedSetting.getInput();
    Transformation<I, O> trans = castedInput.getTransformation().get();
    O returnO = trans.transform(response);
    trans.processTransformation(returnO);
  }

}
