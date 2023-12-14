package com.lunova.moonbot.core.services.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.plugin.Plugin;
import com.lunova.moonbot.core.services.BotService;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import net.dv8tion.jda.api.JDA;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.jar.JarFile;

import static spark.Spark.post;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class PluginLoader extends BotService {

    private static final PluginLoader INSTANCE = new PluginLoader("Plugin Loader Service");

    public static PluginLoader getInstance() {
        return INSTANCE;
    }

    protected PluginLoader(String serviceName) {
        super(serviceName);
    }

    @Override
    protected void initialize() throws ServiceLoadingException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        post("/plugin-action", (request, response) -> {
            String requestBody = request.body(); // Get the request body as a string

            PluginRequest pluginRequest = gson.fromJson(requestBody, PluginRequest.class);


            return "Request processed";
        });
        //start web service / thread here for accepting incoming plugin requests
    }

    private void processPluginRequest(PluginRequest request) {

        switch (request.getPluginAction()) {
            case INSTALL:
                try {
                    loadAndExecutePlugin(request.getPluginUrl(), request.getGuildId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public void loadAndExecutePlugin(String jarPath, String guildId) throws Exception {
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

            // Step 3: Load the plugin class
            Class<?> pluginClass = classLoader.loadClass(pluginClassName);

            // Step 4: Verify that it's a subclass of PluginClass
            if (Plugin.class.isAssignableFrom(pluginClass)) {
                // Step 5: Instantiate and execute plugin routine
                Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                JDA session = MoonBotService.getInstance().getBotSession();
                pluginInstance.beforeInstall(session); // Assuming this is your plugin routine method
                pluginInstance.install(session, guildId);
                pluginInstance.afterInstall(session);
            } else {
                throw new IllegalArgumentException("Loaded class is not a subclass of PluginClass");
            }
        }
    }
}
