package com.lunova.moonbot.core.api.plugin;

import com.lunova.moonbot.core.api.plugin.common.PluginInstallState;
import com.lunova.moonbot.core.api.plugin.common.PluginToggleState;
import com.lunova.moonbot.core.api.plugin.features.FeatureGroup;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.UUID;

/**
 * Abstract base class for all plugins, providing common structure and behavior for plugin lifecycle
 * management in the bot system. This includes installation, initialization, and uninstallation
 * routines, as well as basic plugin metadata.
 *
 * <p>Plugins are expected to extend this class and implement the necessary installation and
 * uninstallation behaviors specific to the plugin's functionality.
 */
public abstract class Plugin extends ListenerAdapter {

  //TODO: Investigate for JSON serializing and deserializing information for consistient plugin state upon restarts.
  //TODO: Future the JSON will be written to a file or a string value and sent to the database and retrieved for continuity.
  //TODO: IDK about that but we will see...

  /**
   * Notes:
   *  - Name and version don't need to be included here, these should be included in the manifest of the jar file.
   *    If the developer wants to reference the plugin name and version the dev can incorporate it into local values.
   */

  //Plugin class values
  private final UUID uuid;

  //State management for plugin
  private PluginInstallState installState;
  private PluginToggleState toggleState;

  //Feature Management
  private List<FeatureGroup> featureGroups;




  public Plugin() {
    this.uuid = UUID.randomUUID();
    this.installState = PluginInstallState.NONE;
    this.toggleState = PluginToggleState.DISABLED;
  }

  public static void foo() {

  }

}
