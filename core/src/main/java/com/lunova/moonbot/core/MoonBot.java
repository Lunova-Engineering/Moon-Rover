package com.lunova.moonbot.core;

import com.lunova.moonbot.core.service.ServiceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Entry point for the MoonBot Discord bot. Initializes and manages the bot's operations using the
 * JDA library. Responsible for configuring the bot, initializing services
 *
 * <p>Upon start, sets the main thread's name and initializes services
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class MoonBot {

    /** Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MoonBot.class);

    public static ShutdownCode shutdown = ShutdownCode.NORMAL;

    private static final ShutdownHook hook = new ShutdownHook();

    /**
     * Initializes the bot application. Sets the main thread's name and initializes services. Errors
     * during initialization and runtime are logged.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            Thread.currentThread().setName("Main");
            MDC.put("threadName", Thread.currentThread().getName());
            Runtime.getRuntime().addShutdownHook(hook);
            ServiceManager.initialize();
        } catch (Exception e) {
            // Should catch a specific exception such as "FatalException" telling us we've reached a
            // point
            // we cannot
            // recover from and need to shut the entire application down initiating a safe and
            // controlled
            // shutdown
            // versus a fatal uncaught error that will crash the entire program which would be
            // handled
            // using a shut down hook.
            shutdown = ShutdownCode.UNEXPECTED_ERROR;
            throw new RuntimeException(e);
        } finally {
            // if(shutdown.equals(ShutdownCode.NORMAL))
            // Runtime.getRuntime().removeShutdownHook(hook);
            // Here is cleanup, likely we will execute a safe shutdown of everything, namely
            // services
            // If something fatal happens that isn't caught it will be handled using a shutdown hook
            // Shutdown hook should be registered in the try block and contain logic to safely
            // shutdown
            // everything including all services and any other application data as the application
            // grows
            // LOGGER.info("Application has fully terminated and cleaned up all resources and saved
            // all
            // data. ");
        }
    }
}
