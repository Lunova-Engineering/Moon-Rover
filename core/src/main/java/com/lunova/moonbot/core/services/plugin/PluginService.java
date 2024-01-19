package com.lunova.moonbot.core.services.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lunova.moonbot.core.exceptions.PluginException;
import com.lunova.moonbot.core.exceptions.PluginRequestException;
import com.lunova.moonbot.core.exceptions.PluginServiceException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.plugin.Plugin;
import com.lunova.moonbot.core.plugin.PluginInfo;
import com.lunova.moonbot.core.services.BotService;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import com.lunova.moonbot.core.utility.Utilities;
import com.lunova.moonbot.core.utility.XmlParser;
import jakarta.xml.bind.JAXBException;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

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

    protected PluginService(String serviceName) {
        super(serviceName);
    }

    public static PluginService getInstance() {
        return INSTANCE;
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
                    Plugin plugin = executePluginRequest(pluginRequest);
                    processPluginRequest(pluginRequest, plugin);
                    PluginManager.registerPlugin(plugin, pluginRequest.guildId());
                    return "Plugin installed Successfully!";
                } catch (JsonSyntaxException | PluginRequestException | PluginException e) {
                    response.status(400);
                    return e.getMessage();
                } catch (PluginServiceException e) {
                    response.status(500);
                    LOGGER.error(e.getMessage(), e);
                    return "Internal server error: " + e.getMessage();
                }
            });
        } catch (Exception e) {
            throw new ServiceLoadingException("Failed to start web server on port " + port + " while initializing plugin service.");
        }
    }

    private Plugin executePluginRequest(PluginRequest request) throws PluginRequestException, PluginServiceException, PluginException {
        if (request.guildId() == null || request.guildId().trim().isEmpty()) {
            throw new PluginRequestException("Guild ID is missing or empty");
        }
        if (request.pluginAction() == null) {
            throw new PluginRequestException("Plugin action is missing");
        }
        if (request.pluginUrl() == null || request.pluginUrl().trim().isEmpty()) {
            throw new PluginRequestException("Plugin URL is missing or empty");
        }
        if (MoonBotService.getInstance().getBotSession().getGuildById(request.guildId()) == null) {
            throw new PluginServiceException("Guild not found in registry");
        }
        return loadPlugin(request);
    }

    private Plugin loadPlugin(PluginRequest request) throws PluginServiceException, PluginException {
        try {

            URLClassLoader classLoader = Utilities.createJarClassLoader(request.pluginUrl());
            PluginInfo pluginInfo = XmlParser.parsePluginInfo("META-INF/plugin-info.xml", classLoader);
            Class<?> pluginClass = Class.forName(pluginInfo.getMainClass(), true, classLoader);

            if (Plugin.class.isAssignableFrom(pluginClass)) {
                return (Plugin) pluginClass.getDeclaredConstructor().newInstance();
            } else {
                throw new IllegalArgumentException("Loaded class is not a subclass of PluginClass");
            }
        } catch (IOException | JAXBException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException | InstantiationException e) {
            throw new PluginServiceException("Error loading plugin: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new PluginException(e.getMessage(), e);
        }
    }

    //check throwing of plugin exception. Install and Uninstall routines already throw exception, no need to catch and rethrow here. Should throw service exception up to web server.
    private void processPluginRequest(PluginRequest request, Plugin plugin) throws PluginException {
        try {
            JDA session = MoonBotService.getInstance().getBotSession();
            switch (request.pluginAction()) {
                case INSTALL:
                    plugin.executeInstallRoutine(session, request.guildId());
                    break;
                case UNINSTALL:
                    plugin.executeUninstallRoutine(session, request.guildId());
                    break;
                case ENABLE:
                case DISABLE:
                default:
                    throw new PluginServiceException("Unexpected internal server error.");
            }
        } catch (Exception e) {
            throw new PluginException("Error running " + request.pluginAction().toString() + " routine: " + e.getMessage(), e);
        }
    }


}
