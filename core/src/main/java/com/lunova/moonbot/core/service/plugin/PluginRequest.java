package com.lunova.moonbot.core.service.plugin;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PluginRequest(
        @JsonProperty("id") @NotNull @Min(0) int id,
        @JsonProperty("timestamp") @NotBlank String timestamp,
        @JsonProperty("discordUserSnowflake") @NotBlank String discordUserSnowflake,
        @JsonProperty("guildId") @NotBlank String guildId,
        @JsonProperty("pluginGroupId") @NotBlank String pluginGroupId,
        @JsonProperty("pluginArtifactId") @NotBlank String pluginArtifactId,
        @JsonProperty("pluginVersion") @NotBlank String pluginVersion,
        @JsonProperty("pluginAction") @NotNull PluginAction pluginAction) {}
