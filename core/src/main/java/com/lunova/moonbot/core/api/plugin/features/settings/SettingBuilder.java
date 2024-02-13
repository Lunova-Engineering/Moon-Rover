package com.lunova.moonbot.core.api.plugin.features.settings;

import com.lunova.moonbot.core.api.plugin.features.settings.definitions.RangeSettingDefinition;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.SelectionSettingDefinition;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.ToggleSettingDefinition;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.UserSettingDefinition;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputFactory;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;

import java.util.ArrayList;
import java.util.Collection;

public final class SettingBuilder<I, O> {

    private final Setting.Builder<I, O> setting;

    private SettingBuilder(Setting.Builder<I, O> settingBuilder) {
        this.setting = settingBuilder;
    }

    public SettingBuilder<I, O> withTransformation(Transformation<I, O> transformation) {
        setting.withTransformation(transformation);
        return this;
    }

    public Setting<O> getSetting() {
        return setting.build();
    }


    public static <T, I> SettingBuilder<I, T> createSelectionInput(Class<I> inputReturnType, Class<T> outputType, String settingKey, boolean required, String inputLabel, InputType inputType, Collection<?> collection) {
        if(!inputReturnType.equals(inputType.getDataType().getClazz()))
            throw new IllegalArgumentException("Input return type does not match the selected Input Type's return data type.");
        Input<I, T> input = InputFactory.createInput(inputType, inputReturnType, outputType);
        SelectionSettingDefinition<I, T> definition = new SelectionSettingDefinition<>(inputLabel, new ArrayList<String>(10), input);
        Setting.Builder<I, T> builder = new Setting.Builder<I, T>(settingKey, required, definition);
        return new SettingBuilder<I, T>(builder);
    }

    public static <T, I> SettingBuilder<I, T> createSelectionSetting(Class<I> inputReturnType, Class<T> outputType, String settingKey, boolean required, String inputLabel, InputType inputType, Collection<?> collection) {
        if(!inputReturnType.equals(inputType.getDataType().getClazz()))
            throw new IllegalArgumentException("Input return type does not match the selected Input Type's return data type.");
        Input<I, T> input = InputFactory.createInput(inputType, inputReturnType, outputType);
        SelectionSettingDefinition<I, T> definition = new SelectionSettingDefinition<>(inputLabel, new ArrayList<String>(10), input);
        Setting.Builder<I, T> builder = new Setting.Builder<I, T>(settingKey, required, definition);
        return new SettingBuilder<I, T>(builder);
    }

    public static <T, I> Setting<T> createUserSetting(Class<I> inputReturnType, Class<T> outputType, String key, boolean required, String label, InputType inputType) {
        if(!(inputReturnType.equals(inputType.getDataType().getClazz())))
            throw new IllegalArgumentException("Input return type does not match the selected Input Type's return data type.");
        Input<I, T> input = InputFactory.createInput(inputType, inputReturnType, outputType);
        UserSettingDefinition<I, T> definition = new UserSettingDefinition<>(label, input);
        Setting.Builder<I, T> builder = new Setting.Builder<I, T>(key, required, definition);
        return builder.build();
    }

    public static <T, I> Setting<T> createRangeSetting(Class<I> inputReturnType, Class<T> outputType, String key, boolean required, String label, InputType inputType, Number min, Number step, Number max) {
        if(!(inputReturnType.equals(inputType.getDataType().getClazz())))
            throw new IllegalArgumentException("Input return type does not match the selected Input Type's return data type.");
        Input<I, T> input = InputFactory.createInput(inputType, inputReturnType, outputType);
        RangeSettingDefinition<I, T> definition = new RangeSettingDefinition<>(label, input, min, step, max);
        Setting.Builder<I, T> builder = new Setting.Builder<I, T>(key, required, definition);
        return builder.build();
    }

    public static <T, I> Setting<T> createToggleSetting(Class<I> inputReturnType, Class<T> outputType, String key, boolean required, String label, InputType inputType) {
        if(!(inputReturnType.equals(inputType.getDataType().getClazz())))
            throw new IllegalArgumentException("Input return type does not match the selected Input Type's return data type.");
        Input<I, T> input = InputFactory.createInput(inputType, inputReturnType, outputType);
        ToggleSettingDefinition<I, T> definition = new ToggleSettingDefinition<>(label, input);
        Setting.Builder<I, T> builder = new Setting.Builder<I, T>(key, required, definition);
        return builder.build();
    }

}
