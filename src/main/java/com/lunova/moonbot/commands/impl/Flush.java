package com.lunova.moonbot.commands.impl;

import com.lunova.moonbot.annotations.CommandAnnotation;
import com.lunova.moonbot.commands.BotCommand;
import com.lunova.moonbot.movies.logging.LogManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * The type Flush.
 */
@CommandAnnotation(name = "flush")
public class Flush extends BotCommand {

    /**
     * Constructs a new bot command with the given name.
     *
     * @param name the unique name of this bot command.
     */
    public Flush(String name) {
        super(name);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        LogManager.flushLogs();
        event.reply("Flushing Logs!").queue();
    }

    @Override
    public SlashCommandData getRegistryData() {
        return Commands.slash(getName(), "Flushes current log buffer.");
    }

}
