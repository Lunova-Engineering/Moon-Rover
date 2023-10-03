package com.lunova.moonrover.logger.commands.impl;

import com.lunova.moonrover.logger.commands.BotCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class Ping extends BotCommand {
    public Ping(String identifier) {
        super(identifier);
    }

    @Override
    public void register(CommandListUpdateAction commandList) {
        commandList.addCommands(
                Commands.slash("ping", "Ping Moon-Bot for a response!")
        ).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("pong").queue();
    }

}
