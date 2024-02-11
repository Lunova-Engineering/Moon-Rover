package com.lunova.moonbot.core.api.plugin.features.settings.definitions;

import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;

public abstract class InputDefinition<I, O> {

    private Transformation<I, O> transformation;


    protected InputDefinition<I, O> withTransformation(Transformation<I, O> transformation) {
        this.transformation = transformation;
        return this;
    }

    protected abstract InputDefinition<I, O> withValidation();

}
