package com.lunova.moonbot.core.api.plugin.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public final class FeatureManager {

    @JsonProperty("containers")
    private final Set<FeatureContainer> containers = new HashSet<>();
    @JsonIgnore
    private final Set<Class<? extends FeatureComponent>> features = new HashSet<>();
    @JsonIgnore
    private boolean isSealed = false;

    public void registerSingleFeature(Feature feature) {
        FeatureContainer container = FeatureComponentFactory.createContainerWith(feature.getName() + " Container", feature);
        registerFeatureContainer(container);
    }

    public void registerFeatureContainer(FeatureContainer container) {
        if (!isSealed) {
            ImmutableSet<Feature> unique = container.getFeatures().stream().filter(feature ->
                    !features.contains(feature.getClass()))
                    .collect(ImmutableSet.toImmutableSet());
            if(unique.isEmpty())
                return;
            unique.forEach(feature -> {
                features.add(feature.getClass());
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
