package com.lunova.moonbot.core.api.plugin.features.settings.input.impl;

import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputFormat;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;

public class RangeSliderInput<I, O> extends Input<I, O> {

    public RangeSliderInput(InputType type, String label, Number min, Number max, Number step) {
        super(InputFormat.RANGE_SLIDER, type, label);
    }

}
