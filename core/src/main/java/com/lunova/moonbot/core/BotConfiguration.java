package com.lunova.moonbot.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import com.lunova.moonbot.core.exceptions.ConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The BotConfiguration class is responsible for loading and providing access to configuration
 * properties. It encapsulates the properties handling mechanism by reading them from a server
 * configuration file and providing them as an ImmutableMap.
 */
public class BotConfiguration {
  /**
   * LOGGER is a static final Logger instance used throughout the BotConfiguration class to log
   * important information and error messages related to the configuration loading and accessing
   * processes.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(BotConfiguration.class);

  /**
   * PROPERTY_TOKENS is a static final ImmutableMap that stores the properties loaded from the
   * configuration file. It's populated at class initialization time by reading the properties file
   * and transforming its contents into an immutable map.
   */
  private static final ImmutableMap<String, String> PROPERTY_TOKENS = readProperties();

  /** Private constructor to prevent instantiation of this utility class. */
  private BotConfiguration() {}

  /**
   * Retrieves the property value associated with the specified key from the application's
   * configuration.
   *
   * @param key the property key
   * @return the property value as a String
   * @throws ConfigurationException if the key is not found in the configuration
   */
  public static String getProperty(String key) throws ConfigurationException {
    if (!PROPERTY_TOKENS.containsKey(key)) {
      throw new ConfigurationException("Property with key '" + key + "' not found.");
    }
    return PROPERTY_TOKENS.get(key);
  }

  /**
   * Reads properties from the server configuration file and returns them as an ImmutableMap. Logs a
   * warning for any property with a null or empty value and logs errors for issues accessing the
   * file.
   *
   * @return an ImmutableMap of property keys to values
   */
  private static ImmutableMap<String, String> readProperties() {
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
    try {
      Properties properties = loadPropertiesFromFile("server_configuration.properties");
      for (String key : properties.stringPropertyNames()) {
        String value = properties.getProperty(key);
        if (value != null && !value.isEmpty()) {
          builder.put(key, value);
        } else {
          LOGGER.warn("Null or empty value for property key: " + key);
        }
      }
    } catch (IllegalArgumentException e) {
      LOGGER.error("Configuration file not found.", e);
      return ImmutableMap.of();
    } catch (IOException e) {
      LOGGER.error("Error reading or parsing the configuration file.", e);
      return ImmutableMap.of();
    }
    return builder.build();
  }

  /**
   * Loads properties from a file into a Properties object. It reads the file using the classpath
   * and converts it into a string which is then loaded into Properties.
   *
   * @param fileName the name of the file to load the properties from
   * @return a Properties object containing the loaded properties
   * @throws IOException if there is an issue reading or parsing the file
   */
  private static Properties loadPropertiesFromFile(String fileName) throws IOException {
    URL resourceUrl = Resources.getResource(fileName);
    String text = Resources.toString(resourceUrl, StandardCharsets.UTF_8);
    Properties properties = new Properties();
    properties.load(new StringReader(text));
    return properties;
  }
}
