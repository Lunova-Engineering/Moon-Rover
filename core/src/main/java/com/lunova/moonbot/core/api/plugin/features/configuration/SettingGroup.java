package com.lunova.moonbot.core.api.plugin.features.configuration;

import com.google.common.collect.ImmutableSet;

public class SettingGroup {

    private final ImmutableSet<Setting> settings;

    public static class Builder {
        private ImmutableSet<Setting> settings;
        private final ImmutableSet.Builder<Setting> builder = new ImmutableSet.Builder<>();

        public Builder withSetting(Setting setting) {
            builder.add(setting);
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

    public ImmutableSet<Setting> getOptions() {
        return settings;
    }

}
