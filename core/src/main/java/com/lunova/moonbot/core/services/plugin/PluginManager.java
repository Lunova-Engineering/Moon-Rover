package com.lunova.moonbot.core.services.plugin;

import com.lunova.moonbot.core.plugin.Plugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

  /** A global list of all registered plugins. */
  public static final List<com.lunova.moonbot.core.plugin.Plugin> PLUGIN_LIST = new ArrayList<>();

  /** A map associating guild IDs with their respective list of plugins. */
  private static final Map<String, List<Plugin>> PLUGIN_MAP = new HashMap<>();

  /**
   * Registers a plugin for a specific guild identified by its ID. If the plugin is not already in
   * the global list, it is added. The plugin is then associated with the specified guild in the
   * plugin map.
   *
   * @param plugin The plugin to register.
   * @param guildId The ID of the guild where the plugin is to be registered.
   */
  public static void registerPlugin(com.lunova.moonbot.core.plugin.Plugin plugin, String guildId) {
    Optional<Plugin> existingPlugin =
        PLUGIN_LIST.stream()
            .filter(
                p ->
                    p.getName().equals(plugin.getName())
                        && p.getVersion().equals(plugin.getVersion()))
            .findFirst();

    com.lunova.moonbot.core.plugin.Plugin pluginToUse =
        existingPlugin.orElseGet(
            () -> {
              PLUGIN_LIST.add(plugin);
              return plugin;
            });

    PLUGIN_MAP.computeIfAbsent(guildId, k -> new java.util.ArrayList<>()).add(pluginToUse);
  }

  /**
   * Deregisters a plugin from a specific guild identified by its ID. The method removes the plugin
   * association from the guild in the plugin map.
   *
   * @param plugin The plugin to deregister.
   * @param guildId The ID of the guild from which the plugin is to be deregistered.
   * @return true if the plugin was successfully deregistered, false otherwise.
   */
  public static boolean deregisterPlugin(
      com.lunova.moonbot.core.plugin.Plugin plugin, String guildId) {
    List<Plugin> guildPlugins = PLUGIN_MAP.get(guildId);
    if (guildPlugins != null) {
      return guildPlugins.removeIf(
          p -> p.getName().equals(plugin.getName()) && p.getVersion().equals(plugin.getVersion()));
    }
    return false;
  }
}
