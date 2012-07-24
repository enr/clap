package com.github.enr.clap.api;

import java.util.Map;

/*
 * main configuration container
 */
public interface Configuration {

    /**
     * Get a configuration value
     *
     * @param key
     * @return the value of the specified key, implicitly casted as required or null if something goes wrong
     */
    <T> T get(String key);

    <T> T get(String key, T devaultValue);
    
    Map<String, Boolean> getPaths();

    void addPath(String configurationPath);
    
	Map<String, Object> getAllProperties();
	
	Map<String, Object> getBulk(String prefix);
}
