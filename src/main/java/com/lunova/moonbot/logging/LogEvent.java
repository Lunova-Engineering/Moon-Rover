package com.lunova.moonbot.logging;

import net.dv8tion.jda.api.events.Event;

import java.time.OffsetDateTime;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.15.2023
 */
public class LogEvent {

    private final OffsetDateTime timeStamp;

    private final LogEventType logEventType;

    private final Event event;

    public LogEvent(LogEventType logEventType, Event event) {
        this(OffsetDateTime.now(), logEventType, event);
    }

    public LogEvent(OffsetDateTime timeStamp, LogEventType logEventType, Event event) {
        this.timeStamp = timeStamp;
        this.logEventType = logEventType;
        this.event = event;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    public LogEventType getLogEventType() {
        return logEventType;
    }

    public Event getEvent() {
        return event;
    }

}
