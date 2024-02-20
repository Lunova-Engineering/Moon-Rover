package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

public abstract class RangeValueRule<T> extends ValidationRule<T> {

    private final Number min;
    private final Number max;

    public RangeValueRule(Number min, Number max) {
        super("RANGE_VALUE_RULE");
        this.min = min;
        this.max = max;
    }

    public Number getMin() {
        return min;
    }

    public Number getMax() {
        return max;
    }

}
