package com.lunova.moonbot.core.api.plugin.features.settings.input.impl;

import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputFormat;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;

public class ToggleInput<I, O> extends Input<I, O> {

    public ToggleInput(InputType type, String label) {
        super(InputFormat.TOGGLE, type, label);
    }
}
