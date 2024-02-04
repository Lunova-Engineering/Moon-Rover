package com.lunova.plugin;

import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.plugin.commands.CommandFeature;

/**
 * Main class for initializing plugin hosting all information required for installation.
 */
public class DefaultPlugin extends Plugin {

  @Override
  protected void registerFeatures() {
    getFeatureManager().registerSingleFeature(new CommandFeature());
  }

}
