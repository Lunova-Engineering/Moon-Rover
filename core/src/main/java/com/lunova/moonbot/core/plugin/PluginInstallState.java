package com.lunova.moonbot.core.plugin;

/**
 * Enumerates the detailed states of a plugin's installation or uninstallation process in the bot
 * system. These states provide granular control and visibility over the plugin lifecycle,
 * especially during the installation and uninstallation phases, allowing for actions to be taken at
 * specific points and for the system to respond appropriately to changes in state.
 */
public enum PluginInstallState {
  /** No installation or uninstallation process is underway. */
  NONE,

  /** Pre-installation phase where preliminary checks and preparations occur. */
  PRE_INSTALL,

  /** Active installation phase where the plugin's files and dependencies are being set up. */
  INSTALL,

  /** Post-installation phase for finalizing installation, such as cleanup and verification. */
  POST_INSTALL,

  /** Pre-uninstallation phase for preparatory actions before a plugin is removed. */
  PRE_UNINSTALL,

  /** Active uninstallation phase where the plugin is being removed from the system. */
  UNINSTALL,

  /** Post-uninstallation phase for cleanup and updates following a plugin's removal. */
  POST_UNINSTALL,

  /** The plugin has completed its installation or uninstallation process. */
  COMPLETE
}
