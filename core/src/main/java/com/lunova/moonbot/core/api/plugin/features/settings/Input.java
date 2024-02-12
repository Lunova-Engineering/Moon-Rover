package com.lunova.moonbot.core.api.plugin.features.settings;

import com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.InputType;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;

public class Input<I, O>  {

    private InputType inputType;
    private Transformation<I, O> transformation;

    public Input(InputType inputType) {
        this.inputType = inputType;
    }

    public Transformation<I, O> getTransformation() {
        return transformation;
    }

    public Input<I, O> withTransformation(Transformation<I, O> transformation) {
        this.transformation = transformation;
        return this;
    }

    public InputType getInputType() {
        return inputType;
    }

}
