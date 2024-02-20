package com.lunova.moonbot.core.services.plugin.loader;

import com.lunova.moonbot.core.api.plugin.Plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginLoader {

    //TODO: Read file and clean up code, seperate concerns, extract code for SRP, modularize.
    /**
     * Loads a plugin from a JAR file.
     *
     * @param jarUrl URL of the JAR file.
     * @return An instance of the Plugin object, or null if it cannot be loaded or instantiated.
     */
    public static Plugin loadPlugin(URL jarUrl) {
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, PluginLoader.class.getClassLoader());
            JarFile jarFile = new JarFile(jarUrl.getFile());
            Manifest manifest = jarFile.getManifest();

            // Perform manifest verification here if needed

            String pluginClassName = manifest.getMainAttributes().getValue("Main-Class");

            // Load the plugin class by its name
            Class<?> loadedClass = classLoader.loadClass(pluginClassName);

            // Check if the loaded class is a subclass of Plugin
            if (!Plugin.class.isAssignableFrom(loadedClass)) {
                System.out.println("The loaded class is not a subclass of Plugin.");
                return null;
            }

            // Cast and instantiate the loaded class as Plugin
            return (Plugin) loadedClass.getDeclaredConstructor().newInstance();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
