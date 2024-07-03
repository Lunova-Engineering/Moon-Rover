package com.lunova.moonbot.core.service.plugin;

import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.ServiceManager;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ThreadFactoryConfig;
import com.lunova.moonbot.core.service.files.FileFormat;
import com.lunova.moonbot.core.service.files.FileService;
import com.lunova.moonbot.core.service.plugin.server.PluginServer;
import org.eclipse.aether.artifact.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Service responsible for managing plugins' lifecycle including loading, registering, and managing
 * plugins. It handles the network endpoints for plugin actions and ensures that plugins are
 * appropriately initialized and maintained.
 */
@ServiceInfo(name = "Plugin Service", critical = true, disabled = false)
@ThreadFactoryConfig(nameFormat = "Plugin-Service")
public class PluginService extends Service<ServiceExecutor> {

    /** Logger for the PluginService class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginService.class);

    public PluginService(String name, boolean critical, ServiceExecutor executor) {
        super(name, critical, executor);
    }

    /**
     * Initializes the PluginService, setting up the network endpoints for handling plugin actions
     * and configuring the plugins' environment.
     *
     * @throws ServiceLoadingException If the initialization of the service fails.
     */
    @Override
    protected void initialize() {
        PluginServer.startServer();
    }

    /*  public Map<Path, Artifact> collectArtifacts(String directoryPath) throws IOException {
      Map<Path, Artifact> artifacts = new HashMap<>();

      Files.walk(Paths.get(directoryPath))
              .filter(Files::isRegularFile)
              .filter(path -> path.toString().endsWith(".jar"))
              .forEach(
                      path -> {
                        String[] parts = path.toString().split(File.separator);
                        String version = parts[parts.length - 2];
                        String artifactId = parts[parts.length - 3];
                        String groupId =
                                String.join(
                                        ".", Arrays.asList(parts).subList(parts.length - 4, parts.length - 3));
                        Artifact artifact = new DefaultArtifact(groupId, artifactId, "jar", version);
                        artifacts.put(path, artifact);
                      });
      writeManifest(artifacts, "core/data/plugins/manifest.json");

      return artifacts;
    }*/

    public void writeManifest(Map<Path, Artifact> artifacts, String manifestPath)
            throws IOException {
        ServiceManager.getService(FileService.class)
                .writeFile(Path.of(manifestPath), "manifest", FileFormat.JSON, artifacts);
    }

    @Override
    protected void onShutdown() {
        super.onShutdown();
    }
}
