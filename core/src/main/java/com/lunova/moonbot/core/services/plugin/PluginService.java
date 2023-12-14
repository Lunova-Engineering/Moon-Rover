package com.lunova.moonbot.core.services.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lunova.moonbot.core.exceptions.InvalidPluginRequestException;
import com.lunova.moonbot.core.exceptions.PluginRoutineException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.plugin.Plugin;
import com.lunova.moonbot.core.services.BotService;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.jar.JarFile;

import static spark.Spark.port;
import static spark.Spark.post;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class PluginService extends BotService {

    private static final PluginService INSTANCE = new PluginService("Plugin Loader Service");

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(PluginRequest.class, new PluginRequestAdapter()).setPrettyPrinting().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginService.class);

    public static PluginService getInstance() {
        return INSTANCE;
    }

    protected PluginService(String serviceName) {
        super(serviceName);
    }

    @Override
    protected void initialize() throws ServiceLoadingException {
        int port = 8080;
        try {
            port(port);
            post("/plugin-action", (request, response) -> {
                try {
                    String requestBody = request.body();
                    PluginRequest pluginRequest = GSON.fromJson(requestBody, PluginRequest.class);
                    if(validatePluginRequest(pluginRequest))
                        processPluginRequest(pluginRequest);
                    return "Plugin installed Successfully!";
                } catch (JsonSyntaxException | InvalidPluginRequestException | PluginRoutineException e) {
                    response.status(400);
                    return e.getMessage();
                } catch (Exception e) {
                    response.status(500);
                    return "Internal server error: " + e.getMessage();
                }
            });
        } catch (Exception e) {
            throw new ServiceLoadingException("Failed to start web server on port " + port + " while initializing plugin service.");
        }
    }

    private boolean validatePluginRequest(PluginRequest request) throws InvalidPluginRequestException {
        //TODO write a function to validate the PluginRequest that Gson converted from json ensuring all data is present and is not malformed or missing information
        if (request.guildId() == null || request.guildId().trim().isEmpty()) {
            throw new InvalidPluginRequestException("Guild ID is missing or empty");
        }
        if (request.pluginAction() == null) {
            throw new InvalidPluginRequestException("Plugin action is missing");
        }
        if (request.pluginUrl() == null || request.pluginUrl().trim().isEmpty()) {
            throw new InvalidPluginRequestException("Plugin URL is missing or empty");
        }
        return true;
    }



    private void processPluginRequest(PluginRequest request) throws PluginRoutineException {
        try {

            Plugin plugin = loadPlugin(request.pluginUrl());
            JDA session = MoonBotService.getInstance().getBotSession();

            switch(request.pluginAction()) {
                case INSTALL:
                    plugin.executeInstallRoutine(session, request.guildId());
                case UNINSTALL:
                    plugin.executeUninstallRoutine(session, request.guildId());
                case ENABLE:
                case DISABLE:
                default:
                    break;

            }

        } catch (Exception e) {
            throw new PluginRoutineException(e.getMessage());
            //TODO: Write a custom exception for Plugin Routine Exceptions when the routine is run but an error occurs preventing the full routine from executing
        }
    }

    private Plugin loadPlugin(String jarPath) throws IOException, ParserConfigurationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SAXException {
        // Step 1: Dynamically add JAR to the classpath
        URL jarURL = Paths.get(jarPath).toUri().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{jarURL}, getClass().getClassLoader());

        // Step 2: Read meta-information
        try (JarFile jarFile = new JarFile(jarPath)) {
            // Assuming meta-info is in 'META-INF/plugin-info.xml'
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(jarFile.getInputStream(jarFile.getEntry("META-INF/plugin-info.xml")));

            // Process the XML to find the class name (implement this part based on your XML structure)
            String pluginClassName = doc.getElementsByTagName("mainClass").item(0).getTextContent(); // Extract class name from XML
            System.out.println(pluginClassName);
            // Step 3: Load the plugin class
            Class<?> pluginClass = classLoader.loadClass(pluginClassName);

            // Step 4: Verify that it's a subclass of PluginClass
            if (Plugin.class.isAssignableFrom(pluginClass)) {
                // Step 5: Instantiate and execute plugin routine
                Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                return pluginInstance;
/*                JDA session = MoonBotService.getInstance().getBotSession();
                pluginInstance.beforeInstall(session); // Assuming this is your plugin routine method
                pluginInstance.install(session, guildId);
                pluginInstance.afterInstall(session);
                System.out.println("Installed");
                PluginManager.registerPlugin(pluginInstance);*/
            } else {
                throw new IllegalArgumentException("Loaded class is not a subclass of PluginClass");
            }
        }
    }
}
