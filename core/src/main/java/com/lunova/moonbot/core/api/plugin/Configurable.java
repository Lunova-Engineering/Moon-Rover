package com.lunova.moonbot.core.api.plugin.configuration;

public interface Configurable {

    default SettingGroup getSettingGroup() {
        return SettingGroup.empty();
    }
}
