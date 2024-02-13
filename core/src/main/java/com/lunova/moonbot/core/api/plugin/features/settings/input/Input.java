package com.lunova.moonbot.core.api.plugin.features.settings;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.InputType;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;

public class Input<I, O>  {

    private final InputType inputType;
    private Transformation<I, O> transformation;

    public Input(InputType inputType) {
        this.inputType = inputType;
    }

    public Optional<Transformation<I, O>> getTransformation() {
        return Optional.fromNullable(transformation);
    }

    public void setTransformation(Transformation<I, O> transformation) {
        this.transformation = transformation;
    }

    public InputType getInputType() {
        return inputType;
    }

}
