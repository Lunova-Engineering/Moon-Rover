package com.lunova.moonbot.core.api.plugin.features;

import com.lunova.moonbot.core.api.plugin.configuration.Configurable;
import com.lunova.moonbot.core.api.plugin.configuration.SettingGroup;
import com.lunova.moonbot.core.api.plugin.configuration.Toggleable;

public abstract class FeatureComponent implements Toggleable, Configurable {



    @Override
    public SettingGroup getSettingGroup() {
        return null;
    }

    @Override
    public void bindConfigureOptions(SettingGroup settingGroup) {

    }

}
