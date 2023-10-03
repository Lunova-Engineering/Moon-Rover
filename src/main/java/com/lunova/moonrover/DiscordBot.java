package com.lunova.moonrover;

import com.lunova.moonrover.logger.commands.CommandList;
import com.lunova.moonrover.logger.commands.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;
import java.util.Set;

public class DiscordBot {
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

        CommandList.COMMANDS.forEach(command -> command.getCommand().register(guild.updateCommands()));
    }

}