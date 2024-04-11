package com.lunova.moonbot.core.service.files.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.exceptions.JsonSerializationException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHandler {

    FilterProvider provider = new SimpleFilterProvider().addFilter("ignoreJDA", new SimpleBeanPropertyFilter() {
        @Override
        public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
            if (!writer.getName().equals("jda") || !writer.getName().equals("guild")) {
                writer.serializeAsField(pojo, jgen, provider);
            }
        }
    });

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT).setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .registerModules(new GuavaModule(), new Jdk8Module());

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);

    public static String serialize(Object obj) throws JsonSerializationException, ConstraintViolationException {
        try {
            JsonGenericValidator.validateObject(obj);
            return OBJECT_MAPPER.writer(new GsonPrettyPrinter()).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException(e.getMessage(), e);
        }
    }

    public static void registerModule(SimpleModule simpleModule) {
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    public static <T> T deserialize(String json, Class<T> clazz) throws JsonDeserializationException, ConstraintViolationException {
        try {
            T obj = OBJECT_MAPPER.readValue(json, clazz);
            JsonGenericValidator.validateObject(obj);
            return obj;
        } catch (JsonProcessingException e) {
            throw new JsonDeserializationException(e.getMessage(), e);
        }
    }

}
