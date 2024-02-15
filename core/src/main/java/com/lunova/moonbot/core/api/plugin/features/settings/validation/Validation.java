package com.lunova.moonbot.core.api.plugin.features.settings.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.AbsentValueRule;

import java.util.List;

/**
 * sub class implementations requiring extra rules
 *For STRING:
 *
 * UniquenessRule: Validates that the string is unique within a certain context or dataset (e.g., username, email).
 * ContentValidationRule: More complex content validations that might involve checking against a database or third-party service (e.g., profanity filter, copyright material).
 * For NUMBER:
 *
 * BusinessLogicValidationRule: Validates the number against business-specific rules that require server-side data or logic (e.g., inventory checks, budget limits).
 * For ARRAY:
 *
 * ContentUniquenessRule: Ensures all selected items in a multi-select are unique or meet certain criteria not verifiable on the client-side.
 *
 * copied and pasted from CGPT because am lazy...
 *
 * @param <T>
 */
public abstract class Validation<T> {

    @JsonProperty("type")
    private final DataType dataType;
    @JsonProperty("rules")
    private final List<ValidationRule<T>> rules;

    protected Validation(DataType dataType, List<ValidationRule<T>> rules) {
        this.dataType = dataType;
        this.rules = rules;
        rules.add(new AbsentValueRule<>());
    }

    public DataType getDataType() {
        return dataType;
    }

    public List<ValidationRule<T>> getRules() {
        return rules;
    }

    protected boolean validateTarget(T target) {
        return rules.stream().allMatch(rule -> rule.validateRule(target));
    }
}
