package com.lunova.moonbot.movies.logging;

import com.lunova.moonbot.movies.logging.strategy.MessageLogStrategy;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * The enum Log event.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon -Rover
 * @since 10.13.2023
 */
public enum LogEvent {
    MESSAGE(MessageReceivedEvent.class, new MessageLogStrategy());

    private final Class<? extends Event> eventType;
    private final LogStrategy<? extends Event> strategy;

    <T extends Event> LogEvent(Class<T> eventType, LogStrategy<T> strategy) {
        this.eventType = eventType;
        this.strategy = strategy;
    }

    public <T extends Event> LogStrategy<T> getStrategy() {
        return (LogStrategy<T>) strategy;
    }
}
