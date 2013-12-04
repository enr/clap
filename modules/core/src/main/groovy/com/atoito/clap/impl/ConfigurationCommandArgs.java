package com.atoito.clap.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Informations about configuration")
public class ConfigurationCommandArgs {

    @Parameter(names = { "-f", "--files" }, description = "List all configuration files")
    public boolean files;

    @Parameter(names = { "-l", "--list" }, description = "List all variables set in config files")
    public boolean list;

    @Parameter(names = { "-g", "--get" }, description = "Get the value for a given key")
    public String get;

}
