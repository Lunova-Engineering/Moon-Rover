package com.lunova.moonbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * Represents a bot command that can be executed in response to a
 * Discord slash command interaction.
 * <p>
 * Any specific command should extend this class and implement the abstract methods
 * to define its behavior and registration data.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
public abstract class BotCommand {

    /**
     * The unique name for this bot command.
     */
    private final String name;

    /**
     * Constructs a new bot command with the given name.
     *
     * @param name the unique name of this bot command.
     */
    public BotCommand(String name) {
        this.name = name;
    }

    /**
     * Executes the logic associated with this bot command in response
     * to a slash command interaction event.
     *
     * @param event the slash command interaction event that triggered this command.
     */
    public abstract void execute(SlashCommandInteractionEvent event);

    /**
     * Retrieves the registration data required to register this command with Discord.
     * This includes details like the command name, description, options, etc.
     *
     * @return the slash command data for registering this command.
     */
    public abstract SlashCommandData getRegistryData();

    /**
     * Retrieves the unique name of this bot command.
     *
     * @return the name of this bot command.
     */
    public String getName() {
        return name;
    }

}
