package com.github.enr.clap.impl;

import java.util.Properties;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Constants;
import com.github.enr.clap.util.PropertiesLoader;
import com.github.enr.clap.vendor.guava.base.Throwables;

public class PropertiesBackedAppMeta implements AppMeta {
    
    private Properties properties = new Properties();
    
    private static final String PROPERTY_NAME_KEY = "clap.meta.name";
    private static final String PROPERTY_VERSION_KEY = "clap.meta.version";
    private static final String PROPERTY_DISPLAYNAME_KEY = "clap.meta.displayname";
    
    /**
     * Factory method: it creates a AppMeta loading properties from a classpath resource.
     * 
     */
    public static AppMeta from(String resourceName) {
        Properties properties = null;
        try {
        	properties = getPropertiesLoader().fromResource(resourceName);
        } catch (Throwable e) {
        	Throwables.propagate(e);
        }
        return new PropertiesBackedAppMeta(properties);
    }
    
    private PropertiesBackedAppMeta(Properties properties) {
        super();
        this.properties = properties;
    }
    
    private static PropertiesLoader getPropertiesLoader() {
    	return new PropertiesLoader(); 
    }

    @Override
    public String name() {
        return properties.getProperty(PROPERTY_NAME_KEY, Constants.DEFAULT_META_NAME);
    }

    @Override
    public String version() {
        return properties.getProperty(PROPERTY_VERSION_KEY, Constants.DEFAULT_META_VERSION);
    }

    @Override
    public String displayName() {
        return properties.getProperty(PROPERTY_DISPLAYNAME_KEY, Constants.DEFAULT_META_DISPLAYNAME);
    }
}
