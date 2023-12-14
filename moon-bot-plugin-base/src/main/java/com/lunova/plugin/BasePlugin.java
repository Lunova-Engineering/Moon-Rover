package com.lunova.plugin;

import com.lunova.moonbot.core.plugin.Plugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class BasePlugin extends Plugin {

    public BasePlugin(String name, String version) {
        super(name, version);
    }

    @Override
    public void beforeInstall(JDA session) {

    }

    @Override
    public void install(JDA session, String guildId) {
        session.getGuildById(guildId).updateCommands().addCommands(Commands.slash("ping", "Ping the bot")).queue();
    }

    @Override
    public void afterInstall(JDA session) {

    }

    @Override
    public void beforeUninstall(JDA session) {

    }

    @Override
    public void uninstall(JDA session) {

    }

    @Override
    public void afterUninstall(JDA session) {

    }

}
