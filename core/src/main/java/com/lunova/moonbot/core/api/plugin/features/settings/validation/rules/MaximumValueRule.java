package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

public abstract class MaximumValueRule<T> extends ValidationRule<T> {

    private final Number value;

    public MaximumValueRule(Number value) {
        super("MAX_VALUE_RULE");
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    protected abstract boolean validateRule(T target);
}
