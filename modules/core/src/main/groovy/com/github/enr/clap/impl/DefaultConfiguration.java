package com.github.enr.clap.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import javax.inject.Inject;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.ConfigurationReader;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

public class DefaultConfiguration implements Configuration {

    private ConfigurationReader configurationReader;
    private Reporter reporter;
    private EnvironmentHolder environment;

    private AppMeta meta;

    /*
     * A map with keys configuration files paths and value a boolean indicating
     * if the file has been loaded, which configuration has attemped to load
     */
    Map<String, Boolean> paths = Maps.newHashMap();

    @Inject
    public DefaultConfiguration(AppMeta meta, EnvironmentHolder environment, ConfigurationReader configurationReader,
            Reporter reporter) {
        this.meta = meta;
        this.configurationReader = configurationReader;
        this.reporter = reporter;
        this.environment = environment;
        this.reporter.info("configurationReader = %s", this.configurationReader);
        init();
    }

    /*
     * For programmatic (no injected) usage.
     * Used for dedicated instance creations and maybe tests...
     */
    private DefaultConfiguration(AppMeta meta, EnvironmentHolder environment, ConfigurationReader configurationReader,
            Reporter reporter, String configurationPath) {
        this.meta = meta;
        this.configurationReader = configurationReader;
        this.reporter = reporter;
        this.environment = environment;
        this.reporter.info("configurationReader = %s", this.configurationReader);
        if (Strings.isNullOrEmpty(configurationPath)) {
            init();
        } else {
            load(configurationPath);
            this.configurationReader.build();
        }
    }

    /*
     * Load configuration from the default locations.
     */
    private void init() {
        String installationConfigurationPath = installationConfigurationPath();
        String systemConfigurationPath = systemConfigurationPath();
        String userConfigurationPath = userConfigurationPath();
        reporter.debug("installationConfigurationPath = %s", installationConfigurationPath);
        reporter.debug("systemConfigurationPath = %s", systemConfigurationPath);
        reporter.debug("userConfigurationPath = %s", userConfigurationPath);
        boolean installationConfigurationLoaded = load(installationConfigurationPath);
        boolean systemConfigurationLoaded = load(systemConfigurationPath);
        boolean userConfigurationLoaded = load(userConfigurationPath);
        if (!installationConfigurationLoaded && !systemConfigurationLoaded && !userConfigurationLoaded) {
            reporter.info("No configuration file loaded...");
        }
        configurationReader.build();
    }

    /*
     * returns the path to the main configuration file for the given
     * installation
     */
    private String installationConfigurationPath() {
        File configurationDirectory = environment.installationConfigurationDirectory();
        StringBuilder sb = new StringBuilder().append(configurationDirectory).append(File.separator)
                .append(meta.name()).append(".groovy");
        return sb.toString();
    }

    /*
     * returns the path to the main configuration file for the system where Pick
     * is running
     */
    private String systemConfigurationPath() {
        File configurationDirectory = environment.systemConfigurationDirectory();
        StringBuilder sb = new StringBuilder().append(configurationDirectory).append(File.separator)
                .append(meta.name()).append(".groovy");
        return sb.toString();
    }

    /*
     * returns the path to the main configuration file for the user launching
     * Pick
     */
    private String userConfigurationPath() {
        File configurationDirectory = environment.userConfigurationDirectory();
        StringBuilder sb = new StringBuilder().append(configurationDirectory).append(File.separator)
                .append(meta.name()).append(".groovy");
        return sb.toString();
    }

    /*
     * returns true if configuration file exists and it has been loaded and
     * parsed correctly
     */
    private boolean load(String path) {
        // reporter.out("configuration load %s", path);
        Preconditions.checkNotNull(path);
        File configurationFile = new File(path);
        paths.put(path, false);
        // Preconditions.checkArgument(configurationFile.exists(),
        // "configuration %s not found", path);
        if (!configurationFile.exists()) {
            reporter.info("configuration %s not found", path);
            return false;
        }
        try {
            configurationReader.addConfiguration(configurationFile.toURI().toURL());
        } catch (MalformedURLException e) {
            Throwables.propagate(e);
        }
        paths.put(path, true);
        return true;
    }

    @Override
    public <T> T get(String key) {
        return configurationReader.get(key);
    }

    @Override
    public Map<String, Boolean> getPaths() {
        return paths;
    }

    @Override
    public <T> T get(String key, T defaultValue) {
        T value = get(key);
        return ((value == null) ? defaultValue : value);
    }

    @Override
    public void addPath(String configurationPath) {
        load(configurationPath);
        configurationReader.build();
    }

    @Override
    public Map<String, Object> getAllProperties() {
        return configurationReader.getAllProperties();
    }

    @Override
    public Map<String, Object> getBulk(String prefix) {
        return configurationReader.getBulk(prefix);
    }

    /**
     * Sometimes we need a brand new instance, dedicated to a given configuration file.
     */
    @Override
    public Configuration getDedicatedInstance(String configurationPath) {
        ConfigurationReader confReader = configurationReader.getDedicatedInstance();
        Configuration configuration = new DefaultConfiguration(meta, environment, confReader, reporter, configurationPath);
        return configuration;
    }

    /*
     * cerca un file nelle 3 dir di configurazione.
     * 
     * private String getFileFromConfigurationDirs(String filename) { List<File>
     * configurationDirectories =
     * Lists.newArrayList(environment.installationConfigurationDirectory(),
     * environment.systemConfigurationDirectory(),
     * environment.userConfigurationDirectory()); for (File dir :
     * configurationDirectories) { File file = new File(dir, filename);
     * reporter.info("looking for dataset configuration file:%n- %s",
     * file.getAbsolutePath()); if (file.exists()) { return
     * file.getAbsolutePath(); } } return null; }
     */

    /*
     * @Override public File applicationHome() { return
     * environment.applicationHome(); }
     */

}
