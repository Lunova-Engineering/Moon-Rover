package com.lunova.moonbot.core.api.plugin.features.settings;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class SettingGroupSerializer extends StdSerializer<SettingGroup> {

    public SettingGroupSerializer() {
        super(SettingGroup.class);
    }

    @Override
    public void serialize(SettingGroup value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject(); // Start of the root object

        // Manually serialize each setting with the desired key format
        int index = 1;
        for (Setting<?> setting : value.getSettings()) {
            String fieldName = "setting_" + index++;
            // Write field name for each setting dynamically
            gen.writeFieldName(fieldName);
            // Serialize the setting using its default serializer
            provider.defaultSerializeValue(setting, gen);
        }
        gen.writeEndObject(); // End of the root object
    }
}
