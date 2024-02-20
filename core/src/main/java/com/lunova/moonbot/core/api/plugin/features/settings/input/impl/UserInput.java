package com.lunova.moonbot.core.api.plugin.features.settings.input.impl;

import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputFormat;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;

public class UserInput<I, O> extends Input<I, O> {

    public UserInput(InputType inputType, String label) {
        super(InputFormat.USER_DEFINED, inputType, label);
    }

}
