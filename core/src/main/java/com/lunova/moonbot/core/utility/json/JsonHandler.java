package com.lunova.moonbot.core.utility.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.exceptions.JsonSerializationException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT).setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
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
