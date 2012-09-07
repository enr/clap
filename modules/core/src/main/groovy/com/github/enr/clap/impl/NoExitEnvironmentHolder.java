package com.github.enr.clap.impl;

import javax.inject.Inject;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.impl.DefaultEnvironmentHolder;


/*
 * Environment holder settled to avoid the System.exit in a Clap app.
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
