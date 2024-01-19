package com.lunova.moonbot.core.utility;

import com.lunova.moonbot.core.plugin.PluginInfo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;

/**
 * summary statement.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class XmlParser {

  /**
   * Parse the Plugin info.
   *
   * @param filePath file path
   * @param classLoader class loader
   * @return {@link PluginInfo} plugin info
   * @throws JAXBException exception
   */
  public static PluginInfo parsePluginInfo(String filePath, ClassLoader classLoader)
      throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(PluginInfo.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();

    try (InputStream stream = classLoader.getResourceAsStream(filePath)) {
      if (stream == null) {
        throw new JAXBException("Resource not found: " + filePath);
      }
      return (PluginInfo) unmarshaller.unmarshal(stream);
    } catch (IOException e) {
      throw new JAXBException("Failed to read plugin-info.xml", e);
    }
  }
}
