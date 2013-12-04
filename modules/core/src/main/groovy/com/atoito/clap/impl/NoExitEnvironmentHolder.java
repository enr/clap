package com.atoito.clap.impl;

import javax.inject.Inject;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Reporter;
import com.atoito.clap.impl.DefaultEnvironmentHolder;


/**
 * Environment holder set to avoid the System.exit in a Clap app.
 * Useful for automated executions or tests.
 */
public class NoExitEnvironmentHolder extends DefaultEnvironmentHolder {

	@Inject
	public NoExitEnvironmentHolder(AppMeta meta, Reporter reporter) {
		super(meta, reporter);
	}

	@Override
	public boolean canExit() {
		return false;
	}

}
