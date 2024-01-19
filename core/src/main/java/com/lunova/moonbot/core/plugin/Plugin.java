package com.lunova.moonbot.core.plugin;

import com.lunova.moonbot.core.exceptions.PluginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Abstract base class for all plugins, providing common structure and behavior for plugin lifecycle
 * management in the bot system. This includes installation, initialization, and uninstallation
 * routines, as well as basic plugin metadata.
 *
 * <p>Plugins are expected to extend this class and implement the necessary installation and
 * uninstallation behaviors specific to the plugin's functionality.
 */
public abstract class Plugin extends ListenerAdapter {

  /** The name of the plugin. */
  private final String name;

  /** The version of the plugin. */
  private final String version;

  /** Current installation state of the plugin. */
  private PluginInstallState pluginInstallState = PluginInstallState.NONE;

  /**
   * Constructs a new plugin with the given name and version.
   *
   * @param name The name of the plugin.
   * @param version The version of the plugin.
   */
  public Plugin(String name, String version) {
    this.name = name;
    this.version = version;
  }

  /**
   * Returns the name of the plugin.
   *
   * @return The name of the plugin.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the version of the plugin.
   *
   * @return The version of the plugin.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Executes the installation routine for this plugin. This method manages the transition of plugin
   * install states and calls the abstract installation methods.
   *
   * @param session The JDA session for interacting with Discord.
   * @param guildId The ID of the guild where the plugin is being installed.
   * @throws PluginException if there is an error during the installation process.
   */
  public void executeInstallRoutine(JDA session, String guildId) throws PluginException {
    try {
      if (pluginInstallState.equals(PluginInstallState.NONE)) {
        pluginInstallState = PluginInstallState.PRE_INSTALL;
        beforeInstall(session, guildId);
      }
      if (pluginInstallState.equals(PluginInstallState.PRE_INSTALL)) {
        pluginInstallState = PluginInstallState.INSTALL;
        install(session, guildId);
      }
      if (pluginInstallState.equals(PluginInstallState.INSTALL)) {
        pluginInstallState = PluginInstallState.POST_INSTALL;
        afterInstall(session, guildId);
      }
      pluginInstallState = PluginInstallState.COMPLETE;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Executes the uninstallation routine for this plugin. This method manages the transition of
   * plugin uninstall states and calls the abstract uninstallation methods.
   *
   * @param session The JDA session for interacting with Discord.
   * @param guildId The ID of the guild where the plugin is being uninstalled.
   * @throws PluginException if there is an error during the uninstallation process.
   */
  public void executeUninstallRoutine(JDA session, String guildId) throws PluginException {
    try {
      if (pluginInstallState.equals(PluginInstallState.NONE)) {
        pluginInstallState = PluginInstallState.PRE_UNINSTALL;
        beforeUninstall(session, guildId);
      }
      if (pluginInstallState.equals(PluginInstallState.PRE_UNINSTALL)) {
        pluginInstallState = PluginInstallState.UNINSTALL;
        uninstall(session, guildId);
      }
      if (pluginInstallState.equals(PluginInstallState.UNINSTALL)) {
        pluginInstallState = PluginInstallState.POST_UNINSTALL;
        afterUninstall(session, guildId);
      }
      pluginInstallState = PluginInstallState.COMPLETE;
    } catch (Exception e) {
      throw new PluginException(
          "Error executing plugin uninstall routine for plugin " + name + " version: " + version,
          e);
    }
  }

  /**
   * Placeholder for pre-installation logic. Subclasses should override this method to perform any
   * actions required before the plugin is installed.
   *
   * @param session The JDA session.
   * @param guildId The ID of the guild.
   */
  public void beforeInstall(JDA session, String guildId) {}

  /**
   * Placeholder for post-installation logic. Subclasses should override this method to perform any
   * actions required after the plugin is installed.
   *
   * @param session The JDA session.
   * @param guildId The ID of the guild.
   */
  public void afterInstall(JDA session, String guildId) {}

  /**
   * Placeholder for pre-uninstallation logic. Subclasses should override this method to perform any
   * actions required before the plugin is uninstalled.
   *
   * @param session The JDA session.
   * @param guildId The ID of the guild.
   */
  public void beforeUninstall(JDA session, String guildId) {}

  /**
   * Placeholder for post-uninstallation logic. Subclasses should override this method to perform
   * any actions required after the plugin is uninstalled.
   *
   * @param session The JDA session.
   * @param guildId The ID of the guild.
   */
  public void afterUninstall(JDA session, String guildId) {}

  /**
   * Abstract method for uninstalling the plugin. Subclasses must implement this method to provide
   * the specific uninstallation logic for the plugin.
   *
   * @param session The JDA session.
   * @param guildId The ID of the guild.
   */
  public abstract void uninstall(JDA session, String guildId);

  /**
   * Abstract method for installing the plugin. Subclasses must implement this method to provide the
   * specific installation logic for the plugin.
   *
   * @param session The JDA session.
   * @param guildId The ID of the guild.
   */
  public abstract void install(JDA session, String guildId);
}
