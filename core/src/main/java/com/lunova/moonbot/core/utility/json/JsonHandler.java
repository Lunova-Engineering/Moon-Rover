package com.lunova.moonbot.core.utility.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.exceptions.JsonSerializationException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);

    public static String serialize(Object obj) throws JsonSerializationException {
        try {
            JsonGenericValidator.validateObject(obj);
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException | ConstraintViolationException e) {
            throw new JsonSerializationException(e.getMessage(), e);
        }
    }


    public static <T> T deserialize(String json, Class<T> clazz) throws JsonDeserializationException {
        try {
            T obj = OBJECT_MAPPER.readValue(json, clazz);
            JsonGenericValidator.validateObject(obj);
            return obj;
        } catch (JsonProcessingException | ConstraintViolationException e) {
            throw new JsonDeserializationException(e.getMessage(), e);
        }
    }


}
