package com.lunova.moonbot;

import com.lunova.moonbot.commands.CommandManager;
import com.lunova.moonbot.logging.LogManager;
import com.lunova.moonbot.messages.MessageManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;
import java.util.Set;

/**
 * MoonBot serves as the main entry point for the Discord bot application.
 * <p>
 * It initializes the bot, connects to the Discord API using JDA, registers listener adapters,
 * and sets up commands for the bot.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
public class MoonBot {

    /**
     * A collection of all available gateway intents.
     * <p>
     * Gateway intents dictate what events the bot will receive. Using all possible
     * intents can impact bot performance and should be used with consideration.
     * </p>
     */
    private static final EnumSet<GatewayIntent> INTENTS = EnumSet.allOf(GatewayIntent.class);

    /**
     * A collection of listener adapters that the bot uses to handle various events.
     * Currently, this set includes a command manager for handling command-related events.
     */
    private static final Set<ListenerAdapter> LISTENER_ADAPTERS = Set.of(
            new CommandManager(),
            new MessageManager()
    );

    /**
     * Represents the main JDA instance for interacting with the Discord API.
     */
    public static JDA jda;

    /**
     * Represents the primary guild (server) in which the bot operates.
     */
    public static Guild guild;

    /**
     * The main entry point of the MoonBot application.
     * <p>
     * Initializes the bot, connects to Discord, retrieves the primary guild,
     * and sets up command handling for the bot.
     * </p>
     *
     * @param args the input arguments where args[0] is the bot token and args[1] is the guild ID.
     * @throws Exception if there's an issue initializing the bot or connecting to Discord.
     */
    public static void main(String[] args) throws Exception {
        jda = JDABuilder.createDefault(args[0]).enableIntents(INTENTS).addEventListeners(LISTENER_ADAPTERS.toArray()).build();
        jda.awaitReady();
        guild = jda.getGuildById(Long.parseLong(args[1]));
        CommandManager.initializeCommands();
        CommandManager.deregisterAllInGuild(guild);
        CommandManager.registerAllInGuild(guild);
        LogManager.initialize();
    }

}
