package com.lunova.moonbot.core.api.plugin.features.settings.definitions;

import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingDefinition;

public class RangeSettingDefinition<I, T> extends SettingDefinition<I, T> {

    private final Number min, step, max;
    public RangeSettingDefinition(String label, Input<I, T> input, Number min, Number step, Number max) {
        super(label, input);
        this.min = min;
        this.step = step;
        this.max = max;
    }

    public Number getMin() {
        return min;
    }

    public Number getStep() {
        return step;
    }

    public Number getMax() {
        return max;
    }

}
