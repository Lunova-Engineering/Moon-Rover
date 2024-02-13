package com.lunova.moonbot.core.api.plugin.features.settings.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.Validation;

public class Input<I, O>  {

    @JsonProperty("inputType")
    private final InputType inputType;
    private final String label;

    @JsonIgnore
    private Transformation<I, O> transformation;
    @JsonIgnore
    private Validation validation;

    public Input(InputType inputType, String label) {
        this.inputType = inputType;
        this.label = label;
    }

    public Optional<Transformation<I, O>> getTransformation() {
        return Optional.fromNullable(transformation);
    }

    public void setTransformation(Transformation<I, O> transformation) {
        this.transformation = transformation;
    }

    public void withValidation(Validation validation) {

    }

    public InputType getInputType() {
        return inputType;
    }

}
