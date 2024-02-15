package com.lunova.moonbot.core.api.plugin.features.settings.validation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ValidationRule<T> {

     boolean validateRule(T target);
}
