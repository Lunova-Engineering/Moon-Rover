package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

import java.util.Objects;

public class AbsentValueRule<T> implements ValidationRule<T> {

    @Override
    public boolean validateRule(Object target) {
        return !Objects.isNull(target);
    }

}
