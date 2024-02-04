package com.lunova.moonbot.core.api.plugin;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.configuration.SettingGroup;

public interface Configurable {

    default Optional<SettingGroup> getSettingGroup() {
        return Optional.absent();
    }
}
