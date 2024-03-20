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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PluginResolver {

    //TODO: Seperate code into small functions and seperate classes to modularize and clean up for SRP and seperation of concerns.
    //TODO: review return types for easy flow in application as well as ensure checking of cache to return jars as needed

    private final RepositorySystem system;
    private final RepositorySystemSession.CloseableSession session;

    public PluginResolver(RepositorySystem system, RepositorySystemSession.SessionBuilder session) {
        this.system = system;
        this.session = session.build();
    }

    public void downloadArtifact(String groupId, String artifactId, String version) throws IOException, ArtifactResolutionException, DependencyResolutionException, VersionRangeResolutionException {
        // Define Maven Central repository
        RemoteRepository central = new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/").build();

        // Check if provided version is the latest
        String latestVersion = getLatestVersion(groupId, artifactId, central);
        if (!latestVersion.equals(version)) {
            System.out.println("Updating to the latest version: " + latestVersion);
            version = latestVersion;
        }

        // Resolve artifact and its dependencies
        Artifact artifact = new DefaultArtifact(groupId, artifactId, "jar", version);
        File jarFile = downloadArtifact(central, artifact);
        System.out.println("Downloaded artifact to " + jarFile.getAbsolutePath());

        // Download dependencies
        downloadDependencies(central, artifact, jarFile);
    }

    private String getLatestVersion(String groupId, String artifactId, RemoteRepository central) throws ArtifactResolutionException, VersionRangeResolutionException {
        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact(new DefaultArtifact(groupId, artifactId, "jar", "(0,)"));
        rangeRequest.addRepository(central);

        VersionRangeResult rangeResult = system.resolveVersionRange(session, rangeRequest);
        List<Version> versions = rangeResult.getVersions();
        return versions.getLast().toString(); // returns the latest version
    }

    private File downloadArtifact(RemoteRepository central, Artifact artifact) throws IOException, ArtifactResolutionException {
        ArtifactRequest request = new ArtifactRequest();
        request.setArtifact(artifact);
        request.addRepository(central);

        ArtifactResult result = system.resolveArtifact(session, request);
        URL artifactURL = result.getArtifact().getFile().toURI().toURL();

        Path targetPath = Path.of(artifactURL.getPath());

        //Path targetPath = Path.of("core/data/plugins", result.getArtifact().getGroupId(), result.getArtifact().getArtifactId(), result.getArtifact().getVersion(), result.getArtifact().getFile().getName());
        Files.createDirectories(targetPath.getParent());

        Files.copy(artifactURL.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return targetPath.toFile();
    }

    private void downloadDependencies(RemoteRepository central, Artifact artifact, File jarFile) throws ArtifactResolutionException, IOException, DependencyResolutionException {
        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot(new Dependency(artifact, JavaScopes.COMPILE));
        collectRequest.addRepository(central);

        DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, null);
        DependencyResult dependencyResult = system.resolveDependencies(session, dependencyRequest);

        for (ArtifactResult artifactResult : dependencyResult.getArtifactResults()) {
            URL dependencyURL = artifactResult.getArtifact().getFile().toURI().toURL();
            Artifact depArtifact = artifactResult.getArtifact();
            Path targetPath = Path.of(dependencyURL.getPath());

            // Path targetPath = Path.of("core/data/plugins", depArtifact.getGroupId(), depArtifact.getArtifactId(), depArtifact.getVersion(), depArtifact.getFile().getName());
            Files.createDirectories(targetPath.getParent());

            Files.copy(dependencyURL.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Downloaded dependency to " + targetPath);
        }
    }
}
