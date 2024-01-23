package com.lunova.moonbot.core.services.plugin.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lunova.moonbot.core.exceptions.PluginRequestException;
import com.lunova.moonbot.core.services.plugin.PluginRequest;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class PluginRequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginRequestValidator.class);
    private static final Gson GSON = new GsonBuilder().create();
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    public static PluginRequest validateJsonRequest(String json) throws PluginRequestException {
        try {
            Validator validator = VALIDATOR_FACTORY.getValidator();
            PluginRequest request = GSON.fromJson(json, PluginRequest.class);
            Set<ConstraintViolation<PluginRequest>> violations = validator.validate(request);

            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            return request;

        } catch (JsonSyntaxException e) {
            LOGGER.warn("JSON parsing error for request: {}", json, e);
            throw new PluginRequestException("Failed to deserialize JSON request: " + json, e);

        } catch (ConstraintViolationException exception) {
            LOGGER.warn("Validation failed for plugin request. Validation errors:");
            exception.getConstraintViolations().forEach(violation ->
                    LOGGER.warn("Field: {}, Value: {}, Constraint: {}",
                            violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()));
            LOGGER.debug("Invalid JSON: {}", json);
            throw new PluginRequestException("Validation error in JSON request", exception);
        }
    }
}
