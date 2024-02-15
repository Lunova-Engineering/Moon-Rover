package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

public abstract class MinimumValueRule<T> implements ValidationRule<T> {

    private Class<MinimumValueRule> clazz = MinimumValueRule.class;
    private final Number value;

    public MinimumValueRule(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

}
