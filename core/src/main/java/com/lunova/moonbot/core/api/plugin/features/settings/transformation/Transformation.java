package com.lunova.moonbot.core.api.plugin.features.settings.transformation;

public interface Transformation<INPUT_RETURN_TYPE, OUTPUT_TYPE> {
    OUTPUT_TYPE transform(INPUT_RETURN_TYPE object);
    void processTransformation(OUTPUT_TYPE object);
}
