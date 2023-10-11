package com.lunova.moonbot.commands;

import com.lunova.moonbot.commands.impl.Help;
import com.lunova.moonbot.commands.impl.Ping;

import java.util.EnumSet;
import java.util.Optional;

public enum CommandList {
    PING(new Ping("ping")),
    HELP(new Help("help"));

    public static final EnumSet<CommandList> COMMANDS = EnumSet.allOf(CommandList.class);
    private final BotCommand botCommand;
    CommandList(BotCommand botCommand) {
        this.botCommand = botCommand;
    }

    public BotCommand getCommand() {
        return botCommand;
    }

    public static Optional<? extends BotCommand> getCommand(String identifier) {
        return COMMANDS.stream()
                .map(CommandList::getCommand)
                .filter(command -> command.getName().equalsIgnoreCase(identifier))  // Map the CommandList enum to its BotCommand
                .findFirst();
    }


}
