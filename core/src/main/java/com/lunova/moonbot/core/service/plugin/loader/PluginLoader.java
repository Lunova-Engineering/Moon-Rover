package com.lunova.moonbot.core.service.plugin.loader;

import com.lunova.moonbot.core.api.plugin.Plugin;
import com.lunova.moonbot.core.exceptions.PluginException;
import org.eclipse.aether.artifact.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InvalidClassException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginLoader {

    private static final Logger logger = LoggerFactory.getLogger(PluginLoader.class);

    public static Plugin loadArtifact(Artifact artifact) throws PluginException {
        try (JarFile jar = new JarFile(artifact.getFile())) {
            return isModular(jar)
                    ? loadModularArtifact(artifact)
                    : loadNonModularArtifact(artifact);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean loadArtifacts(List<Artifact> artifact) {
        try {
            Set<Plugin> plugins = new HashSet<>(artifact.size());
            for (Artifact a : artifact) {
                plugins.add(loadArtifact(a));
            }
        } catch (Exception e) {
            logger.error("Error loading plugin artifacts", e);
            return false;
        }
        return true;
    }

    private static boolean isModular(JarFile jar) {
        return !Objects.isNull(jar.getEntry("module-info.class"));
    }

    private static Plugin loadModularArtifact(Artifact artifact) throws PluginException {
        return null;
    }

    private static Plugin loadNonModularArtifact(Artifact artifact) throws PluginException {
        return null;
    }

    // TODO: Read file and clean up code, seperate concerns, extract code for SRP, modularize.
    /**
     * Loads a plugin from a JAR file.
     *
     * @param jarUrl URL of the JAR file.
     * @return An instance of the Plugin object, or null if it cannot be loaded or instantiated.
     */
    public static Plugin loadArtifact(URL jarUrl) {
        try (JarFile jarFile = new JarFile(jarUrl.getFile())) {

            URLClassLoader classLoader =
                    new URLClassLoader(new URL[] {jarUrl}, PluginLoader.class.getClassLoader());

            Manifest manifest = jarFile.getManifest();

            // Perform manifest verification here if needed

            String pluginClassName = manifest.getMainAttributes().getValue("Main-Class");

            // Load the plugin class by its name
            Class<?> loadedClass = classLoader.loadClass(pluginClassName);

            // Check if the loaded class is a subclass of Plugin
            if (!Plugin.class.isAssignableFrom(loadedClass)) {
                throw new InvalidClassException(loadedClass.getName(), "The loaded class is not a subclass of Plugin.");
            }

            // Cast and instantiate the loaded class as Plugin
            Plugin p =
                    (Plugin)
                            loadedClass
                                    .getDeclaredConstructor(String.class)
                                    .newInstance(manifest.getMainAttributes().getValue("Name"));
            return p;
            // return (Plugin) loadedClass.getDeclaredConstructor().newInstance();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
