package com.lunova.moonbot.core.api.plugin.features.settings.definitions.impl;

import com.lunova.moonbot.core.api.plugin.features.settings.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.SettingDefinition;

import java.util.ArrayList;
import java.util.Collection;

public class SelectionSettingDefinition<I, T> extends SettingDefinition<I, T> {


    private final Collection<String> collection = new ArrayList<>();
    public SelectionSettingDefinition(String label, Collection<?> collection, Input<I, T> input) {
        super(label, input);
        this.collection.addAll(collection.stream().map(Object::toString).toList());
    }

    public Collection<String> getCollection() {
        return collection;
    }

}
