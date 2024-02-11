package com.lunova.moonbot.core.api.plugin.features.configuration;

public enum InputRenderType {
    TEXT_FIELD(InputSelector.USER),
    DROP_DOWN(InputSelector.PRE_DEFINED);

    InputRenderType(InputSelector inputSelector) {

    }
}
