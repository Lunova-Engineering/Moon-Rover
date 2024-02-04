package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.moonbot.core.api.plugin.features.FeatureComponentFactory;
import com.lunova.moonbot.core.api.plugin.features.FeatureContainer;

public class BasePlugin extends Plugin {


    @Override
    protected void registerFeatures() {
        FeatureContainer commands = FeatureComponentFactory.createContainerWith("commands", new Item(), new Item(), new Item());
        FeatureContainer commands1 = FeatureComponentFactory.createContainerWith("commands", new Item());
        FeatureContainer commands2 = FeatureComponentFactory.createContainerWith("commands", new Item());
        FeatureContainer commands3 = FeatureComponentFactory.createContainerWith("commands", new Item());
        FeatureContainer commands4 = FeatureComponentFactory.createContainerWith("commands", new Item());
        getFeatureManager().registerAllFeatureContainers(commands, commands1, commands4, commands2, commands3);
    }

}
