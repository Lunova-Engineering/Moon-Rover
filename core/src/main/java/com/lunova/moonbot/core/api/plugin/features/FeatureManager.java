package com.lunova.moonbot.core.api.plugin.features;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class FeatureManager {

    private final Set<FeatureContainer> containers = new HashSet<>();
    private final Set<Class<? extends FeatureComponent>> features = new HashSet<>();
    private boolean isSealed = false;

    public void registerSingleFeature(Feature feature) {
        FeatureContainer container = FeatureComponentFactory.createContainerWith(feature.getName(), feature);
        registerFeatureContainer(container);
    }

    public void registerFeatureContainer(FeatureContainer container) {
        if (!isSealed) {
            ImmutableSet<Feature> unique = container.getFeatures().stream().filter(feature ->
                    features.contains(feature.getClass().asSubclass(Feature.class)))
                    .collect(ImmutableSet.toImmutableSet());
            if(unique.isEmpty())
                return;
            unique.forEach(feature -> {
                features.add(feature.getClass().asSubclass(Feature.class));
            });
            containers.add(container);
        } else {
            throw new IllegalStateException("Cannot register containers after the FeatureManager has been sealed.");
        }
    }

    public void registerAllFeatureContainers(FeatureContainer ... containers) {
        Arrays.stream(containers).forEach(this::registerFeatureContainer);
    }

    public void registerAllFeatureContainers(Iterable<FeatureContainer> containers) {
        containers.forEach(this::registerFeatureContainer);
    }



    public void seal() {
        isSealed = true;
    }

    public ImmutableSet<FeatureContainer> getFeatureContainers() {
        return ImmutableSet.copyOf(containers);
    }

    public ImmutableSet<Feature> getFeatures() {
        return containers.stream()
                .flatMap(container -> container.getFeatures().stream())
                .collect(ImmutableSet.toImmutableSet());
    }

    public ImmutableSet<Class<? extends FeatureComponent>> getRegisteredFeatures() {
        return ImmutableSet.copyOf(features);
    }
}
