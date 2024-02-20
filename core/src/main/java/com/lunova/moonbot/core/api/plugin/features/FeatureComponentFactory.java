package com.lunova.moonbot.core.api.plugin.features;

public final class FeatureComponentFactory {

    public static FeatureContainer createContainerWith(String name, Feature ... features) {
        return new FeatureContainer.Builder(name).addFeature(features).build();
    }

}
