package com.github.enr.clap.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

/*
 * parameters for main command.
 */
@Parameters
public class MainCommandArgs implements CommonArgsAware {

	
	@Parameter(names = { "-v", "--version" }, description = "Print version")
	private boolean version;

	/*
	@Parameter(names = { "-h", "--help" }, description = "Print help")
	private boolean help = false;
	@Parameter(names = { "-d", "--debug" }, description = "Set output level to debug")
	private boolean debug = false;
	@Parameter(names = { "-i", "--info" }, description = "Set output level to info")
	private boolean info = false;
	*/

	@Parameter(names = { "-c", "--configurations" }, description = "Print out configuration files")
	private boolean configurations = false;
	
	@ParametersDelegate
	public CommonArgs common = new CommonArgs();
	
	public boolean isVersion() {
		return version;
	}

	public boolean isConfigurations() {
		return configurations;
	}

	@Override
	public boolean isHelp() {
		return common.help;
	}

	@Override
	public boolean isInfo() {
		return common.info;
	}

	@Override
	public boolean isDebug() {
		return common.debug;
	}
	
	@Override
	public boolean isStacktrace() {
		return common.stacktrace;
	}
	
}
