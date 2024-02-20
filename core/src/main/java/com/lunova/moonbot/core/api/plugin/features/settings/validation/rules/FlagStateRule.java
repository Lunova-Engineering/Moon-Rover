package com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;

import com.lunova.moonbot.core.api.plugin.features.settings.validation.ValidationRule;

public class FlagStateRule extends ValidationRule<Boolean> {

    private final boolean flag;

    public FlagStateRule(boolean flag) {
        super("FLAG_STATE_RULE");
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    //TODO: Investigate for ways to provide custom logic to ensure correct flag being set based on XYZ conditions.
    @Override
    public boolean validateRule(Boolean target) {
        return flag == target;
    }

}
