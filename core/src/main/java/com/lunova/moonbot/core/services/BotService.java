package com.lunova.moonbot.core.services;

import com.lunova.moonbot.core.exceptions.ServiceLoadingFailedException;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public interface BotService {


    void initialize() throws ServiceLoadingFailedException;
    String getServiceName();

}
