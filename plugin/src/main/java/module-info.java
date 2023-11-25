/**
 * Defines the plugin module for the Moon Bot application.
 *
 * <p>This module is an extension of the core Moon Bot application, enabling third-party developers
 * to create and integrate their own plugins. It includes essential logging capabilities and
 * interfaces for plugin development.
 *
 * <p>By leveraging this module, developers can build plugins that seamlessly integrate with the
 * Moon Bot ecosystem, enhancing its functionality and user experience.
 *
 * <p>Plugins developed with this module require the core Moon Bot module to function.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.25.2023
 */
module moon.bot.plugin {
  // requires net.dv8tion.jda;
  requires org.slf4j;
  requires ch.qos.logback.classic;
  requires ch.qos.logback.core;
}
