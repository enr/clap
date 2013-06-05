package com.github.enr.clap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Default implementation for PropertiesLoader.
 */
public class PropertiesLoader {

    public Properties fromFilename(String filename) throws IOException {
		FileInputStream is = new FileInputStream(filename);
		return fromInputStream(is);
    }
	
	/**
	 * Returns properties from InputStream.
	 * The stream is closed, after the loading.
	 */
    public Properties fromInputStream(InputStream is) throws IOException {
        final Properties props = new Properties();
        //FileInputStream fis = new FileInputStream(filename);
        props.load(is);
        is.close();
        return props;
    }

    /**
     * Load a properties file from the classpath.
     * 
     * @param propsName
     * @return Properties
     * @throws Exception
     */
    public Properties fromResource(String propsName) throws IOException {
        if (propsName == null) {
        	throw new IllegalArgumentException("Resource name should not be null");
        }
        Properties props = new Properties();
        URL url = ClassLoader.getSystemResource(propsName);
        if (url == null) {
        	throw new IllegalArgumentException("Resource not found: "+propsName);
        }
        props.load(url.openStream());
        return props;
    }

    /**
     * Load a Properties File.
     * 
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public Properties fromFile(File propsFile) throws IOException {
    	return fromInputStream( new FileInputStream(propsFile));
    }

    public Properties fromResourceBundle(ResourceBundle bundle) {
        ResourceBundle rb = bundle;
        Properties result = new Properties();
        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            final String key = keys.nextElement();
            final String value = rb.getString(key);
            result.put(key, value);
        }
        return result;
    }
}