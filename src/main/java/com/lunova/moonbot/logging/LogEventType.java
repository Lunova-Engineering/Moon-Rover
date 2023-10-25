package com.lunova.moonbot.logging;

import com.lunova.moonbot.logging.strategy.CommandLogStrategy;
import com.lunova.moonbot.logging.strategy.MessageLogStrategy;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * The enum Log event.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon -Rover
 * @since 10.13.2023
 */
public enum LogEventType {
    MESSAGE(MessageReceivedEvent.class, new MessageLogStrategy()),
    COMMAND(SlashCommandInteractionEvent.class, new CommandLogStrategy());

    private final Class<? extends Event> eventType;

    private final LogStrategy<? extends Event> strategy;

    <T extends Event> LogEventType(Class<T> eventType, LogStrategy<T> strategy) {
        this.eventType = eventType;
        this.strategy = strategy;
    }

    public <T extends Event> LogStrategy<T> getStrategy() {
        return (LogStrategy<T>) strategy;
    }
}
