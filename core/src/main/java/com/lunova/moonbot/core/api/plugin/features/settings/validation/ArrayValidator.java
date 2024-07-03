package com.lunova.moonbot.core.api.plugin.features.settings.validation;

import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.MinimumValueRule;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.RangeValueRule;

import java.util.ArrayList;
import java.util.List;

public class ArrayValidator<T> extends Validator<ArrayList<T>> {

    protected ArrayValidator(
            String identifier,
            DataType dataType,
            List<ValidationRule<ArrayList<T>>> validationRules) {
        super("ARRAY_VALIDATOR", DataType.ARRAY, validationRules);
    }

    public static class Builder<T> {
        private final List<ValidationRule<List<T>>> rules = new ArrayList<>();

        public Builder<T> setMinimumElements(int minimum) {
            rules.add(
                    new MinimumValueRule<>(minimum) {
                        @Override
                        public boolean validateRule(List<T> target) {
                            return target.size() >= getValue().intValue();
                        }
                    });
            return this;
        }

        public Builder<T> setMaximumElements(int maximum) {
            rules.add(
                    new MinimumValueRule<>(maximum) {
                        @Override
                        public boolean validateRule(List<T> target) {
                            return target.size() <= getValue().intValue();
                        }
                    });
            return this;
        }

        public Builder<T> setElementRange(int minimum, int maximum) {
            rules.add(
                    new RangeValueRule<>(minimum, maximum) {

                        @Override
                        public boolean validateRule(List<T> target) {
                            return target.size() >= getMin().intValue()
                                    && target.size() <= getMax().intValue();
                        }
                    });
            return this;
        }
    }
}
