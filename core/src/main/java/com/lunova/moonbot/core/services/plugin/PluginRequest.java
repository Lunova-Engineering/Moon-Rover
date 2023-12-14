package com.lunova.moonbot.core.services.plugin;

public record PluginRequest(String guildId, PluginAction pluginAction, String pluginUrl) {}
