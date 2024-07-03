package com.lunova.moonbot.core.api.plugin.features.settings.input.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputFormat;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;

public class RangeSliderInput<I, O> extends Input<I, O> {

    @JsonProperty("min")
    private final Number min;

    @JsonProperty("max")
    private final Number max;

    @JsonProperty("step")
    private final Number step;

    public RangeSliderInput(InputType type, String label, Number min, Number max, Number step) {
        super(InputFormat.RANGE_SLIDER, type, label);
        this.min = min;
        this.max = max;
        this.step = step;
    }
}
