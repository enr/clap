package com.github.enr.clap.test;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.URL;
import java.util.Map;

import org.fest.assertions.api.MapAssert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.enr.clap.api.ConfigurationReader;
import com.github.enr.clap.impl.GroovierFlattenConfigurationReader;
import com.github.enr.clap.util.ResourcesLoader;

@Test(suiteName = "Configuration")
public class GroovyConfigurationTest {

    ConfigurationReader configuration;

    @BeforeMethod
    public void initConfiguration() {
        configuration = new GroovierFlattenConfigurationReader();
    }

    @AfterMethod
    public void resetConfiguration() {
        configuration.reset();
    }

    @Test
    public void testConfigurationBuilding() {
        URL firstConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/groovy-config-1.groovy");
        assertThat(firstConfig).as("configuration url").isNotNull();
        configuration.addConfiguration(firstConfig);
        configuration.addBinding("binded", "xxx");
        String enviroment = "production";
        boolean buildSuccess = configuration.build(enviroment);
        assertThat(buildSuccess).as("configuration.build()").isTrue();
        assertConfigurationStringEqualsTo("e", "e-production", "configuration e");
        Boolean aOne = configuration.get("a.one");
        assertThat(aOne).as("configuration a.one").isTrue();
    }

    @Test
    public void testBinding() {
        URL firstConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/groovy-config-1.groovy");
        assertThat(firstConfig).as("configuration url").isNotNull();
        configuration.addConfiguration(firstConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build();
        assertThat(buildSuccess).as("configuration.build()").isTrue();
        assertConfigurationStringEqualsTo("b", "B1", "binded configuration b");
    }

    @Test
    public void testGroovyString() {
        URL firstConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/groovy-config-1.groovy");
        assertThat(firstConfig).as("configuration url").isNotNull();
        configuration.addConfiguration(firstConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build();
        assertThat(buildSuccess).as("configuration.build()").isTrue();
        assertConfigurationStringEqualsTo("groovier", "Hello Groovy!", "configuration groovier");
    }

    @Test
    public void testConfigurationMerging() {
        String enviroment = "production";
        URL firstConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/first-config.groovy");
        assertThat(firstConfig).as("first configuration url").isNotNull();
        URL secondConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/second-config.groovy");
        assertThat(secondConfig).as("second configuration url").isNotNull();
        configuration.addConfiguration(firstConfig);
        configuration.addConfiguration(secondConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build(enviroment);
        assertThat(buildSuccess).as("configuration.build()").isTrue();
        assertConfigurationStringEqualsTo("server.URL", "http://prod/url", "server url");
        assertConfigurationStringEqualsTo("app.version", "2.0", "app version");
    }

    @Test
    public void testBulk() {
        URL firstConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/first-config.groovy");
        assertThat(firstConfig).as("first configuration url").isNotNull();
        URL secondConfig = ResourcesLoader.getResource(GroovyConfigurationTest.class, "/second-config.groovy");
        assertThat(secondConfig).as("second configuration url").isNotNull();
        configuration.addConfiguration(firstConfig);
        configuration.addConfiguration(secondConfig);
        configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build();
        assertThat(buildSuccess).as("configuration.build()").isTrue();
        Map<String, Object> bulk = configuration.getBulk("my.bulk");
        String[] keys = { "a.a", "a.b", "a.c", "b.a", "c.a", "c.b", "c.c", "c.d" };
        MapAssert<String, Object> vut = assertThat(bulk);
        vut.as("my bulk").isNotNull().isNotEmpty().hasSize(8);
        for (String key : keys) {
            vut.containsKey(key);
            String configurationKey = "my.bulk." + key;
            assertConfigurationStringEqualsTo(configurationKey, key.replace(".", "_"), "bulk value " + key);
        }
    }
    
    /*
    @Test
    public void testDedicatedInstance() {
        URL firstConfig = Resources.getResource(GroovyConfigurationTest.class, "/third-config.groovy");
        assertThat(firstConfig).as("configuration url").isNotNull();
        configuration.addConfiguration(firstConfig);
        //configuration.addBinding("binded", "B");
        boolean buildSuccess = configuration.build();
        assertThat(buildSuccess).as("configuration.build()").isTrue();
        Object datasets = configuration.get("datasets");
        System.out.println (datasets);
    }
    */

    private void assertConfigurationStringEqualsTo(String configurationKey, String expectedValue, String description) {
        String groovier = configuration.get(configurationKey);
        assertThat(groovier).as(description).isEqualTo(expectedValue);
    }

    /*
     * @Test public void testDefaultValue() {
     * assertNull(configuration.get("no.such.key"));
     * assertEquals(configuration.get("no.such.key", "A value!"), "A value!"); }
     */
}
