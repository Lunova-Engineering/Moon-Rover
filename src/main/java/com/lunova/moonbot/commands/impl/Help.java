package com.lunova.moonbot.commands.impl;

import com.lunova.moonbot.commands.BotCommand;
import com.lunova.moonbot.commands.CommandList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Help extends BotCommand {
    public Help(String identifier) {
        super(identifier);
    }

    @Override
    public SlashCommandData getRegistry() {
        return Commands.slash("help", "Provides a list of commands available to you from Moon-Bot");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.LIGHT_GRAY); // Color resembling code block background

        StringBuilder bldr = new StringBuilder();

        // Determine the length of the longest command
        int maxCommandLength = 0;
        List<Command> allCommands = new ArrayList<>();
        allCommands.addAll(event.getJDA().retrieveCommands().complete());
        allCommands.addAll(event.getGuild().retrieveCommands().complete());

        for (Command command : allCommands) {
            int length = command.getFullCommandName().length();
            if (length > maxCommandLength) {
                maxCommandLength = length;
            }
        }

        // Append commands and descriptions with padding
        for (Command command : allCommands) {
            bldr.append("**/")
                    .append(command.getFullCommandName())
                    .append("**\n*")
                    //.append(String.format("%" + (maxCommandLength - command.getFullCommandName().length() + 4) + "s", "")) // Padding
                    .append(command.getDescription())
                    .append("*\n\n");
        }

        embedBuilder.setDescription(bldr.toString());
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
