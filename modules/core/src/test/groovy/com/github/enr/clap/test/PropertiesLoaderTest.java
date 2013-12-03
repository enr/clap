package com.github.enr.clap.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.testng.annotations.Test;

import com.github.enr.clap.util.PropertiesLoader;

public class PropertiesLoaderTest {

    @Test
    public void testLoadFromResource() throws IOException {
        String resourceName = "properties/PropertiesLoader.properties";
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties = propertiesLoader.fromResource(resourceName);
        assertThat(properties).as("properties from " + resourceName).hasSize(1)
                .contains(entry("test.key", "test.value")).doesNotContain(entry("chiave.test", "valore.test"));
    }
    
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void shouldFailIfFileNotFound() throws IOException {
    	PropertiesLoader propertiesLoader = new PropertiesLoader();
    	propertiesLoader.fromResource("clap-notexists.properties");
    }

    @Test
    public void testLoadFromInputStream() throws IOException {
        String resourceName = "properties/PropertiesLoader.properties";
        URL url = ClassLoader.getSystemResource(resourceName);
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        InputStream stream = url.openStream();
		Properties properties = propertiesLoader.fromInputStream(stream);
		stream.close();
        assertThat(properties).as("properties from " + resourceName).hasSize(1)
                .contains(entry("test.key", "test.value")).doesNotContain(entry("chiave.test", "valore.test"));
    }

    @Test
    public void testLoadFromFile() throws IOException {
        String filename = "src/test/resources/properties/PropertiesLoader.properties";
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        File file = new File(filename);
        assertThat(file).as("file "+file.getPath()).exists();
        Properties properties = propertiesLoader.fromFile(file);
        assertThat(properties).as("properties from " + filename).hasSize(1)
                .contains(entry("test.key", "test.value")).doesNotContain(entry("chiave.test", "valore.test"));
    }

    @Test
    public void testLoadFromFilename() throws IOException {
        String filename = "src/test/resources/properties/PropertiesLoader.properties";
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties = propertiesLoader.fromFilename(filename);
        assertThat(properties).as("properties from " + filename).hasSize(1)
                .contains(entry("test.key", "test.value")).doesNotContain(entry("chiave.test", "valore.test"));
    }

}
