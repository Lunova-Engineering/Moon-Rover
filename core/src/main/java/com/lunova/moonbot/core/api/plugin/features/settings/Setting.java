package com.lunova.moonbot.core.api.plugin.features.settings;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.Validator;

import java.io.Serializable;

/**
 * TODO AND NOTES Value Range for Numerical Options: For numerical options (like integers or
 * floats), provide a way to specify minimum and maximum values. This is particularly useful for
 * settings like volume control, timeout durations, or limits on certain resources.
 *
 * <p>Regex Validator for String Options: Allow developers to specify a regular expression for
 * validating string inputs. This can be used to ensure that inputs like usernames, URLs, or custom
 * command prefixes meet certain criteria.
 *
 * <p>List and Enum Types: Offer options that can take a list of values or are based on predefined
 * enums. For example, a list of user roles, categories, or predefined themes. This helps in cases
 * where the setting should be from a set of fixed values.
 *
 * <p>Dependency or Conditional Options: Some options might only be relevant if another option is
 * set in a certain way. Implementing conditional options or dependencies between options can make
 * the settings more intuitive and less error-prone.
 *
 * <p>Multi-language Support: Allowing developers to specify descriptions and names for options in
 * multiple languages can make the bot more accessible to a diverse user base.
 *
 * <p>Custom Data Types: Beyond the basic data types (string, integer, boolean), allow for more
 * complex data types like date-time formats, color codes, or even custom object types defined by
 * the developer.
 *
 * <p>Value Transformers: Provide a way for developers to specify how the value of an option should
 * be transformed before being applied. For instance, converting a string to uppercase, or a date
 * string to a specific format.
 *
 * <p>Dynamic Default Values: Allow the setting of default values based on other configurations or
 * external factors. For instance, defaulting a ‘greeting message’ option to the server name.
 *
 * <p>Visibility Control: Options to control the visibility of certain settings based on user roles
 * or permissions. For example, some settings might only be visible to administrators.
 *
 * <p>Help Text and Documentation: Provide a way for developers to add help text or links to
 * documentation for each option, aiding end-users in understanding what each settings option does.
 *
 * <p>Validator Messages: Customizable messages for when a user enters an invalid value, guiding
 * them on the correct format or expected values.
 *
 * <p>UI Hints: For use in a GUI (like a web dashboard), allow developers to provide hints on how
 * each option should be rendered, like checkboxes, sliders, text fields, color pickers, etc.
 *
 * <p>By adding these features, you would greatly enhance the capabilities and flexibility of your
 * settings system, enabling developers to create more sophisticated and user-friendly
 * configurations for their plugins.'
 *
 * <p>Settings and Options Redefine the structure of Options structure to give better naming
 * conventions. Options should be Renamed to Settings and Settings should have new Objects permitted
 * called Options that represent options that would modify the behavior of the setting, for example.
 * SettingCreator.newSetting("NAME", DESCRIPTION) - This portion would create a new setting
 * (container almost) for modification .addOption(new OptionData(DATA TYPE,STRING NAME OF OPTION,
 * STRING OF DESCRIPTION.
 */
@JsonPropertyOrder({"key", "inputClass", "outputClass", "description", "required", "input"})
public final class Setting<SO> implements Serializable {

    private final String key;
    private final boolean required;
    private final Input<?, SO> input;

    private final String description;

    private final Class<?> inputClass;
    private final Class<?> outputClass;

    public static class Builder<BI, BO> {
        private final String key;
        private final boolean required;
        private final Input<BI, BO> input;
        private String description;
        private Class<?> inputClass;
        private Class<?> outputClass;

        public Builder(String key, boolean required, Input<BI, BO> input) {
            this.key = key;
            this.required = required;
            this.input = input;
        }

        public Builder<BI, BO> withTransformation(Transformation<BI, BO> transformation) {
            input.setTransformation(transformation);
            return this;
        }

        public Builder<BI, BO> withTyping(Class<?> input, Class<?> output) {
            inputClass = input;
            outputClass = output;
            return this;
        }

        public Builder<BI, BO> setDescription(String description) {
            this.description = description;
            return this;
        }

        public <V extends Validator<BI>> Builder<BI, BO> withValidation(V validation) {
            input.setValidation(validation);
            return this;
        }

        public Setting<BO> build() {
            return new Setting<>(this);
        }
    }

    private Setting(Builder<?, SO> builder) {
        this.key = builder.key;
        this.required = builder.required;
        this.input = builder.input;
        this.description = builder.description;
        this.inputClass = builder.inputClass;
        this.outputClass = builder.outputClass;
    }

    public Input<?, SO> getInput() {
        return input;
    }

    public String getKey() {
        return key;
    }

    public boolean isRequired() {
        return required;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getInputClass() {
        return inputClass;
    }

    public Class<?> getOutputClass() {
        return outputClass;
    }
}
