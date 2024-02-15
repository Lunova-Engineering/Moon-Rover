package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

public abstract class MaximumValueRule<T> implements ValidationRule<T> {

    private final Number value;

    public MaximumValueRule(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

}
