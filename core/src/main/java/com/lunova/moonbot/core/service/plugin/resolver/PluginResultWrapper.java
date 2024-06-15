package com.lunova.moonbot.core.service.plugin.resolver;

import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PluginResultWrapper {

    private static final Logger logger = LoggerFactory.getLogger(PluginResultWrapper.class);

    private final ArtifactResult artifactResult;

    private final DependencyResult dependencyResult;

    private final List<ArtifactResult> results = new ArrayList<>();

    public PluginResultWrapper(ArtifactResult artifactResult, DependencyResult dependencyResult) {
        this.artifactResult = artifactResult;
        this.dependencyResult = dependencyResult;
        results.add(artifactResult);
        results.addAll(dependencyResult.getArtifactResults());
    }

    public ArtifactResult getArtifactResult() {
        return artifactResult;
    }

    public DependencyResult getDependencyResult() {
        return dependencyResult;
    }

    public List<ArtifactResult> getAllResults() {
        return results;
    }
}
