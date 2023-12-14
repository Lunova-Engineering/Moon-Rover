package com.lunova.moonbot.core.plugin;

import com.lunova.moonbot.core.exceptions.PluginRoutineException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Plugin extends ListenerAdapter {

    private final String name;
    private final String version;
    private PluginInstallState pluginInstallState = PluginInstallState.NONE;

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

    public void executeInstallRoutine(JDA session, String guildId) throws PluginRoutineException {
        try {
            if(pluginInstallState.equals(PluginInstallState.NONE)) {
                pluginInstallState = PluginInstallState.PRE_INSTALL;
                beforeInstall(session, guildId);
            }
            if(pluginInstallState.equals(PluginInstallState.PRE_INSTALL)) {
                pluginInstallState = PluginInstallState.INSTALL;
                install(session, guildId);
            }
            if(pluginInstallState.equals(PluginInstallState.INSTALL)) {
                pluginInstallState = PluginInstallState.POST_INSTALL;
                afterInstall(session, guildId);
            }
            pluginInstallState = PluginInstallState.COMPLETE;
        } catch (Exception e) {
            throw new PluginRoutineException(e.getMessage());
        }
    }

    public void executeUninstallRoutine(JDA session, String guildId) throws PluginRoutineException {
        try {
            if(pluginInstallState.equals(PluginInstallState.NONE)) {
                pluginInstallState = PluginInstallState.PRE_UNINSTALL;
                beforeUninstall(session, guildId);
            }
            if(pluginInstallState.equals(PluginInstallState.PRE_UNINSTALL)) {
                pluginInstallState = PluginInstallState.UNINSTALL;
                uninstall(session, guildId);
            }
            if(pluginInstallState.equals(PluginInstallState.UNINSTALL)) {
                pluginInstallState = PluginInstallState.POST_UNINSTALL;
                afterUninstall(session, guildId);
            }
            pluginInstallState = PluginInstallState.COMPLETE;
        } catch (Exception e) {
            throw new PluginRoutineException(e.getMessage());
        }
    }

    public void beforeInstall(JDA session, String guildId) {

    }

    public void afterInstall(JDA session, String guildId) {

    }
    public void beforeUninstall(JDA session, String guildId) {

    }
    public void afterUninstall(JDA session, String guildId) {

    }
    public abstract void uninstall(JDA session, String guildId);
    public abstract void install(JDA session, String guildId);


}
