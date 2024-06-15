package com.lunova.moonbot.core.service.plugin.server;

import com.lunova.moonbot.core.service.plugin.resolver.PluginResolver;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginServer {

    private static final Logger logger = LoggerFactory.getLogger(PluginServer.class);

    private static final Server server = new Server(8080);

    public static void startServer() {


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/plugin");


        // Map the JsonServlet to the path /processJson
        context.addServlet(new ServletHolder(new PluginRequestServlet()), "/plugin-action");
        //ServletHolder holder = new ServletHolder();
       // holder.setServlet(new PluginRequestServlet());
        //context.addServlet(holder, "");
        server.setHandler(context);

        try {
            server.start();
        } catch (Exception e) {
            logger.error("Error starting the server", e);
        }
        PluginResolver.loadCachedPlugins();
    }
}
