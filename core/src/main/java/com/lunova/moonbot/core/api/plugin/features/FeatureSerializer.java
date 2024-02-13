package com.lunova.moonbot.core.api.plugin.features;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class FeatureSerializer extends StdSerializer<Feature> {

    public FeatureSerializer() {
        super(Feature.class);
    }

    @Override
    public void serialize(Feature value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject(); // start wrapping object
        gen.writeObjectFieldStart("feature"); // start 'feature' object
        gen.writeStringField("name", value.getName());
        // Serialize SettingGroup if present
        if (value.getSettingGroup().isPresent()) {
            gen.writeObjectField("settingGroup", value.getSettingGroup().get());
        } else {
            gen.writeNullField("settingGroup");
        }
        gen.writeEndObject(); // end 'feature' object
        gen.writeEndObject(); // end wrapping object
    }
}
