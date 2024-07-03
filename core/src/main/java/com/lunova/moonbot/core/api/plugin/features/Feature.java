package com.lunova.moonbot.core.api.plugin.features;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.ToggleState;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

// @JsonSerialize(using = FeatureSerializer.class)
public abstract class Feature extends ListenerAdapter implements FeatureComponent {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("settingGroup")
    private final SettingGroup settingGroup;

    @JsonProperty("toggleState")
    private ToggleState toggleState;

    public Feature(String name) {
        this.name = name;
        this.settingGroup = defineSettingGroup(new SettingGroup.Builder());
    }

    public final String getName() {
        return name;
    }

    @Override
    public final Optional<SettingGroup> getSettingGroup() {
        return Optional.fromNullable(settingGroup);
    }

    protected SettingGroup defineSettingGroup(SettingGroup.Builder builder) {
        return null;
    }

    public abstract void install();

    public abstract void uninstall();
}
