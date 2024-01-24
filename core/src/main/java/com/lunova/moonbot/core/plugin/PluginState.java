package com.lunova.moonbot.core.plugin;

/**
 * Enumerates the possible states of a plugin in the bot system. These states represent the
 * lifecycle of a plugin from the point it is added to the system until it is removed or disabled.
 * This lifecycle management allows for clear understanding and control of plugin behavior and
 * status within the system.
 */
public enum PluginState {
  /** Plugin is not installed in the system. */
  UNINSTALLED,

  /** Plugin is in the process of being installed. */
  INSTALLING,

  /** Plugin is installed and presumably ready for use. */
  INSTALLED,

  /** Plugin is installed but has been disabled and is not active. */
  DISABLED,

  /** Plugin is in the process of being uninstalled from the system. */
  UNINSTALLING
}
