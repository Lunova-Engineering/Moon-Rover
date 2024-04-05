package com.lunova.moonbot.core.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class providing methods for class loading and file path handling. Includes functionality
 * to create class loaders for JAR files and to add URLs to the classpath dynamically. This class
 * serves as a helper for operations commonly needed across the application, particularly in plugin
 * loading and management.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class Utilities {

  private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

  public static String concat(String ... value) {
    StringBuilder builder = new StringBuilder();
    for(String s : value) {
      builder.append(s);
    }
    return builder.toString();
  }
}
