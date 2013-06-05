package com.github.enr.clap.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Constants;
import com.github.enr.clap.util.ResourcesLoader;
import com.github.enr.clap.vendor.guava_14_0_1.base.Throwables;
import com.github.enr.clap.vendor.guava_14_0_1.io.Closeables;

public class PropertiesBackedAppMeta implements AppMeta {
    
    private Properties properties = new Properties();
    
    private static final String PROPERTY_NAME_KEY = "clap.meta.name";
    private static final String PROPERTY_VERSION_KEY = "clap.meta.version";
    private static final String PROPERTY_DISPLAYNAME_KEY = "clap.meta.displayname";
    
    public static PropertiesBackedAppMeta from(String filename) {
        URL url = ResourcesLoader.getResource(filename);
        //InputSupplier<InputStream> inputSupplier = Resources.newInputStreamSupplier(url);
        //Closer closer = Closer.create();
        InputStream in = null;
        Properties properties = new Properties();
        try {
            //is = url.openStream();
            in = url.openStream();
            properties.load(in);
        } catch (Throwable e) {
        	Throwables.propagate(e);
        } finally {
            Closeables.closeQuietly(in);
        }
        return new PropertiesBackedAppMeta(properties);
    }
    
    private PropertiesBackedAppMeta(Properties properties) {
        super();
        this.properties = properties;
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
