package com.lunova.moonbot.core.api.plugin.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
 * Some options might only be relevant if another option is set in a certain way. Implementing conditional options or dependencies between options can make the configuration more intuitive and less error-prone.
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
 * Provide a way for developers to add help text or links to documentation for each option, aiding end-users in understanding what each configuration option does.
 *
 * Validation Messages:
 * Customizable messages for when a user enters an invalid value, guiding them on the correct format or expected values.
 *
 * UI Hints:
 * For use in a GUI (like a web dashboard), allow developers to provide hints on how each option should be rendered, like checkboxes, sliders, text fields, color pickers, etc.
 *
 * By adding these features, you would greatly enhance the capabilities and flexibility of your configuration system, enabling developers to create more sophisticated and user-friendly configurations for their plugins.'
 *
 * Settings and Options
 * Redefine the structure of Options structure to give better naming conventions. Options should be Renamed to Settings and Settings should have new Objects permitted
 * called Options that represent options that would modify the behavior of the setting, for example.
 * SettingBuilder.newSetting("NAME", DESCRIPTION) - This portion would create a new setting (container almost) for modification
 * .addOption(new OptionData(DATA TYPE,STRING NAME OF OPTION, STRING OF DESCRIPTION.
 */


@JsonSerialize(using = SettingSerializer.class)
@JsonDeserialize(using = SettingDeserializer.class)
public final class Setting implements Serializable {
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String description;
    @JsonProperty
    private final OptionType optionType;
    @JsonProperty
    private final boolean required;
    @JsonProperty
    private final Object defaultValue;

    public static class Builder {
        private String name;
        private OptionType optionType;
        private Object defaultValue;
        private String description = "No description available";
        private boolean required = false;

        private final Set<Setting> settingsQueue = new HashSet<>();

        public Builder withOption(String name, OptionType optionType, Object defaultValue) {
            this.name = name;
            this.optionType = optionType;
            this.defaultValue = defaultValue;
            return this;
        }

        private void resetBuilder() {
            this.name = "DEFAULT_NAME";
            this.optionType = OptionType.STRING;
            this. defaultValue = "DEFAULT_VALUE";
            this.description = "No description available";
            this.required = false;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setRequired(boolean required) {
            this.required = required;
            return this;
        }

        public Builder queue() {
            settingsQueue.add(build());
            resetBuilder();
            return this;
        }

        public Setting build() {
            return new Setting(this);
        }

        public Set<Setting> getOptionQueue() {
            return settingsQueue;
        }

        public SettingGroup buildOptionGroup() {
            return buildOptionGroup(false);
        }

        public SettingGroup buildOptionGroup(boolean queue) {
            if(queue)
                queue();
            SettingGroup.Builder builder = new SettingGroup.Builder();
            builder.registerAllOption(settingsQueue);
            settingsQueue.clear();
            return builder.build();
        }



    }


    private Setting(Builder builder) {
        this.name = builder.name;
        this.optionType = builder.optionType;
        this.defaultValue = builder.defaultValue;
        this.description = builder.description;
        this.required = builder.required;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public boolean isRequired() {
        return required;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
