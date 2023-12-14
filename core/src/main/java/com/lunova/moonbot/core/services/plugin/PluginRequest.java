package com.lunova.moonbot.core.services.plugin;

public class PluginRequest {

    private final String guildId;
    private final PluginAction pluginAction;
    private final String pluginUrl;

    public PluginRequest(String guildId, PluginAction pluginAction, String pluginUrl) {
        this.guildId = guildId;
        this.pluginAction = pluginAction;
        this.pluginUrl = pluginUrl;
    }

    public String getGuildId() {
        return guildId;
    }

    public PluginAction getPluginAction() {
        return pluginAction;
    }

    public String getPluginUrl() {
        return pluginUrl;
    }

}
