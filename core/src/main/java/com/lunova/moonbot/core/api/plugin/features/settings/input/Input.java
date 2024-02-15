package com.lunova.moonbot.core.api.plugin.features.settings.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.RangeSliderInput;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.SelectionInput;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.ToggleInput;
import com.lunova.moonbot.core.api.plugin.features.settings.input.impl.UserInput;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.Validation;

//@JsonSerialize(using = InputSerializer.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SelectionInput.class),
    @JsonSubTypes.Type(value = UserInput.class),
    @JsonSubTypes.Type(value = RangeSliderInput.class),
    @JsonSubTypes.Type(value = ToggleInput.class)
        })
public class Input<II, IO>  {
//TODO: Protect this class from modification, user should not be able to alter this class instance in the object

    private final InputFormat format;
    private final InputType type;
    private final String label;


    @JsonIgnore
    private Transformation<II, IO> transformation;
    @JsonProperty("validations")
    private Validation<II> validation;

    public Input(InputFormat format, InputType type, String label) {
        this.format = format;
        this.type = type;
        this.label = label;
    }

    public Optional<Transformation<II, IO>> getTransformation() {
        return Optional.fromNullable(transformation);
    }

    public void setTransformation(Transformation<II, IO> transformation) {
        this.transformation = transformation;
    }

    public InputFormat getFormat() {
        return format;
    }

    public String getLabel() {
        return label;
    }

    public Optional<Validation<II>> getValidation() {
        return Optional.fromNullable(validation);
    }

    public Input<II, IO> setValidation(Validation<II> validation) {
        this.validation = validation;
        return this;
    }

    public InputType getInputType() {
        return type;
    }

}
