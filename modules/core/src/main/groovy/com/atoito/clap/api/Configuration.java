package com.atoito.clap.api;

import java.util.Map;

/**
 * Main configuration container.
 * The actual configuration parsing is done by an injected ConfigurationReader implementation.
 */
public interface Configuration {

    /**
     * Get a configuration value
     * 
     * @param key
     * @return the value of the specified key, implicitly casted as required or
     *         null if something goes wrong
     */
    <T> T get(String key);
    /**
     * Get a configuration value
     * 
     * @param key
     * @param devaultValue
     * @return the value of the specified key, or devaultValue or if configuration has not the key.
     */
    <T> T get(String key, T devaultValue);

    /**
     * @return a map with, as keys the paths to the configuration files, and values if the file is found.
     */
    Map<String, Boolean> getPaths();

    void addPath(String configurationPath);

    /**
     * @return a map of all properties in the configuration holder.
     */
    Map<String, Object> getAllProperties();

    Map<String, Object> getBulk(String prefix);
    
    /**
     * 
     * @param configurationPath
     * @return a configuration object with properties from only the given configurationPath.
     */
    Configuration getDedicatedInstance(String configurationPath);
}
