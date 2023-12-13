package com.lunova.moonbot.core.services.plugin;

import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.services.BotService;

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

    }

}
