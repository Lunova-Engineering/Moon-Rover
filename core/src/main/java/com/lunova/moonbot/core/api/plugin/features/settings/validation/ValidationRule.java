package com.lunova.moonbot.core.api.plugin.features.settings.validation;

public abstract class ValidationRule<T> {

     private final String identifier;

     public ValidationRule(String identifier) {
          this.identifier = identifier;
     }

     public String getIdentifier() {
          return identifier;
     }

     protected abstract boolean validateRule(T target);
}
