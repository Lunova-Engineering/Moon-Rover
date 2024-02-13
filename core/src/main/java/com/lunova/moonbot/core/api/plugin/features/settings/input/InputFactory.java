package com.lunova.moonbot.core.api.plugin.features.settings.input;

import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.SelectionInput;

import java.util.Collection;

public class InputFactory {

    public static  <I, O> Input<I, O> createInput(InputType inputType, Class<I> input, Class<O> output) {
        return new Input<I, O>(inputType, "");
    }

    public static <I, O> SelectionInput<I, O> getUserInput(InputType inputType, String label, Class<I> input, Class<O> output, Collection<?> options) {
        return new SelectionInput<I, O>(inputType, label, options);
    }
}
