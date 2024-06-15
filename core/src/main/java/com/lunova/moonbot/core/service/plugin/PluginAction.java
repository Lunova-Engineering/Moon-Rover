package com.lunova.moonbot.core.service.plugin;

/**
 * Enumerates the possible actions that can be performed on a plugin within the system. These
 * actions dictate how the system should interact with and manage individual plugins, whether it's
 * to make them operational, remove them, or change their active status.
 */
public enum PluginAction {
    /** Represents the action of installing a plugin into the system. */
    INSTALL,

    /** Represents the action of removing a plugin from the system. */
    UNINSTALL,

    /** Represents the action of enabling a plugin, making it active. */
    ENABLE,

    /** Represents the action of disabling a plugin, making it inactive. */
    DISABLE
}
