package com.lunova.moonbot.commands;

import com.lunova.moonbot.annotations.CommandAnnotation;
import com.lunova.moonbot.exceptions.CommandNotFoundException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages the registration, deregistration, and execution of bot commands.
 * <p>
 * Utilizes the {@link CommandAnnotation} to discover command implementations dynamically via reflection.
 * Commands are stored in a set and can be registered/deregistered to/from a guild.
 * Also handles the event of a slash command interaction, executing the appropriate command if found.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
public class CommandManager extends ListenerAdapter {

    /**
     * Contains all the registered {@link BotCommand}s.
     */
    public static final Set<BotCommand> COMMANDS = new HashSet<>();

    /**
     * Logger instance for logging command-related activities and errors.
     */
    public static final Logger COMMAND_LOGGER = LoggerFactory.getLogger(CommandManager.class);

    /**
     * Registers all commands in the COMMANDS set to a specified guild.
     *
     * @param guild The target guild for the registration.
     */
    public static void registerAllInGuild(Guild guild) {
        guild.updateCommands().addCommands(COMMANDS.stream().map(BotCommand::getRegistryData).collect(Collectors.toList())).queue();
    }

    /**
     * Deregisters all commands from a specified guild.
     *
     * @param guild The target guild for the deregistration.
     */
    public static void deregisterAllInGuild(Guild guild) {
        guild.retrieveCommands().complete().forEach(command -> command.delete().queue());
    }

    /**
     * Initializes commands by discovering them using reflection and the {@link CommandAnnotation}.
     * Commands are instantiated and added to the COMMANDS set.
     *
     * @throws Exception If there's an error during reflection or command instantiation.
     */
    public static void initializeCommands() throws Exception {
        Reflections reflections = new Reflections("com.lunova.moonbot.commands.impl");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(CommandAnnotation.class);

        for (Class<?> clazz : annotatedClasses) {
            if (BotCommand.class.isAssignableFrom(clazz)) {
                CommandAnnotation annotation = clazz.getAnnotation(CommandAnnotation.class);
                String description = annotation.name();

                Constructor<?> constructor = clazz.getConstructor(String.class);
                BotCommand command = (BotCommand) constructor.newInstance(description);

                COMMANDS.add(command);
            }
        }
    }

    /**
     * Provides access to the command logger.
     *
     * @return The logger instance for command-related logging.
     */
    public static Logger getLogger() {
        return COMMAND_LOGGER;
    }

    /**
     * Event handler for slash command interactions. Executes the appropriate command if found.
     * Throws and logs a {@link CommandNotFoundException} if the command isn't present.
     *
     * @param event The slash command interaction event.
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Optional<BotCommand> command = COMMANDS.stream().filter(botCommand -> event.getFullCommandName().equalsIgnoreCase(botCommand.getName())).findFirst();
        command.ifPresentOrElse(c -> c.execute(event), () -> {
            try {
                throw new CommandNotFoundException("Command with name: " + event.getFullCommandName() + " not found.");
            } catch (CommandNotFoundException e) {
                COMMAND_LOGGER.error(e.getMessage(), e);
            }
        });
    }

}