package com.lunova.moonbot.commands.impl;

import com.lunova.moonbot.annotations.CommandAnnotation;
import com.lunova.moonbot.commands.BotCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.12.2023
 */
@CommandAnnotation(name = "help")
public class Help extends BotCommand {

    /**
     * Constructs a new bot command with the given name.
     *
     * @param name the unique name of this bot command.
     */
    public Help(String name) {
        super(name);
    }

    /**
     * Executes the logic associated with this bot command in response
     * to a slash command interaction event.
     *
     * @param event the slash command interaction event that triggered this command.
     */
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

    /**
     * Retrieves the registration data required to register this command with Discord.
     * This includes details like the command name, description, options, etc.
     *
     * @return the slash command data for registering this command.
     */
    @Override
    public SlashCommandData getRegistryData() {
        return Commands.slash("help", "Provides a list of commands available to you from Moon-Bot");
    }

}
