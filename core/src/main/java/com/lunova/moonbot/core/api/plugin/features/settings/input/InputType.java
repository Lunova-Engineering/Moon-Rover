package com.lunova.moonbot.core.api.plugin.features.settings.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InputType {

    //User Inputs
    TEXT_FIELD(DataType.STRING),
    PASSWORD(DataType.STRING),
    EMAIL(DataType.STRING),
    DATE(DataType.STRING),
    COLOR(DataType.STRING),
    URL(DataType.STRING),
    TEXTAREA(DataType.STRING),
    SEARCH_FIELD(DataType.STRING),
    TIME(DataType.STRING),
    NUMBER_INPUT(DataType.NUMBER),

    //Range Inputs;
    RANGE_SLIDER(DataType.NUMBER),

    //Selection Inputs
    DROP_DOWN(DataType.STRING),
    MULTI_SELECT_DROP_DOWN(DataType.ARRAY),
    RADIO_BUTTONS(DataType.STRING),

    //Toggle Inputs
    TOGGLE_SWITCH(DataType.BOOLEAN),
    CHECKBOX(DataType.BOOLEAN);

    @JsonProperty("returnInputType")
    private final DataType dataType;

    InputType(DataType dataType) {
        this.dataType = dataType;
    }

    InputType(DataType dataType, Class<String> stringClass) {
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }
}
