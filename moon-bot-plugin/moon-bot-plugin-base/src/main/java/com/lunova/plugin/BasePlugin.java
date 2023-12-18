package com.lunova.plugin;

import com.lunova.moonbot.core.plugin.Plugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class BasePlugin extends Plugin {

    public BasePlugin() {
        super("Base Plugin", "0.2.0-SNAPSHOT");
    }

    @Override
    public void uninstall(JDA session, String guildId) {
        session.getGuildById(guildId).retrieveCommands().complete().forEach(c -> c.delete().queue());
    }

    @Override
    public void install(JDA session, String guildId) {
        session.getGuildById(guildId).updateCommands().addCommands(Commands.slash("ping", "Ping the bot")).queue();
        System.out.println("Updated guild commands!");
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
        //super.onSlashCommandInteraction(event);
    }

}
