package com.lunova.moonbot.core.service.plugin.tasks;

import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.plugin.server.PluginServer;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerTask extends RunnableServiceTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerTask.class);

    private final int port;

    public WebServerTask(TaskPriority taskPriority, Service<?> originator, int port) {
        super(taskPriority, originator);
        this.port = port;
    }

    @Override
    protected void onRun() {
        PluginServer.startServer();
    }
}
