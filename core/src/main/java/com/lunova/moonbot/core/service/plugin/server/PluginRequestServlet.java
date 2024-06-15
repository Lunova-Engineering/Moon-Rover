package com.lunova.moonbot.core.service.plugin.server;

import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.service.plugin.PluginRequest;
import com.lunova.moonbot.core.service.plugin.resolver.PluginResolver;
import com.lunova.moonbot.core.service.plugin.resolver.PluginResolverUtils;
import com.lunova.moonbot.core.utility.json.JsonHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class PluginRequestServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PluginRequestServlet.class);

    private static final PluginResolver resolver = PluginResolverUtils.createDefaultResolver();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {

            logger.info("Received plugin request");
            String body = getBody(req);
            if (body.isBlank()) return;

            PluginRequest request = JsonHandler.deserialize(body, PluginRequest.class);
            resolver.downloadArtifact(
                    request.pluginGroupId(), request.pluginArtifactId(), request.pluginVersion());

        } catch (JsonDeserializationException e) {
            logger.error("Error deserializing plugin request", e);
        } catch (ArtifactResolutionException e) {
            throw new RuntimeException(e);
        } catch (DependencyResolutionException e) {
            throw new RuntimeException(e);
        } catch (VersionRangeResolutionException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBody(HttpServletRequest req) {
        try (BufferedReader reader = req.getReader()) {
            Iterator<String> itr = reader.lines().iterator();
            StringBuilder builder = new StringBuilder();
            while (itr.hasNext()) {
                builder.append(itr.next());
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
