package com.lunova.moonbot.core.service.plugin.loader;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginManifestReader {


    //TODO: Evaluate usefulness of a Manifest reading class / system. Likely will be used strictly for seperation of concerns.
    public static String getMainPluginClass(JarFile jarFile) throws IOException {
        Manifest manifest = jarFile.getManifest();
        return manifest.getMainAttributes().get("Main-Class").toString();
    }
}
