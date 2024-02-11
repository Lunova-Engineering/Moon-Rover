package com.lunova.moonbot.core.api.plugin.features.settings;

import com.lunova.moonbot.core.api.plugin.features.settings.definitions.SettingDefinition;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.impl.SelectionSettingDefinition;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.InputType;

import java.util.ArrayList;
import java.util.Collection;

public class SettingBuilder {

    public <T, IO extends Input<T>, R> IO createInput(Class<T> output, Class<IO> inputClass, Class<R> returnType) {

    }

    public static void createUserSetting() {

    }

    public static <T, I> Setting<T> createSelectionSetting(Class<I> inputType, Class<T> outputType, String key, boolean required, String label, Collection<?> collection) {
        Input<I, T> input = InputFactory.createInput(inputType, outputType);
        SelectionSettingDefinition<I, T> definition = new SelectionSettingDefinition<>(label, new ArrayList<String>(10), input);
        Setting.Builder<I, T> builder = new Setting.Builder<I, T>(key, required, definition);
        return builder.build();
    }

    public static void createToggleSetting() {

    }

    public static void createSliderSetting() {

    }



    public  <T> Setting<T> createSetting(Class<T> outputType, String key, boolean required, InputType inputType) {
      // Input<Role> input = createInput(Role.class, SelectionInput.class, String.class);

        Input<String, T> input = InputFactory.createInput(String.class, outputType);
        SettingDefinition<T> definition = new SettingDefinition<>(key, input);

        Setting.Builder<T> builder = new Setting.Builder<>(key, required, definition);

        return builder.build();
    }
}
