package com.lunova.moonbot.core.api.plugin.features;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.features.configuration.SettingGroup;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Feature extends ListenerAdapter implements FeatureComponent {

    private final String name;

    public Feature() {
        this.name = getClass().getSimpleName();
    }

    public Feature(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

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
