/**
 * Defines the core module for the Moon Bot application.
 *
 * <p>This module provides the foundational classes and interfaces that constitute the central
 * framework of the application. It is designed to support plugin-style additions, allowing for
 * extensibility and modular feature integration.
 *
 * <p>Future modules can depend on this core module to build additional features and plugins for the
 * Moon Bot application.
 *
 * <p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.11.2023
 */
module com.lunova.moonbot.core {
  requires net.dv8tion.jda;
  requires org.slf4j;
  requires ch.qos.logback.classic;
  requires ch.qos.logback.core;
}
