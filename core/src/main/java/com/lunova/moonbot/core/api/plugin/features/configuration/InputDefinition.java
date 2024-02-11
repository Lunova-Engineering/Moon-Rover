package com.lunova.moonbot.core.api.plugin.features.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InputDefinition {

    private final InputDefinitionType inputDefinitionType;
    private final InputRenderType inputRenderType;
    private final Collection<Object> values = new ArrayList<>();


    // define the parameters for the input, this would include data type, validation techniques, (input field types, pre defined, user defined)

    // Input definition should represent all the information as far as the user input, how they give their response to set a value
    public InputDefinition(String name, InputDefinitionType inputDefinitionType, InputRenderType inputRenderType) {
        this.inputDefinitionType = inputDefinitionType;
        this.inputRenderType = inputRenderType;
    }

    public InputDefinition withDefinedValue(Object obj) {
        this.values.add(obj);
        return this;
    }

    public InputDefinition withDefinedValues(Collection<Object> values) {
        this.values.addAll(values);
        return this;
    }

    public InputDefinition withCustomValidation() {
        //Implement custom validation controls here, if not use default for InputDefinitionType
        return this;
    }

    public InputDefinition withTransformation() {
        //Implement transformation data, change the systems interpreted value of the users input(user input -> apply transform -> new data for server)
        return this;
    }

    public Collection<Object> getValues() {
        return values;
    }

    public InputDefinitionType getInputDefinitionType() {
        return inputDefinitionType;
    }

    public InputRenderType getInputRenderType() {
        return inputRenderType;
    }

    private List<String> convertDefinitionsToStrings() {
        return values.stream().map(Object::toString).collect(Collectors.toList());
    }
}
