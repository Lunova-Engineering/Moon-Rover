package com.lunova.moonbot.core.services.plugin;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.14.2023
 */
public class PluginRequestAdapter implements JsonSerializer<PluginRequest>, JsonDeserializer<PluginRequest> {

    @Override
    public JsonElement serialize(PluginRequest src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guildId", src.guildId());
        jsonObject.addProperty("pluginAction", src.pluginAction().toString());
        jsonObject.addProperty("pluginUrl", src.pluginUrl());
        return jsonObject;
    }

    @Override
    public PluginRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String guildId = jsonObject.get("guildId").getAsString();
        PluginAction pluginAction = PluginAction.valueOf(jsonObject.get("pluginAction").getAsString());
        String pluginUrl = jsonObject.get("pluginUrl").getAsString();
        return new PluginRequest(guildId, pluginAction, pluginUrl);
    }
}
