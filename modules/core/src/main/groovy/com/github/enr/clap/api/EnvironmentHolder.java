package com.github.enr.clap.api;

import java.io.File;

public interface EnvironmentHolder {

    File applicationHome();
    
    /**
     * Forces the application home to a given dir; sometimes we need this, ie in tests.
     */
    void forceApplicationHome(File home);

    /**
     * @return the directory containing configuration files in the installation (ie the
     * config/ dir into application home).
     */
    File installationConfigurationDirectory();

    /**
     * @return the directory containing configuration files in the current system.
     * The actual path depends from the os.
     */
    File systemConfigurationDirectory();

    /**
     * @return directory containing configuration files for the user launching app.
     */
    File userConfigurationDirectory();

    /**
     * @return if the current process can call System.exit.
     * Probably it will be true for the actual application and false in tests.
     */
    boolean canExit();

}
