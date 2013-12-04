package com.atoito.clap.api;

/**
 * Main container for application metadata, such as name and version.
 */
public interface AppMeta {

    /**
     * @return a string representing the app name, used to build some path to configuration files.
     */
    String name();

    /**
     * @return the current version for the app.
     */
    String version();

    /**
     * @return a string displayed from some command, such as --version.
     */
    String displayName();
}
