package com.lunova.moonbot.core.api.plugin.features;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.lunova.moonbot.core.api.plugin.configuration.*;

import java.util.Iterator;

public abstract class FeatureGroup extends FeatureComponent {
    public static class Builder<T extends FeatureGroup> {

        private final ImmutableSet.Builder<Feature> featureBuilder = new ImmutableSet.Builder<>();
        private final ImmutableSet.Builder<Setting> optionBuilder = new ImmutableSet.Builder<>();
        private ImmutableSet<Feature> features;
        private ImmutableSet<Setting> settings;


        public Builder<T> withFeature(Feature feature) {
            featureBuilder.add(feature);
            return this;
        }

        public Builder<T> withFeature(Feature... features) {
            featureBuilder.add(features);
            return this;
        }

        public Builder<T> withAllFeatures(Iterable<Feature> features) {
            featureBuilder.addAll(features);
            return this;
        }

        public Builder<T> withAllFeatures(Iterator<Feature> features) throws NullPointerException {
            featureBuilder.addAll(features);
            return this;
        }

        public Builder<T> withConfigureOption(Setting setting) {
            optionBuilder.add(setting);
            return this;
        }

        public Builder<T> withConfigureOption(Setting... setting) {
            optionBuilder.add(setting);
            return this;
        }

        public Builder<T> withAllConfigureOption(Iterable<Setting> option) {
            optionBuilder.addAll(option);
            return this;
        }

        public Builder<T> withAllConfigureOption(Iterator<Setting> option) {
            optionBuilder.addAll(option);
            return this;
        }

        public T build(Function<FeatureGroup.Builder<T>, T> constructor) {
            return constructor.apply(this);
        }
    }
    private final ImmutableSet<Feature> features;
    private final ImmutableSet<Setting> settings;


    protected  <T extends FeatureGroup> FeatureGroup(Builder<T> builder) {
        this.features = builder.features;
        this.settings = builder.settings;
    }

    public ImmutableSet<Feature> getFeatures() {
        return features;
    }

    public ImmutableSet<Setting> getOptions() {
        return settings;
    }

}
