package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

public abstract class MinimumValueRule<T> extends ValidationRule<T> {

    private final Number value;

    public MinimumValueRule(Number value) {
        super("MIN_VALUE_RULE");
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

}
