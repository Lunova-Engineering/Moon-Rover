package com.lunova.moonbot.core.services.bot;

import com.lunova.moonbot.core.BotConfiguration;
import com.lunova.moonbot.core.event.EventDispatcher;
import com.lunova.moonbot.core.exceptions.ConfigurationException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.services.BotService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class MoonBotService extends BotService {

    private static MoonBotService instance;

    private JDA botSession;

    protected MoonBotService(String serviceName, boolean critical) {
        super(serviceName, critical);
    }

    public static MoonBotService getInstance() {
        if (instance == null) instance = new MoonBotService("Moon Bot Service", true);
        return instance;
    }

    public JDA getBotSession() {
        return botSession;
    }

    @Override
    public void initialize() throws ServiceLoadingException {
        try {
            botSession = JDABuilder.createDefault(BotConfiguration.getProperty("AUTH_TOKEN")).enableIntents(EnumSet.allOf(GatewayIntent.class)).addEventListeners(new EventDispatcher()).build().awaitReady();
        } catch (ConfigurationException | InterruptedException e) {
            getLogger().error(e.getMessage(), e);
            throw new ServiceLoadingException("Failed to load Moon Bot Service. Shutting down.", isCritical());
        }
    }

}
