package com.lunova.moonbot.core.api.plugin.features.settings.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.gson.reflect.TypeToken;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.Validation;

public class Input<I, O>  {

    @JsonProperty("format")
    private final InputFormat format;
    @JsonProperty("type")
    private final InputType type;
    @JsonProperty("label")
    private final String label;

    @JsonIgnore
    private final TypeToken<I> inputType;

    @JsonIgnore
    private Transformation<I, O> transformation;
    @JsonIgnore
    private Validation validation;

    public Input(InputFormat format, InputType type, String label) {
        this.format = format;
        this.type = type;
        this.label = label;
        this.inputType = new TypeToken<I>(){};
    }

    public Optional<Transformation<I, O>> getTransformation() {
        return Optional.fromNullable(transformation);
    }

    public void setTransformation(Transformation<I, O> transformation) {
        this.transformation = transformation;
    }

    public void withValidation(Validation validation) {

    }

    public TypeToken<I> getInputType() {
        return inputType;
    }

    public InputType getType() {
        return type;
    }

}
