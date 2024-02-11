package com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.impl;

import com.lunova.moonbot.core.api.plugin.features.settings.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.InputType;

import java.util.Collection;

public class SelectionInput<T> extends Input<T> {

    private Collection<?> collection;
    public SelectionInput(InputType inputType, Collection<?> collection) {
        super(inputType);
    }

}
