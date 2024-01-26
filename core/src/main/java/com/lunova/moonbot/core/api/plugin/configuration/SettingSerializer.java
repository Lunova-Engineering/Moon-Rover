package com.lunova.moonbot.core.api.plugin.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SettingSerializer extends JsonSerializer<Setting> {

    @Override
    public void serialize(Setting value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }

}
