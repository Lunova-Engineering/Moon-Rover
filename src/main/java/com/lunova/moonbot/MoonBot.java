package com.lunova.moonbot;

import com.lunova.moonbot.commands.BotCommand;
import com.lunova.moonbot.commands.CommandList;
import com.lunova.moonbot.commands.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MoonBot {
    public static JDA jda;

    public static Guild guild;

    private static final EnumSet<GatewayIntent> INTENTS = EnumSet.allOf(GatewayIntent.class);

    private static final Set<ListenerAdapter> LISTENER_ADAPTERS = Set.of(
            new CommandListener()
    );

    public static void main(String[] args) throws Exception {
        jda = JDABuilder.createDefault(args[0]).enableIntents(INTENTS).addEventListeners(LISTENER_ADAPTERS.toArray()).build();
        jda.awaitReady();
        guild = jda.getGuildById(Long.parseLong(args[1]));

        CommandListUpdateAction commands = guild.updateCommands();
        List<SlashCommandData> list = CommandList.COMMANDS.stream().map(CommandList::getCommand).collect(Collectors.toList()).stream().map(BotCommand::getRegistry).collect(Collectors.toList());
        commands.addCommands(list).queue();
    }

}