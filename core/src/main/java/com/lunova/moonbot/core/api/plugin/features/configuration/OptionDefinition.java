package com.lunova.moonbot.core.api.plugin.features.configuration;

import java.util.HashMap;
import java.util.Map;

public class OptionDefinition {

    private Map<String, InputDefinition> inputDefinitionMap = new HashMap<>();

    //defines an option deifnition, an option is a complex object that should within it contain options from a drop down selector
    //that defines the remaining fields of a particular setting. an example is a drop down to select allow all channels
    //or whitelist / blacklist and a new drop down for selecting which channels should be added

    //OptionDefinition should represent a portion of the option such as a drop down for selectors that modify the input definition
    public OptionDefinition(String key, InputDefinition inputDefinition) {
        inputDefinitionMap.put(key, inputDefinition);
    }

    public OptionDefinition withAdditionalPair(String key, InputDefinition inputDefinition) {
        inputDefinitionMap.put(key, inputDefinition);
        return this;
    }

    public OptionDefinition withAdditionalPairs(Map<String, InputDefinition> inputDefinitions) {
        inputDefinitionMap.putAll(inputDefinitions);
        return this;
    }
}
