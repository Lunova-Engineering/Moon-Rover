package com.lunova.moonbot.core.service.plugin.resolver;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.version.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginResolver {

    private static final Logger logger = LoggerFactory.getLogger(PluginResolver.class);

    // TODO: Seperate code into small functions and seperate classes to modularize and clean up for
    // SRP and separation of concerns.
    // TODO: review return types for easy flow in application as well as ensure checking of cache to
    // return jars as needed

    private final RepositorySystem system;
    private final RepositorySystemSession.CloseableSession session;
    private static final RemoteRepository central =
            new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/")
                    .build();
    private static final Map<Path, Artifact> ARTIFACT_MAP = new HashMap<>();

    public PluginResolver(RepositorySystem system, RepositorySystemSession.SessionBuilder session) {
        this.system = system;
        this.session = session.build();
    }

    public static void loadCachedPlugins() {

    }

    public void downloadArtifact(String groupId, String artifactId, String version)
            throws IOException,
                    ArtifactResolutionException,
                    DependencyResolutionException,
                    VersionRangeResolutionException {
        version = verifyLatestVersion(groupId, artifactId, version)
                        ? version
                        : getLatestVersion(groupId, artifactId);
        Artifact artifact = new DefaultArtifact(groupId, artifactId, "jar", version);
        PluginResultWrapper results = createResultWrapper(artifact);
        cacheArtifactResults(results);
    }

    private PluginResultWrapper createResultWrapper(Artifact artifact)
            throws DependencyResolutionException, ArtifactResolutionException {

        // Artifact (Plugin) Request
        ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(artifact);
        request.addRepository(central);
        ArtifactResult result = system.resolveArtifact(session, request);

        // dependency results
        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot(new Dependency(request.getArtifact(), JavaScopes.COMPILE));
        collectRequest.addRepository(central);

        DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, null);
        DependencyResult dependencyResult = system.resolveDependencies(session, dependencyRequest);

        return new PluginResultWrapper(result, dependencyResult);
    }

    private void cacheArtifactResults(PluginResultWrapper results) {
        results.getAllResults()
                .reversed()
                .forEach(
                        result -> {
                            Artifact artifact = result.getArtifact();
                            Path path = artifact.getFile().toPath();
                            try {
                                Files.createDirectories(path.getParent());
                                Files.copy(
                                        path.toUri().toURL().openStream(),
                                        path,
                                        StandardCopyOption.REPLACE_EXISTING);
                                ARTIFACT_MAP.put(path, artifact);
                            } catch (IOException e) {
                                logger.warn(
                                        "Error while downloading artifact with ID \"{}\"",
                                        artifact.getArtifactId(),
                                        e);
                                if (logger.isDebugEnabled())
                                    logger.debug(
                                            "Artifact Information: Group-ID: {}, Artifact-ID: {}, Version: {}",
                                            artifact.getGroupId(),
                                            artifact.getArtifactId(),
                                            artifact.getVersion(),
                                            e);
                                throw new RuntimeException(e);
                            }
                        });
    }

    private boolean verifyLatestVersion(String groupId, String artifactId, String version)
            throws ArtifactResolutionException, VersionRangeResolutionException {
        return version.equals(getLatestVersion(groupId, artifactId));
    }

    private String getLatestVersion(String groupId, String artifactId)
            throws ArtifactResolutionException, VersionRangeResolutionException {
        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact(new DefaultArtifact(groupId, artifactId, "jar", "(0,)"));
        rangeRequest.addRepository(central);

        VersionRangeResult rangeResult = system.resolveVersionRange(session, rangeRequest);
        List<Version> versions = rangeResult.getVersions();
        return versions.getLast().toString(); // returns the latest version
    }
}
