package com.lunova.moonbot.core.api.plugin.common;

/**
 * Enumerates the detailed states of a plugin's installation or uninstallation process in the bot
 * system. These states provide granular control and visibility over the plugin lifecycle,
 * especially during the installation and uninstallation phases, allowing for actions to be taken at
 * specific points and for the system to respond appropriately to changes in state.
 */
public enum PluginInstallState {
    /** No installation or uninstallation process is underway. */
    NONE,
    INSTALLED,
    UNINSTALLED;
}
