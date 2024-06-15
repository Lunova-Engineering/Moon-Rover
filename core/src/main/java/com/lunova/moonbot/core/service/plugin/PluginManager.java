package com.lunova.moonbot.core.service.plugin;

import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;

/**
 * Manages the lifecycle and association of plugins to guilds within the bot system. It provides
 * functionalities to register and deregister plugins for specific guilds and maintains a global
 * list of all plugins and a map associating them with the guilds.
 *
 * <p>Plugins are identified and managed based on their name and version. The manager ensures that
 * plugins are appropriately added or removed from guilds, maintaining the integrity of the plugin
 * ecosystem within the bot.
 */
public class PluginManager {

    // The following is ugly test code from the new settings API, it works but i can't exactly
    // explain
    // it without
    // looking more into how it works at the API level due to lack of actively working on this
    public static void foo(Plugin base, Object res) throws ClassNotFoundException {
        Class<?> output =
                Class.forName(
                        "net.dv8tion.jda.api.entities.Role"); // Object.getOutputClassName(); can
        // also do
        // assignment during JSON deserialization
        DataType dataType = DataType.STRING; // Object.getDataType();
        Class<?> roleClass = Class.forName("net.dv8tion.jda.api.entities.Role");
        System.out.println(roleClass.descriptorString());
        // here is the key for the plugin and container and feature and setting to find it in the
        // map or
        // whatever in the server registry;
        // lets say we found it Plugin plugin = getPluginFromKey(); getFeatureManager();
        // getFeatures()
        // or getFeature(FEATURE_KEY); getSetting(SETTING_KEY);
        getT(
                base.getFeatureManager().getFeatures().stream()
                        .findFirst()
                        .get()
                        .getSettingGroup()
                        .get()
                        .getSettings()
                        .stream()
                        .findFirst()
                        .get(),
                dataType,
                res,
                output);

        // after deserializing the web response (or during serialization or loading of a plugin?)

    }

    public static void testCode(Setting<?> setting, Object obj) throws ClassNotFoundException {
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

    private static <I, O> void getT(
            Setting<?> setting, DataType dataType, I response, Class<O> output) {
        Setting<O> castedSetting = (Setting<O>) setting;
        Input<I, O> castedInput = (Input<I, O>) castedSetting.getInput();
        Transformation<I, O> trans = castedInput.getTransformation().get();
        O returnO = trans.transform(response);
        trans.processTransformation(returnO);
    }
}
