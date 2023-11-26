package com.lunova.plugin;

import java.util.List;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.25.2023
 */
public abstract class Plugin {

    private final String name;

    private final String version;

    public Plugin(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public void installPlugin(String serverId) {
        // TODO: requires code from core module that allows for calls to send to the JDA to install
        // everything to server
    }

    public abstract List<SlashCommandData> registerCommands();

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

}
