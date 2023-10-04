package com.lunova.moonbot.commands;

import com.lunova.moonbot.exceptions.CommandNotFoundException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        try {
            CommandList.getCommand(event.getName()).ifPresentOrElse(
                    command -> command.execute(event),
                    () -> {
                        throw new CommandNotFoundException("Unable to locate command named \""+event.getName() +"\"");
                    }
            );
        } catch(CommandNotFoundException e) {
            e.printStackTrace();
        }
    }
}
