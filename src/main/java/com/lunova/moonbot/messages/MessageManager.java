package com.lunova.moonbot.messages;

import com.lunova.moonbot.movies.logging.LogEvent;
import com.lunova.moonbot.movies.logging.LogManager;
import com.lunova.moonbot.movies.logging.LogMessage;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        System.out.println(event.getAuthor() + " - " +  event.getMessage().getContentRaw());
        LogManager.submitLog(LogEvent.MESSAGE, new LogMessage(System.currentTimeMillis(), event.getAuthor(), event.getMessage().getContentRaw()));
        //super.onMessageReceived(event);
    }

}
