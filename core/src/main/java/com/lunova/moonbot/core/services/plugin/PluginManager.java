package com.lunova.moonbot.core.services.plugin;

import com.lunova.moonbot.core.plugin.Plugin;

import java.util.*;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.14.2023
 */
public class PluginManager {

    private static final Map<String, List<Plugin>> p2 = new HashMap<>();
    public static final List<Plugin> plugins = new ArrayList<>();

    public static void registerPlugin(Plugin plugin, String guildId) {
        // Find a plugin with the same name and version
        Optional<Plugin> existingPlugin = plugins.stream()
                .filter(p -> p.getName().equals(plugin.getName()) && p.getVersion().equals(plugin.getVersion()))
                .findFirst();

        Plugin pluginToUse = existingPlugin.orElseGet(() -> {
            plugins.add(plugin); // Add new plugin to the list if not found
            return plugin; // Use the new plugin
        });

        // Add or update the plugin list for the guildId in p2 map
        p2.computeIfAbsent(guildId, k -> new ArrayList<>()).add(pluginToUse);
    }
}
