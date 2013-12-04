package com.atoito.clap.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Constants;
import com.atoito.clap.impl.PropertiesBackedAppMeta;

@Test(suiteName = "Core components")
public class PropertiesBackedAppMetaTest {

    @Test
    public void shouldReadProperties() {
        AppMeta meta = PropertiesBackedAppMeta.from("clap-unit-tests.properties");
        assertThat(meta.name()).as("name").isEqualTo("ClapUnitTest");
        assertThat(meta.version()).as("version").isEqualTo("3.4.5");
        assertThat(meta.displayName()).as("display name").isEqualTo("Clap Unit Test Application");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void shouldFailIfFileNotFound() {
        PropertiesBackedAppMeta.from("clap-notexists.properties");
    }

    @Test
    public void shouldGiveDefaultValuesForNotFoundProperties() {
        AppMeta meta = PropertiesBackedAppMeta.from("clap-noprops.properties");
        assertThat(meta.name()).as("name").isEqualTo(Constants.DEFAULT_META_NAME);
        assertThat(meta.version()).as("version").isEqualTo(Constants.DEFAULT_META_VERSION);
        assertThat(meta.displayName()).as("display name").isEqualTo(Constants.DEFAULT_META_DISPLAYNAME);
    }

}
