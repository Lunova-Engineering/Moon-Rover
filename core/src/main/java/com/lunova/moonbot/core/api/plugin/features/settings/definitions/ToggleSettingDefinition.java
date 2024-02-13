package com.lunova.moonbot.core.api.plugin.features.settings.definitions;

import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingDefinition;

public class ToggleSettingDefinition<I, T> extends SettingDefinition<I, T> {

    public ToggleSettingDefinition(String label, Input<I, T> input) {
        super(label, input);
    }

}
