package com.lunova.moonbot.core.services.bot;

import com.lunova.moonbot.core.BotConfiguration;
import com.lunova.moonbot.core.exceptions.ConfigurationPropertyException;
import com.lunova.moonbot.core.exceptions.ServiceLoadingFailedException;
import com.lunova.moonbot.core.services.BotService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class MoonBotService implements BotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoonBotService.class);

    private static final String SERVICE_NAME = "Moon Bot Service";

    private static MoonBotService instance;

    private JDA jda;

    private MoonBotService() {}

    public static MoonBotService getInstance() {
        if (instance == null)
            instance = new MoonBotService();
        return instance;
    }

    public JDA getJda() {
        return jda;
    }

    @Override
    public void initialize() throws ServiceLoadingFailedException {
        try {
            jda = JDABuilder.createDefault(BotConfiguration.getProperty("AUTH_TOKEN")).build().awaitReady();
        } catch (ConfigurationPropertyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceLoadingFailedException("Failed to load Moon Bot Service. Shutting down.", true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

}
