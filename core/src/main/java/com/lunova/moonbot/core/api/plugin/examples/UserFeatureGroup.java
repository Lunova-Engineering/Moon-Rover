package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.FeatureGroup;

public class UserFeatureGroup extends FeatureGroup {


    protected <T extends FeatureGroup> UserFeatureGroup(Builder<T> builder) {
        super(builder);
    }

}
