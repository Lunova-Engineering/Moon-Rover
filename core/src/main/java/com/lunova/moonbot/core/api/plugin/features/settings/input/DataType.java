package com.lunova.moonbot.core.api.plugin.features.settings.input;

import java.util.ArrayList;

public enum DataType {
    NUMBER(Number.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    DOUBLE(Double.class),
    FLOAT(Float.class),
    ARRAY(ArrayList.class),
    NULL(Object.class),
    OBJECT(Object.class),
    UNKNOWN(Object.class);

    private final Class<?> clazz;

    DataType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
