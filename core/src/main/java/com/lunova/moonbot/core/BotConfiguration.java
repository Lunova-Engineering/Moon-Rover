package com.lunova.moonbot.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import com.lunova.moonbot.core.exceptions.PropertyNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the configuration for the bot.
 *
 * <p>This class loads configuration properties from a file and provides access to them. It uses a
 * static initializer block to ensure properties are loaded when the class is first used.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class BotConfiguration {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BotConfiguration.class);

  /** Stores the bot key-value pairs for configuration properties. */
  private static final ImmutableMap<String, String> PROPERTY_TOKENS = readProperties();

  /** Private constructor to prevent instantiation. */
  private BotConfiguration() {}

  /**
   * Retrieves the property value for a specified key.
   *
   * <p>This method throws a {@code PropertyNotFoundException} if the key is not found.
   *
   * @param key the key whose value should be retrieved
   * @return the value associated with the specified key
   * @throws PropertyNotFoundException if the key is not found in the configuration
   */
  public static String getProperty(String key) throws PropertyNotFoundException {
    if (!PROPERTY_TOKENS.containsKey(key)) {
      throw new PropertyNotFoundException("Property with key '" + key + "' not found.");
    }
    return PROPERTY_TOKENS.get(key);
  }

  /**
   * Reads properties from a configuration file and stores them.
   *
   * <p>This method uses Guava's {@link Resources} to read the file. It logs an error if the file
   * cannot be found or if an error occurs during reading or parsing, and logs a warning if the key
   * is associated with a null value or an empty string.
   */
  private static ImmutableMap<String, String> readProperties() {
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
    try {
      URL resourceUrl = Resources.getResource("server_configuration.properties");
      String text = Resources.toString(resourceUrl, StandardCharsets.UTF_8);
      Properties properties = new Properties();
      properties.load(new StringReader(text));
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
}
