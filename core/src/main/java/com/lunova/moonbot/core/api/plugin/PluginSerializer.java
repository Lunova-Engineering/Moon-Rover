package com.lunova.moonbot.core.api.plugin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PluginSerializer extends JsonSerializer<Plugin> {

    @Override
    public void serialize(Plugin value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", value.getName());
        gen.writeStringField("uuid", value.getUuid().toString());
        gen.writeStringField("installState", value.getInstallState().name());
        gen.writeStringField("toggleState", value.getToggleState().name());
        gen.writeObject(value.getFeatureManager());
        gen.writeEndObject();
    }

}
