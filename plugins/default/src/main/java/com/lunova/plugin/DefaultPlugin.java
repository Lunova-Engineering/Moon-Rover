package com.lunova.plugin;

import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.plugin.commands.CommandFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Main class for initializing plugin hosting all information required for installation. */
public class DefaultPlugin extends Plugin {

  private static final Logger logger = LoggerFactory.getLogger(DefaultPlugin.class);

  public DefaultPlugin(String name) {
    super("Default Plugin");
  }

  @Override
  public void registerFeatures() {
    getFeatureManager().registerFeature(new CommandFeature("Commands"));
  }
}
