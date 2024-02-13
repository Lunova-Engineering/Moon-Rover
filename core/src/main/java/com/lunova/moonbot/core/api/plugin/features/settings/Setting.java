package com.lunova.moonbot.core.api.plugin.features.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.reflect.TypeToken;
import com.lunova.moonbot.core.api.plugin.features.settings.input.Input;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;

import java.io.Serializable;

/**
 * TODO AND NOTES
 *Value Range for Numerical Options:
 * For numerical options (like integers or floats), provide a way to specify minimum and maximum values. This is particularly useful for settings like volume control, timeout durations, or limits on certain resources.
 *
 * Regex Validation for String Options:
 * Allow developers to specify a regular expression for validating string inputs. This can be used to ensure that inputs like usernames, URLs, or custom command prefixes meet certain criteria.
 *
 * List and Enum Types:
 * Offer options that can take a list of values or are based on predefined enums. For example, a list of user roles, categories, or predefined themes. This helps in cases where the setting should be from a set of fixed values.
 *
 * Dependency or Conditional Options:
 * Some options might only be relevant if another option is set in a certain way. Implementing conditional options or dependencies between options can make the settings more intuitive and less error-prone.
 *
 * Multi-language Support:
 * Allowing developers to specify descriptions and names for options in multiple languages can make the bot more accessible to a diverse user base.
 *
 * Custom Data Types:
 * Beyond the basic data types (string, integer, boolean), allow for more complex data types like date-time formats, color codes, or even custom object types defined by the developer.
 *
 * Value Transformers:
 * Provide a way for developers to specify how the value of an option should be transformed before being applied. For instance, converting a string to uppercase, or a date string to a specific format.
 *
 * Dynamic Default Values:
 * Allow the setting of default values based on other configurations or external factors. For instance, defaulting a ‘greeting message’ option to the server name.
 *
 * Visibility Control:
 * Options to control the visibility of certain settings based on user roles or permissions. For example, some settings might only be visible to administrators.
 *
 * Help Text and Documentation:
 * Provide a way for developers to add help text or links to documentation for each option, aiding end-users in understanding what each settings option does.
 *
 * Validation Messages:
 * Customizable messages for when a user enters an invalid value, guiding them on the correct format or expected values.
 *
 * UI Hints:
 * For use in a GUI (like a web dashboard), allow developers to provide hints on how each option should be rendered, like checkboxes, sliders, text fields, color pickers, etc.
 *
 * By adding these features, you would greatly enhance the capabilities and flexibility of your settings system, enabling developers to create more sophisticated and user-friendly configurations for their plugins.'
 *
 * Settings and Options
 * Redefine the structure of Options structure to give better naming conventions. Options should be Renamed to Settings and Settings should have new Objects permitted
 * called Options that represent options that would modify the behavior of the setting, for example.
 * SettingCreator.newSetting("NAME", DESCRIPTION) - This portion would create a new setting (container almost) for modification
 * .addOption(new OptionData(DATA TYPE,STRING NAME OF OPTION, STRING OF DESCRIPTION.
 */


/*@JsonSerialize(using = SettingSerializer.class)
@JsonDeserialize(using = SettingDeserializer.class)*/
public final class Setting<T> implements Serializable {
    @JsonProperty("name")
    private final String key;
    @JsonProperty("required")
    private final boolean required;
    @JsonProperty("input")
    private final Input<?, T> input;

    //optional
    @JsonProperty("description")
    private final String description;
   @JsonIgnore
    private final TypeToken<?> returnType;


    public static class Builder<I, T> {
        private final String key;
        private final boolean required;
        private final Input<I, T> input;
        private String description;
        private final TypeToken<T> returnType;


        public Builder(String key, boolean required, Input<I, T> input) {
            this.key = key;
            this.required = required;
            this.input = input;
            this.returnType = new TypeToken<T>(){};
        }

        public Builder<I, T> withTransformation(Transformation<I, T> transformation) {
            input.setTransformation(transformation);
            return this;
        }

        public Builder<I, T> withValidation() {
            return this;
        }

        public Setting<T> build() {
            return new Setting<>(this);
        }


    }


    private Setting(Builder<?, T> builder) {
        this.key = builder.key;
        this.required = builder.required;
        this.input = builder.input;
        this.returnType = builder.returnType;
        this.description = builder.description;
    }

    public Input<?, T> getInput() {
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

    public TypeToken<?> getReturnType() {
        return returnType;
    }


}
