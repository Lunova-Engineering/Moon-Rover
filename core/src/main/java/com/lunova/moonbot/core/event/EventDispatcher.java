package com.lunova.moonbot.core.event;

import com.lunova.moonbot.core.services.plugin.PluginManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.14.2023
 */
public class EventDispatcher extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        PluginManager.plugins.forEach(plugin -> plugin.onSlashCommandInteraction(event));
        //super.onSlashCommandInteraction(event);
    }

}
