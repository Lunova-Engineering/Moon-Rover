package com.lunova.moonbot.movies.logging;

import net.dv8tion.jda.api.events.Event;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.15.2023
 */
public class LogEvent {

    private LogEventType logEventType;
    private Event event;

    public LogEvent(LogEventType logEventType, Event event) {
        this.logEventType = logEventType;
        this.event = event;
    }

    public LogEventType getLogEventType() {
        return logEventType;
    }

    public Event getEvent() {
        return event;
    }

}
