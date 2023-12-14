package com.lunova.moonbot.core.plugin;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.14.2023
 */
public enum PluginInstallState {
    NONE,
    PRE_INSTALL,
    INSTALL,
    POST_INSTALL,
    PRE_UNINSTALL,
    UNINSTALL,
    POST_UNINSTALL,
    COMPLETE;

}
