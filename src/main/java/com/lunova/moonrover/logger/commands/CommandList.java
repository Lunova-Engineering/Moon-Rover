package com.lunova.moonrover.logger.commands;

import com.lunova.moonrover.logger.commands.impl.Ping;

import java.util.EnumSet;
import java.util.Optional;

public enum CommandList {
    PING(new Ping("ping"));

    public static final EnumSet<CommandList> COMMANDS = EnumSet.allOf(CommandList.class);
    private BotCommand botCommand;
    CommandList(BotCommand botCommand) {
        this.botCommand = botCommand;
    }

    public BotCommand getCommand() {
        return botCommand;
    }

    public static Optional<? extends BotCommand> getCommand(String identifier) {
        return COMMANDS.stream()
                .map(CommandList::getCommand)
                .filter(command -> command.getIdentifier().equalsIgnoreCase(identifier))  // Map the CommandList enum to its BotCommand
                .findFirst();
    }


}
