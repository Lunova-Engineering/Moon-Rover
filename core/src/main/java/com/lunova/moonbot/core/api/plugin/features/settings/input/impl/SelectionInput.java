package com.lunova.moonbot.core.api.plugin.features.settings.input.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;

import java.util.ArrayList;
import java.util.Collection;

public class SelectionInput<I, O> extends Input<I, O> {

    @JsonProperty("options")
    private final ArrayList<String> inputOptions = new ArrayList<>();
    public SelectionInput(InputType inputType, String label, Collection<?> inputOptions) {
        super(inputType, label);
        this.inputOptions.addAll(inputOptions.stream().map(Object::toString).toList());
    }

}
