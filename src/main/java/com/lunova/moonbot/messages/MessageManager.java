package com.lunova.moonbot.messages;

import com.lunova.moonbot.movies.logging.LogEventType;
import com.lunova.moonbot.movies.logging.LogManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //LogManager.submitLog(LogEvent.MESSAGE, event);
        //System.out.println(event.getGuildChannel() + "\n" + event.getChannelType() + "\n" + event.getChannel() + "\n" + event.getMessage().getContentRaw());
        LogManager.submitLog(LogEventType.MESSAGE, event);
    }

}
