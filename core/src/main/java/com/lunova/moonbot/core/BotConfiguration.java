package com.lunova.moonbot.core;

import com.lunova.moonbot.core.exceptions.PropertyNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration manager for the bot.
 *
 * <p>This class loads configuration properties from a file and provides access to them. It uses the
 * Singleton pattern to ensure only one instance manages the configuration settings.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.22.2023
 */
public class BotConfiguration {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BotConfiguration.class);

  /** Singleton instance of {@code BotConfiguration}. */
  private static BotConfiguration CONFIGURATION_INSTANCE;

  /** Stores the bot configuration properties. */
  private final Map<String, String> botToken = new HashMap<>();

  /** Constructs a {@code BotConfiguration} instance and reads properties from file. */
  public BotConfiguration() {
    readProperties();
  }

  /**
   * Gets the singleton instance of {@code BotConfiguration}.
   *
   * <p>If the instance does not exist, it initializes a new instance.
   *
   * @return the singleton {@code BotConfiguration} instance
   */
  public static BotConfiguration getInstance() {
    if (CONFIGURATION_INSTANCE == null) {
      CONFIGURATION_INSTANCE = new BotConfiguration();
    }
    return CONFIGURATION_INSTANCE;
  }

  /**
   * Retrieves the property value associated with the specified key.
   *
   * <p>If the key is not found in the configuration, this method throws a {@code
   * PropertyNotFoundException}.
   *
   * @param key the key for the property to be returned
   * @return the value associated with the specified key
   * @throws PropertyNotFoundException if the key is not found in the bot configuration
   */
  public String getProperty(String key) throws PropertyNotFoundException {
    if (!botToken.containsKey(key)) {
      throw new PropertyNotFoundException("Property with key value of '" + key + "' not found.");
    }
    return botToken.get(key);
  }

  /**
   * Reads and loads properties from the configuration file into the botToken map.
   *
   * <p>The properties file should be located in the resources directory. This method logs an error
   * if the file cannot be found or read.
   */
  private void readProperties() {
    try (InputStream stream =
        Objects.requireNonNull(getClass().getResource("/server_configuration.properties"))
            .openStream()) {

      Properties properties = new Properties();
      properties.load(stream);

      for (String key : properties.stringPropertyNames()) {
        String value = properties.getProperty(key);
        botToken.put(key, value);
      }

    } catch (NullPointerException e) {
      LOGGER.error("Unable to locate properties file.", e);
    } catch (IOException e) {
      LOGGER.error("Error while reading properties file.", e);
    }
  }
}
