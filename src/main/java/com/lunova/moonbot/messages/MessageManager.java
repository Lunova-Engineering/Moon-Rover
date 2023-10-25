package com.lunova.moonbot.messages;

import com.lunova.moonbot.logging.LogEventType;
import com.lunova.moonbot.logging.LogManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        LogManager.submitLog(event.getMessage().getTimeCreated(), LogEventType.MESSAGE, event);
    }

}
