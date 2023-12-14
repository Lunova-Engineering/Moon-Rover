package com.lunova.moonbot.core.services.plugin;

import com.lunova.moonbot.core.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.14.2023
 */
public class PluginManager {

    private static final Map<String, List<Plugin>> p2 = new HashMap<>();

    public static final List<Plugin> plugins = new ArrayList<>();

    public static void registerPlugin(Plugin plugin) {
        plugins.add(plugin);
    }
}
