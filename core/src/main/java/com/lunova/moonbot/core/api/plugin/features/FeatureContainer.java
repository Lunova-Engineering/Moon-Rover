package com.lunova.moonbot.core.api.plugin.features;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;

import java.util.Iterator;

public final class FeatureContainer implements FeatureComponent {

    private final ImmutableSet<Feature> features;
    private final SettingGroup settings;
    private final String name;

    protected static class Builder {
        private final ImmutableSet.Builder<Feature> builder = new ImmutableSet.Builder<>();
        private SettingGroup settings;
        private final String name;

        public Builder(String name) {
            this.name = name;
        }

        public Builder addFeature(Feature feature) {
            builder.add(feature);
            return this;
        }

        public Builder addFeature(Feature ... features) {
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

    public ImmutableSet<Feature> getFeatures() {
        return features;
    }

    public String getName() {
        return name;
    }

    @Override
    public Optional<SettingGroup> getSettingGroup() {
        return Optional.fromNullable(settings);
    }

}
