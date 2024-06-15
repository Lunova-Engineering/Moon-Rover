package com.lunova.moonbot.core.api.plugin.features;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;

import java.util.Iterator;

// @JsonSerialize(using = FeatureContainerSerializer.class)
public final class FeatureContainer implements FeatureComponent {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("features")
    private final ImmutableSet<Feature> features;

    @JsonProperty("settingGroup")
    private final SettingGroup settings;

    protected static class Builder {

        private final String name;
        private final ImmutableSet.Builder<Feature> builder = new ImmutableSet.Builder<>();
        private SettingGroup settings;

        public Builder(String name) {
            this.name = name;
        }

        public Builder addFeature(Feature feature) {
            builder.add(feature);
            return this;
        }

        public Builder addFeature(Feature... features) {
            builder.add(features);
            return this;
        }

        public Builder addAll(Iterable<Feature> features) {
            builder.addAll(features);
            return this;
        }

        public Builder addAll(Iterator<Feature> features) {
            builder.addAll(features);
            return this;
        }

        public Builder addSettingGroup(SettingGroup settings) {
            this.settings = settings;
            return this;
        }

        public ImmutableSet<Feature> getImmutableFeatures() {
            return builder.build();
        }

        public FeatureContainer build() {
            return new FeatureContainer(this);
        }
    }

    private FeatureContainer(Builder builder) {
        this.name = builder.name;
        this.features = builder.getImmutableFeatures();
        this.settings = builder.settings;
    }

    public String getName() {
        return name;
    }

    public ImmutableSet<Feature> getFeatures() {
        return features;
    }

    @Override
    public Optional<SettingGroup> getSettingGroup() {
        return Optional.fromNullable(settings);
    }
}
