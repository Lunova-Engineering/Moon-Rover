package com.lunova.moonbot.logging.strategy;

import com.lunova.moonbot.logging.LogEventType;
import com.lunova.moonbot.logging.LogMessage;
import com.lunova.moonbot.logging.LogStrategy;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.14.2023
 */
public class CommandLogStrategy extends LogStrategy<SlashCommandInteractionEvent> {

    @Override
    public LogMessage getLogMessage(SlashCommandInteractionEvent event) {
        GuildMessageChannelUnion channel = event.getGuildChannel();
        ChannelType type = channel.getType();

        // 1. Format the date
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date(event.getTimeCreated().toInstant().toEpochMilli()));
        Category category = null;

        // 2. Check channel type using ChannelType enum
        if (type == ChannelType.TEXT) {
            category = channel.asTextChannel().getParentCategory();
        } else if (type == ChannelType.VOICE) {
            category = channel.asVoiceChannel().getParentCategory();
        }

        String formattedDate = new SimpleDateFormat("dd_MMMM_yyyy").format(new Date());

        // Retrieve the command name and options
        String commandName = event.getName();
        List<OptionMapping> options = event.getOptions(); // Get all options
        String optionDetails = options.stream()
                .map(option -> option.getName() + ": " + option.getAsString())
                .collect(Collectors.joining(", "));

        // Format message
        String logMsg = String.format("[%s] [%s_%s] /%s %s",
                timestamp,
                (category != null) ? category.getName().replaceAll(" ", "-") : "NO-CATEGORY",
                channel.getName(),
                commandName,
                optionDetails);

        // Construct log file name with category and channel name
        String logFileName = String.format("%s.log",
                formattedDate,
                channel.getName());

        // Building file path
        Path logFilePath = getLogPath()
                .resolve(event.getGuild().getName())
                .resolve(event.getUser().getName())
                .resolve(LogEventType.COMMAND.name())  // LogEvent directory
                .resolve(logFileName);  // Log file itself

        // Convert to File for LogMessage constructor
        File logFile = logFilePath.toFile();

        return new LogMessage(logFile, logMsg);
    }


}
