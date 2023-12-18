package com.lunova.moonbot.core.utility;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
public class Utilities {

    /**
     * Loads a JAR file from the given URL or file path and creates a new URLClassLoader.
     *
     * @param jarUrlOrPath The URL or file path of the JAR file to load.
     * @return URLClassLoader that can be used to load classes from the JAR.
     * @throws IOException If an I/O error occurs.
     */
    public static URLClassLoader createJarClassLoader(String jarUrlOrPath) throws IOException {
        URL url;

        if (isLocalFilePath(jarUrlOrPath)) {
            Path path = Paths.get(jarUrlOrPath);
            url = path.toUri().toURL();
        } else {
            url = new URL(jarUrlOrPath);
        }

        return URLClassLoader.newInstance(new URL[]{url}, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Adds the given URL to the classpath using URLClassLoader.
     *
     * @param url The URL to add to the classpath.
     * @param classLoader The class loader to which the URL will be added.
     * @throws IOException If an I/O error occurs.
     */
    private static void addURLToClasspath(URL url, URLClassLoader classLoader) throws IOException {
        // Reflection to invoke 'addURL' method on URLClassLoader
        try {
            java.lang.reflect.Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
        } catch (ReflectiveOperationException e) {
            throw new IOException("Failed to add URL to system classloader", e);
        }
    }

    /**
     * Checks if the provided string is a valid local file path.
     *
     * @param path The string to check.
     * @return true if the string is a valid local file path, false otherwise.
     */
    private static boolean isLocalFilePath(String path) {
        try {
            Path filePath = Paths.get(path);
            return Files.exists(filePath) && !Files.isDirectory(filePath);
        } catch (Exception e) {
            return false;
        }
    }
}
