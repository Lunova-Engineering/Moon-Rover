package com.lunova.moonbot.core.api.plugin.features.settings.definitions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lunova.moonbot.core.api.plugin.features.settings.Input;

public class SettingDefinition<I, T> {

    @JsonProperty("inputLabel")
    private final String label;

    @JsonProperty("input")
    private final Input<I, T> input;

    //Return type and object to match with @Setting key for K,V  pair?


    public SettingDefinition(String label, Input<I, T> input) {
        this.label = label;
        this.input = input;
    }


    public String getLabel() {
        return label;
    }

    public Input<I, T> getInput() {
        return input;
    }



}
