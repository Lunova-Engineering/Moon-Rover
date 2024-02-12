package com.lunova.moonbot.core.api.plugin.features.settings;

import com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.InputType;

public class InputFactory {

    public static  <I, O> Input<I, O> createInput(InputType inputType, Class<I> input, Class<O> output) {
        return new Input<I, O>(inputType);
    }
}
