package com.lunova.moonbot.core.api.plugin.configuration;

public interface Configurable {

    SettingGroup getSettingGroup();
    void bindConfigureOptions(SettingGroup settingGroup);
}
