package com.lunova.moonbot.core.api.plugin.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SettingGroupSerializer extends JsonSerializer<SettingGroup> {

    @Override
    public void serialize(SettingGroup value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }

}
