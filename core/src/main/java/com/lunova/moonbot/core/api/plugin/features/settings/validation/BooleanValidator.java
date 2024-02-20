package com.lunova.moonbot.core.api.plugin.features.settings.validation;

import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.FlagStateRule;

import java.util.ArrayList;
import java.util.List;

public class BooleanValidator extends Validator<Boolean>{

    private BooleanValidator(Builder builder) {
        super("BOOLEAN_VALIDATOR", DataType.BOOLEAN, builder.rules);
    }

    public static class Builder {

        private final List<ValidationRule<Boolean>> rules = new ArrayList<>();

        public Builder setFlagStateRule(boolean target) {
            rules.add(new FlagStateRule(target));
            return this;
        }

        public BooleanValidator build() {
            return new BooleanValidator(this);
        }

    }

}
