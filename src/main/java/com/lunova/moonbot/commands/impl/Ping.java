package com.lunova.moonbot.commands.impl;

import com.lunova.moonbot.commands.BotCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class Ping extends BotCommand {
    public Ping(String identifier) {
        super(identifier);
    }

    @Override
    public SlashCommandData getRegistry() {
       return Commands.slash("ping", "Ping Moon-Bot for a response!");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("pong").queue();
    }

}
