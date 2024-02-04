package com.lunova.moonbot.core.api.plugin;

import com.lunova.moonbot.core.api.plugin.common.PluginInstallState;
import com.lunova.moonbot.core.api.plugin.common.PluginToggleState;
import com.lunova.moonbot.core.api.plugin.features.FeatureManager;

import java.util.UUID;

/**
 * Abstract base class for all plugins, providing common structure and behavior for plugin lifecycle
 * management in the bot system. This includes installation, initialization, and uninstallation
 * routines, as well as basic plugin metadata.
 *
 * <p>Plugins are expected to extend this class and implement the necessary installation and
 * uninstallation behaviors specific to the plugin's functionality.
 */
public abstract class Plugin implements Configurable, Toggleable {

  //TODO: Investigate for JSON serializing and deserializing information for consistent plugin state upon restarts.
  //TODO: Future the JSON will be written to a file or a string value and sent to the database and retrieved for continuity.
  //TODO: IDK about that but we will see...

  private final UUID uuid;
  private PluginInstallState installState;
  private PluginToggleState toggleState;

  private final FeatureManager featureManager;

  public Plugin() {
    this.uuid = UUID.randomUUID();
    this.installState = PluginInstallState.NONE;
    this.toggleState = PluginToggleState.DISABLED;
    this.featureManager = new FeatureManager();
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

  protected abstract void registerFeatures();
}
