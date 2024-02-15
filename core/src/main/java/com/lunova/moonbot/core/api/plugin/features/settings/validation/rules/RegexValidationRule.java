package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

import java.util.regex.Pattern;

public class RegexValidationRule implements ValidationRule<String> {

    private final Pattern regex;

    public RegexValidationRule(String regex) {
        this.regex = Pattern.compile(regex);
    }

    @Override
    public boolean validateRule(String target) {
        return regex.pattern().matches(target);
    }

    public Pattern getRegex() {
        return regex;
    }

}
