package com.lunova.moonbot.core.api.plugin.features;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@JsonSerialize(using = FeatureSerializer.class)
public abstract class Feature extends ListenerAdapter implements FeatureComponent {

    @JsonProperty("name")
    private final String name;


    public Feature(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    @JsonProperty("settingGroup")
    @Override
    public final Optional<SettingGroup> getSettingGroup() {
        return Optional.fromNullable(defineSettingGroup(new SettingGroup.Builder()));
    }

    protected SettingGroup defineSettingGroup(SettingGroup.Builder builder) {
        return null;
    }

    protected abstract void install();
    protected abstract void uninstall();

}
