package com.lunova.moonbot.core.api.plugin.features.settings.definitions;

import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingDefinition;

public class UserSettingDefinition<I, O> extends SettingDefinition<I, O> {

    public UserSettingDefinition(String label, Input<I, O> input) {
        super(label, input);
    }

}
