package com.lunova.moonrover.logger.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        CommandList.getCommand(event.getName()).ifPresentOrElse(command -> command.execute(event), () -> System.out.println("Unable to locate command named \""+event.getName() +"\""));
        //super.onSlashCommandInteraction(event);
    }
}
