package com.lunova.moonbot.logging;

import com.lunova.moonbot.movies.logging.LogEvent;
import com.lunova.moonbot.movies.logging.LogManager;
import com.lunova.moonbot.movies.logging.LogMessage;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LogManagerTest {

    @TempDir
    Path tempDirectory;  // JUnit5 will create and manage this temporary directory.

    @BeforeEach
    public void setUp() {
       // LogManager.LOG_MESSAGES.clear(); // Clear the static log messages map before each test.
        // Reset BASE_DIR in LogManager to point to our temporary directory. This might require you to adjust the access level of BASE_DIR or provide a setter.
         //LogManager.BASE_DIR = tempDirectory.toString();
    }

    @Test
    public void testSubmitLog() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn("TestUser");
        when(mockUser.getId()).thenReturn("123456");

        LogMessage logMessage = new LogMessage(System.currentTimeMillis(), mockUser, "Test Message");

        LogManager.submitLog(LogEvent.MESSAGE, logMessage);

       // assertTrue(LogManager.LOG_MESSAGES.containsKey(mockUser));
        //assertTrue(LogManager.LOG_MESSAGES.get(mockUser).containsKey(LogEvent.MESSAGE));
        //assertEquals(1, LogManager.LOG_MESSAGES.get(mockUser).get(LogEvent.MESSAGE).size());
    }

    @Test
    public void testFlushLogs() throws IOException {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn("TestUser");
        when(mockUser.getId()).thenReturn("123456");

        LogMessage logMessage = new LogMessage(System.currentTimeMillis(), mockUser, "Test Message");
       // LogManager.LOG_MESSAGES.put(mockUser, Map.of(LogEvent.MESSAGE, new ArrayList<>(List.of(logMessage))));

        LogManager.flushLogs();

        // Assertions for directories and files being created.
        Path userDir = tempDirectory.resolve("TestUser (123456)");
        assertTrue(Files.exists(userDir));

        Path eventDir = userDir.resolve(LogEvent.MESSAGE.name());
        assertTrue(Files.exists(eventDir));

        Path logFile = eventDir.resolve("TestUser_" + LogEvent.MESSAGE.name() + "_log.txt");
        assertTrue(Files.exists(logFile));

        String content = Files.readString(logFile);
        assertTrue(content.contains("[MESSAGE] Test Message")); // Adjust this as needed based on your exact log format.
    }
}
