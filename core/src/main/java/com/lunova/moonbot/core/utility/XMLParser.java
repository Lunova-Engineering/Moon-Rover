package com.lunova.moonbot.core.utility;

import com.lunova.moonbot.core.plugin.PluginInfo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


import java.io.IOException;
import java.io.InputStream;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class XMLParser {

    public static PluginInfo parsePluginInfo(String filePath, ClassLoader classLoader) throws JAXBException {
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
/*
public class XMLParser {



    // Load and parse XML file
    public static Document loadXMLDocument(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(new File(filePath));
    }

    // Overloaded method to get XML value directly from a file path
    public static String getXMLValue(String filePath, String tagName) throws ParserConfigurationException, IOException, SAXException, IllegalArgumentException {
        Document doc = loadXMLDocument(filePath);
        return getXMLValue(doc, tagName);
    }

    // Existing getXMLValue method that takes a Document object
    public static String getXMLValue(Document doc, String tagName) throws IllegalArgumentException {
        NodeList elements = doc.getElementsByTagName(tagName);
        if (elements.getLength() == 0) {
            throw new IllegalArgumentException("Tag not found: " + tagName);
        }
        return elements.item(0).getTextContent();
    }

    // Extract multiple values based on varargs
    public static Map<String, String> parseXML(String filePath, String... tags) throws ParserConfigurationException, IOException, SAXException {
        return parseXML(filePath, Arrays.asList(tags));
    }

    // Extract multiple values based on a collection
    public static Map<String, String> parseXML(String filePath, Collection<String> tags) throws ParserConfigurationException, IOException, SAXException {
        Document doc = loadXMLDocument(filePath);
        Map<String, String> values = getXMLValues(doc, tags);

        if (values.isEmpty()) {
            throw new RuntimeException("Failed to read any values from the XML file: " + filePath);
        }
        return values;
    }

    // Extract multiple values from XML document
    private static Map<String, String> getXMLValues(Document doc, Iterable<String> tags) {
        Map<String, String> valuesMap = new HashMap<>();
        for (String tag : tags) {
            try {
                String value = getXMLValue(doc, tag);
                if (value != null && !value.isEmpty()) {
                    valuesMap.put(tag, value);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Error retrieving XML value for tag: " + tag + ", Error: " + e.getMessage());
            }
        }
        return valuesMap;
    }
}
*/
