package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

import java.util.Objects;

public class AbsentValueRule<T> extends ValidationRule<T> {

    public AbsentValueRule() {
        super("ABSTRACT_VALUE_RULE");
    }

    @Override
    public boolean validateRule(Object target) {
        return !Objects.isNull(target);
    }

}
