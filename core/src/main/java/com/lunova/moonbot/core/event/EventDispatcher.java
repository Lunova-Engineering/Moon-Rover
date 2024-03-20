package com.lunova.moonbot.core.event;

import com.lunova.moonbot.core.servold.plugin.PluginManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Dispatches various events to the registered plugins. It extends the {@link ListenerAdapter} class
 * from JDA, enabling it to listen and respond to Discord events. The EventDispatcher is responsible
 * for taking incoming Discord events and propagating them to all plugins for processing.
 *
 * <p>Currently, it handles {@link SlashCommandInteractionEvent}, forwarding these events to each
 * plugin managed by the {@link PluginManager}. Future implementations may include more Discord
 * events and additional logic for event handling and propagation.
 */
public class EventDispatcher extends ListenerAdapter {

  /**
   * Handles the Slash Command Interaction event from Discord. This method is called when a Slash
   * Command is used in Discord, and it dispatches this event to all registered plugins for
   * handling.
   *
   * <p>Each plugin gets a chance to process this event. Plugins are responsible for determining if
   * and how they should respond to the slash command based on their own criteria.
   *
   * @param event The slash command interaction event that was triggered in Discord.
   */
  @Override
  public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
   // PluginManager.PLUGIN_LIST.forEach(plugin -> plugin.onSlashCommandInteraction(event));
    // Note: The super call is commented out as typically, the parent method does nothing,
    // but you might enable it if the parent class's behavior is required in future JDA versions.
    // super.onSlashCommandInteraction(event);
  }
}
