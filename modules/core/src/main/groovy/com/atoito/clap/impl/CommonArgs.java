package com.atoito.clap.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Container for common parameters.
 * These params are usable with every command, before the command itself.
 */
@Parameters
public class CommonArgs {

    @Parameter(names = { "-h", "--help" }, description = "Print help")
    public boolean help = false;

    @Parameter(names = { "-d", "--debug" }, description = "Set output level to debug")
    public boolean debug = false;

    @Parameter(names = { "-i", "--info" }, description = "Set output level to info")
    public boolean info = false;

    @Parameter(names = { "-s", "--stacktrace" }, description = "Show stacktrace if an exception is thrown")
    public boolean stacktrace = false;
}
