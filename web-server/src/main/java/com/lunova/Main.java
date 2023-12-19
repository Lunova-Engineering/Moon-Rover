package com.lunova;

import com.google.gson.GsonBuilder;
import com.lunova.moonbot.core.plugin.Plugin;
import com.lunova.moonbot.core.services.plugin.PluginAction;
import com.lunova.moonbot.core.services.plugin.PluginRequest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class Main {

    private static final Collection<WebPlugin> plugins = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/favicon.ico", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

            }
        });
        server.createContext("/install", new InstallHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        plugins.add(new WebPlugin("Base Plugin", "1.0", "This is plugin A", "C:/Users/drake/Documents/Moon Bot Plugins/moon-bot-plugin-base-0.2.0-SNAPSHOT.jar"));
        plugins.add(new WebPlugin("PluginB", "1.2", "This is PluginB.", "path"));
        plugins.add(new WebPlugin("PluginC", "2.0", "This is PluginC.", "path"));

        System.out.println("Server started on port " + port);
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder response = new StringBuilder("<html><head><style>")
                    .append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; }")
                    .append(".grid-container { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 10px; padding: 10px; }")
                    .append(".plugin-box { background-color: #fff; border: 1px solid #ddd; padding: 15px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.2); }")
                    .append(".install-btn { background-color: #007bff; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-size: 16px; }")
                    .append(".install-btn:hover { background-color: #0056b3; }")
                    .append("</style></head><body>")
                    .append("<div class='grid-container'>");

            for (WebPlugin plugin : plugins) {
                response.append("<div class='plugin-box'>")
                        .append("<h3>").append(plugin.name()).append(" - Version: ").append(plugin.version()).append("</h3>")
                        .append("<p>").append(plugin.description()).append("</p>")
                        .append("<form action='/install' method='post'>")
                        .append("<input type='hidden' name='pluginName' value='").append(plugin.path()).append("' />")
                        .append("<input type='submit' class='install-btn' value='Install' />")
                        .append("</form>")
                        .append("</div>");

                System.out.println(URLEncoder.encode(plugin.path(), StandardCharsets.UTF_8));
            }
            response.append("</div></body></html>");
            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }

    static class InstallHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine(); // Read the body of the request
                System.out.println("Received install request: " + query); // Log the received query

                String[] keyValue = query.split("=");

                if (keyValue.length == 2 && "pluginName".equals(keyValue[0])) {
                    String pluginPath = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    System.out.println("Decoded plugin path: " + pluginPath);
                    try {
                        installPlugin(pluginPath);
                    } catch (Exception e) {
                        e.printStackTrace(); // Log the exception for debugging
                        throw new RuntimeException(e);
                    }

                    String response = "Plugin installation initiated for " + pluginPath;
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        }
    }



    public static void installPlugin(String path) throws Exception {
        URL url = new URL("http://localhost:8080/plugin-action"); // Replace with your URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // Set up the request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        // Create the request body
        PluginRequest rq = new PluginRequest("993720567729492080", PluginAction.INSTALL, path);
        String jsonInputString = new GsonBuilder().setPrettyPrinting().create().toJson(rq);
        System.out.println(jsonInputString);

        // Send the request
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read the response
        try(java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

}
