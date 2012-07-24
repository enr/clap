package com.github.enr.clap.api;

import java.io.File;

public interface EnvironmentHolder {

	File applicationHome();

	/*
	 * directory containing configuration files in the installation
	 * ie the config/ dir into application home.
	 */
	File installationConfigurationDirectory();

	/*
	 * directory containing configuration files in the current system
	 */
	File systemConfigurationDirectory();

	/*
	 * directory containing configuration files for the user launching app.
	 */
	File userConfigurationDirectory();

	/*
	 * if the current process can call System.exit
	 * probably it will be true for the actual application and false in acceptance tests.
	 */
	boolean canExit();

	//String getApplicationName();

	//public String getApplicationVersion();
}