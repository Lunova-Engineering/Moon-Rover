package com.lunova.moonbot.core.api.plugin.features.settings;

import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.RangeSliderInput;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.SelectionInput;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.ToggleInput;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.UserInput;

import java.util.Collection;

public final class SettingCreator {

    public static <I, O> Setting.Builder<I, O> createUserSetting(
            Class<I> input,
            Class<O> output,
            String settingKey,
            boolean required,
            String label,
            InputType inputType) {
        UserInput<I, O> selectionInput = new UserInput<>(inputType, label);
        return new Setting.Builder<>(settingKey, required, selectionInput)
                .withTyping(input, output);
    }

    public static <I, O> Setting.Builder<I, O> createSelectionSetting(
            Class<I> input,
            Class<O> output,
            String settingKey,
            boolean required,
            String label,
            InputType inputType,
            Collection<?> options) {
        SelectionInput<I, O> selectionInput = new SelectionInput<>(inputType, label, options);
        return new Setting.Builder<>(settingKey, required, selectionInput)
                .withTyping(input, output);
    }

    public static <I, O> Setting.Builder<I, O> createRangeSliderSetting(
            Class<I> input,
            Class<O> output,
            String settingKey,
            boolean required,
            String label,
            InputType inputType,
            Number min,
            Number max,
            Number step) {
        RangeSliderInput<I, O> selectionInput =
                new RangeSliderInput<>(inputType, label, min, max, step);
        return new Setting.Builder<>(settingKey, required, selectionInput)
                .withTyping(input, output);
    }

    public static <I, O> Setting.Builder<I, O> createToggleSetting(
            Class<I> input,
            Class<O> output,
            String settingKey,
            boolean required,
            String label,
            InputType inputType) {
        ToggleInput<I, O> toggleInput = new ToggleInput<>(inputType, label);
        return new Setting.Builder<>(settingKey, required, toggleInput).withTyping(input, output);
    }
}
