package com.lunova.moonbot.logging;

import net.dv8tion.jda.api.events.Event;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Rover
 * @since 10.14.2023
 */
public abstract class LogStrategy<T extends Event> {


    private static final Path LOG_PATH = Paths.get("data", "logs");

    public abstract LogMessage getLogMessage(T event);

    protected Path getLogPath() {
        return LOG_PATH;
    }

}
