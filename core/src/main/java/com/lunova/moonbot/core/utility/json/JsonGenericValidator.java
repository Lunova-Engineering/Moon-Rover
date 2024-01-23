package com.lunova.moonbot.core.utility.json;

import com.lunova.moonbot.core.services.plugin.loader.PluginRequestValidator;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Utility class for validating objects using Jakarta Bean Validation.
 * <p>
 * This class provides a generic method to validate any given object
 * against constraints defined in its class definition.
 * </p>
 *
 */
public class JsonGenericValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginRequestValidator.class);
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    /**
     * Validates an object against its constraint annotations.
     *
     * @param <T> the type of the object to validate
     * @param obj the object to validate
     * @return true if the object passes all validation constraints
     * @throws ConstraintViolationException if validation constraints are violated
     */
    public static <T> void validateObject(T obj) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(obj);
        if (!violations.isEmpty()) {
            if(LOGGER.isDebugEnabled())
                logViolations(violations);
            throw new ConstraintViolationException("Object failed validation check.", violations);
        }
    }

    /**
     * Logs validation errors.
     *
     * @param violations a set of constraint violations
     * @param <T> the type of the object with violations
     */
    private static <T> void logViolations(Set<ConstraintViolation<T>> violations) {
        LOGGER.debug("Validation Errors:");
        violations.forEach(violation ->
                LOGGER.debug("Field: {}, Value: {}, Constraint: {}",
                        violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()));
    }

}
