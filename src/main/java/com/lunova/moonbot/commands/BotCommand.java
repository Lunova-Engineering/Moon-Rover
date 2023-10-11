package com.lunova.moonbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.List;

public abstract class BotCommand {

    private final String name;

    public BotCommand(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public static void deregister(RestAction<List<Command>> commandList, BotCommand botCommand) {
        commandList.queue(commands -> {
            commands.stream()
                    .filter(command -> command.getName().equalsIgnoreCase(botCommand.getName()))
                    .findAny()
                    .ifPresentOrElse(
                            commandToDelete -> commandToDelete.delete().queue(),
                            () -> {
                                throw new IllegalArgumentException("Command not found for identifier: " + botCommand.getName());
                            }
                    );
        }, throwable -> {
            System.err.println("Error deregistering command: " + throwable.getMessage());
        });
    }


    public abstract SlashCommandData getRegistry();
    public abstract void execute(SlashCommandInteractionEvent event);
}
