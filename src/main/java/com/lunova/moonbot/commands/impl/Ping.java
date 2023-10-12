package com.lunova.moonbot.commands.impl;

import com.lunova.moonbot.annotations.CommandAnnotation;
import com.lunova.moonbot.commands.BotCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * Represents the "ping" command for the bot.
 * <p>
 * When this command is invoked, the bot responds with "pong".
 * It serves both as a simple example command and a way to check the bot's responsiveness.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
@CommandAnnotation(name = "ping")
public class Ping extends BotCommand {

    /**
     * Constructs the "ping" command.
     *
     * @param name the name of the command.
     */
    public Ping(String name) {
        super(name);
    }

    /**
     * Executes the ping command logic.
     * <p>
     * Responds to the user with a "pong" message.
     * </p>
     *
     * @param event the slash command interaction event that triggered this command.
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("pong").queue();
    }

    /**
     * Retrieves the registration data for the "ping" command.
     *
     * @return the slash command data for registering this command.
     */
    @Override
    public SlashCommandData getRegistryData() {
        return Commands.slash(getName(), "Ping Moon-Bot and get a reply!");
    }

}
