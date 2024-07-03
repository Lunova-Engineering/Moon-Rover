package com.lunova.moonbot.core.api.plugin.features.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

// @JsonSerialize(using = SettingGroupSerializer.class)
public class SettingGroup {

    @JsonProperty("settings")
    private final ImmutableSet<Setting<?>> settings;

    public static class Builder {
        private ImmutableSet<Setting<?>> settings;
        private final ImmutableSet.Builder<Setting<?>> builder = new ImmutableSet.Builder<>();

        public Builder withSetting(Setting<?> setting) {
            builder.add(setting);
            return this;
        }

        public Builder withSetting(Setting<?>... settings) {
            builder.add(settings);
            return this;
        }

        public SettingGroup build() {
            settings = builder.build();
            return new SettingGroup(this);
        }
    }

    private SettingGroup(Builder builder) {
        this.settings = builder.settings;
    }

    public ImmutableSet<Setting<?>> getSettings() {
        return settings;
    }
}
