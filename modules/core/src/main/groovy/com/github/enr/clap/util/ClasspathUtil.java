package com.github.enr.clap.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods pertaining classpath.
 */
public class ClasspathUtil {

    public static File getClasspathForClass(Class<?> targetClass) {
        URI location = null;
        try {
            location = targetClass.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        if (!location.getScheme().equals("file")) {
            throw new RuntimeException(String.format("Cannot determine classpath for %s from codebase '%s'.",
                    targetClass.getName(), location));
        }
        return new File(location.getPath());
    }

    /**
     * Returns a list of jars urls to include in the app classpath, using the
     * given file as home.
     * 
     * Usage:
     * getApplicationClasspathUrls(home, "lib", "plugins");
     * 
     * @param home
     * @return a list of jars urls to include in Please classpath.
     */
    public static List<URL> getApplicationClasspathUrls(File home, String... subdirs) {
        List<URL> result = new ArrayList<URL>();
        for (String subdir: subdirs) {
            result.addAll(jarsInSubDirectory(home, subdir));
        }
        return result;
    }

    private static List<URL> jarsInSubDirectory(File parent, String dirName) {
        checkArgumentNotNull(parent, "asking jars, parent directory cannot be null");
        checkArgumentNotNull(dirName, "asking jars, directory cannot be null");
        checkArgument(parent.exists(), "parent directory '%s' not found", parent.getAbsolutePath());
        checkArgument(parent.isDirectory(), "parent directory '%s' not a directory",
                parent.getAbsolutePath());
        String pleaseHomePath = parent.getAbsolutePath();
        List<URL> plugins = new ArrayList<URL>();
        File subDir = new File(pleaseHomePath, dirName);

        if ((subDir == null) || (!subDir.exists()) || (!subDir.isDirectory())) {
            return plugins;
        }
        for (File file : subDir.listFiles()) {
            if (!file.getName().endsWith(".jar")) {
                continue;
            }
            try {
                URL url = file.toURI().toURL();
                plugins.add(url);
            } catch (MalformedURLException e) {
                throw new RuntimeException("error loading jar " + file.getAbsolutePath());
            }
        }
        return plugins;
    }

    private static void checkArgumentNotNull(Object arg, String errorMessage) {
        if (arg == null) {
            throw new NullPointerException(errorMessage);
        }
    }

    private static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
        }
    }
}
