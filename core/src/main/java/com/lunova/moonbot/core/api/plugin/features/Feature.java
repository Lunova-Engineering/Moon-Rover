package com.lunova.moonbot.core.api.plugin.features;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Feature extends ListenerAdapter implements FeatureComponent {

    @JsonProperty("featureName")
    private final String name;


    public Feature(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    @JsonProperty("featureSettingGroup")
    @Override
    public final Optional<SettingGroup> getSettingGroup() {
        return Optional.fromNullable(defineSettingGroup());
    }

    protected SettingGroup defineSettingGroup() {
        return null;
    }

    protected abstract void install();
    protected abstract void uninstall();

}
