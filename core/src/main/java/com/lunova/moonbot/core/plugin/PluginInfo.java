package com.lunova.moonbot.core.plugin;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Represents the basic information of a plugin in the bot system. This class is used to hold and
 * manage metadata about a plugin, such as its name, version, and main class. It is annotated for
 * XML parsing to allow easy reading and writing of plugin info to and from XML configurations.
 */
@XmlRootElement(name = "plugin-info")
public class PluginInfo {

  /** The name of the plugin. */
  private String name;

  /** The version of the plugin. */
  private String version;

  /** The fully qualified name of the plugin's main class. */
  private String mainClass;

  /**
   * Gets the name of the plugin.
   *
   * @return The name of the plugin.
   */
  @XmlElement
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the plugin.
   *
   * @param name The name to set for the plugin.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the version of the plugin.
   *
   * @return The version of the plugin.
   */
  @XmlElement
  public String getVersion() {
    return version;
  }

  /**
   * Sets the version of the plugin.
   *
   * @param version The version to set for the plugin.
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Gets the fully qualified name of the plugin's main class.
   *
   * @return The fully qualified name of the main class.
   */
  @XmlElement(name = "mainClass")
  public String getMainClass() {
    return mainClass;
  }

  /**
   * Sets the fully qualified name of the plugin's main class.
   *
   * @param mainClass The fully qualified name of the main class to set for the plugin.
   */
  public void setMainClass(String mainClass) {
    this.mainClass = mainClass;
  }
}
