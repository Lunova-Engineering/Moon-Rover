package com.lunova.moonbot.core.api.plugin.features.settings.validation;

import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.MaximumValueRule;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.MinimumValueRule;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.RangeValueRule;

import java.util.ArrayList;
import java.util.List;

public class NumberValidator<T extends Number> extends Validator<T> {

    protected NumberValidator(Builder<T> builder) {
        super("NUMBER_VALIDATOR", DataType.NUMBER, builder.rules);
    }

    public static class Builder<T extends Number> {

        private final List<ValidationRule<T>> rules = new ArrayList<>();

        public Builder<T> setMinimumLength(T minimum) {
            rules.add(
                    new MinimumValueRule<>(minimum) {
                        @Override
                        public boolean validateRule(T target) {
                            return target.doubleValue() >= getValue().doubleValue();
                        }
                    });
            return this;
        }

        public Builder<T> setMaximumLength(T maximum) {
            rules.add(
                    new MaximumValueRule<>(maximum) {
                        @Override
                        public boolean validateRule(T target) {
                            return maximum.doubleValue() <= target.doubleValue();
                        }
                    });
            return this;
        }

        public Builder<T> setRangeLength(T minimum, T maximum) {
            rules.add(
                    new RangeValueRule<>(minimum, maximum) {

                        @Override
                        public boolean validateRule(Number target) {
                            return minimum.doubleValue() >= getMin().doubleValue()
                                    && maximum.doubleValue() <= getMax().doubleValue();
                        }
                    });
            return this;
        }

        public NumberValidator<T> build() {
            return new NumberValidator<T>(this);
        }
    }
}
