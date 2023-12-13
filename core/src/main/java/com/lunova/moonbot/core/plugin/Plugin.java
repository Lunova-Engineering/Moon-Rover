package com.lunova.moonbot.core.plugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Plugin extends ListenerAdapter {

    private String name;
    private String version;

    public Plugin(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public abstract void beforeInstall(JDA session);
    public abstract void install(JDA session);
    public abstract void afterInstall(JDA session);
    public abstract void beforeUninstall(JDA session);
    public abstract void uninstall(JDA session);
    public abstract void afterUninstall(JDA session);

}
