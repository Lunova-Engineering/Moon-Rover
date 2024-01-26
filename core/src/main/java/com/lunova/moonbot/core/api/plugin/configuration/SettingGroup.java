package com.lunova.moonbot.core.api.plugin.configuration;

import com.google.common.collect.ImmutableSet;

public class SettingGroup {

    private final ImmutableSet<Setting> settings;

    public static class Builder {
        private ImmutableSet<Setting> settings;
        private final ImmutableSet.Builder<Setting> builder = new ImmutableSet.Builder<>();

        public Setting.Builder createOptionBuilder() {
            return new Setting.Builder();
        }

        public void registerOption(Setting setting) {
            builder.add(setting);
        }

        public void registerAllOption(Iterable<Setting> options) {
            builder.addAll(options);
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

    public static SettingGroup empty() {
        return new Builder().build();
    }

}
