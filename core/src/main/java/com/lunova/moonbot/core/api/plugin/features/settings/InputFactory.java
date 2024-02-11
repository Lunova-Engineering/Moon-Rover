package com.lunova.moonbot.core.api.plugin.features.settings;

public class InputFactory {

    public static  <I, O> Input<I, O> createInput(Class<I> input, Class<O> output) {
        return new Input<I, O>();
    }
}
