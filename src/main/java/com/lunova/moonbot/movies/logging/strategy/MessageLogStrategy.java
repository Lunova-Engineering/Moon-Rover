package com.lunova.moonbot.movies.logging.strategy;

import com.lunova.moonbot.movies.logging.LogEventType;
import com.lunova.moonbot.movies.logging.LogMessage;
import com.lunova.moonbot.movies.logging.LogStrategy;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.14.2023
 */
public class MessageLogStrategy extends LogStrategy<MessageReceivedEvent> {


    @Override
    public LogMessage getLogMessage(MessageReceivedEvent event) {
        GuildMessageChannelUnion channel = event.getGuildChannel();
        ChannelType type = channel.getType();
        // 1. Format the date
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date(event.getMessage().getTimeCreated().toInstant().toEpochMilli()));
        Category category = null;
        // 2. Check channel type using ChannelType enum
        if (type == ChannelType.TEXT) {
            category = channel.asTextChannel().getParentCategory();
        } else if (type == ChannelType.VOICE) {
            category = channel.asVoiceChannel().getParentCategory();
        }

            String formattedDate = new SimpleDateFormat("dd_MMMM_yyyy").format(new Date());
            // Format message
            String logMsg = String.format("[%s] [%s_%s] %s", timestamp,
                    (category != null) ? category.getName().replaceAll(" ", "-") : "NO-CATEGORY",
                    channel.getName(),
                    event.getMessage().getContentRaw());

            // Construct log file name with category and channel name
            String logFileName = String.format("%s.log",
                    formattedDate,
                    channel.getName());

            // Building file path
            Path logFilePath = getLogPath()
                    .resolve(event.getGuild().getName())
                    .resolve(event.getAuthor().getName())
                    .resolve(LogEventType.MESSAGE.name())  // LogEvent directory
                    .resolve(logFileName);  // Log file itself

            // Convert to File for LogMessage constructor
            File logFile = logFilePath.toFile();

            return new LogMessage(logFile, logMsg);
    }
}
