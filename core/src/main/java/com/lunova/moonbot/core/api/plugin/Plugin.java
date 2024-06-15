package com.lunova.moonbot.core.api.plugin;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.api.plugin.common.PluginInstallState;
import com.lunova.moonbot.core.api.plugin.common.PluginToggleState;
import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.FeatureManager;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Abstract base class for all plugins, providing common structure and behavior for plugin lifecycle
 * management in the bot system. This includes installation, initialization, and uninstallation
 * routines, as well as basic plugin metadata.
 *
 * <p>Plugins are expected to extend this class and implement the necessary installation and
 * uninstallation behaviors specific to the plugin's functionality.
 */
public abstract class Plugin implements Configurable, Toggleable {

    // TODO: Investigate for JSON serializing and deserializing information for consistent plugin
    // state upon restarts.
    // TODO: Future the JSON will be written to a file or a string value and sent to the database
    // and
    // retrieved for continuity.
    // TODO: IDK about that but we will see...

    private final String name;
    private final UUID uuid;
    private PluginInstallState installState;
    private PluginToggleState toggleState;
    private final FeatureManager featureManager;

    public String getName() {
        return name;
    }

    public Plugin() {
        this.name = "Default Name";
        this.uuid = UUID.randomUUID();
        this.installState = PluginInstallState.NONE;
        this.toggleState = PluginToggleState.DISABLED;
        this.featureManager = new FeatureManager();
    }

    public Plugin(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.installState = PluginInstallState.NONE;
        this.toggleState = PluginToggleState.DISABLED;
        this.featureManager = new FeatureManager();
        registerFeatures();
        featureManager.seal();
        initialize();
    }

    private void initialize() {
        featureManager
                .getFeatureContainers()
                .forEach(
                        container -> {
                            container.getFeatures().forEach(Feature::install);
                        });
    }

    public UUID getUuid() {
        return uuid;
    }

    public final PluginInstallState getInstallState() {
        return installState;
    }

    public final FeatureManager getFeatureManager() {
        return featureManager;
    }

    public final void setInstallState(PluginInstallState installState) {
        this.installState = installState;
    }

    public final PluginToggleState getToggleState() {
        return toggleState;
    }

    public final void setToggleState(PluginToggleState toggleState) {
        this.toggleState = toggleState;
    }

    @Override
    public Optional<SettingGroup> getSettingGroup() {
        SettingGroup.Builder sg = new SettingGroup.Builder();
        Stream<Setting<?>> ss =
                featureManager.getFeatures().stream()
                        .flatMap(feature -> feature.getSettingGroup().get().getSettings().stream());
        ss.forEach(sg::withSetting);
        return Optional.of(sg.build());
    }

    public abstract void registerFeatures();
}
