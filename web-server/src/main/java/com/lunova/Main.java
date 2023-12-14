package com.lunova;

import com.google.gson.GsonBuilder;
import com.lunova.moonbot.core.services.plugin.PluginAction;
import com.lunova.moonbot.core.services.plugin.PluginRequest;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/plugin-action"); // Replace with your URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // Set up the request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        // Create the request body
        PluginRequest rq = new PluginRequest("993720567729492080", PluginAction.INSTALL, "C:/Users/drake/Documents/Moon Bot Plugins/moon-bot-plugin-base-0.2.0-SNAPSHOT.jar");
        String jsonInputString = new GsonBuilder().setPrettyPrinting().create().toJson(rq);
        System.out.println(jsonInputString);

        // Send the request
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the response
        try(java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

}
