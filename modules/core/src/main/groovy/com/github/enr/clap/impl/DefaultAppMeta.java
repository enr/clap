package com.github.enr.clap.impl;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Constants;

public class DefaultAppMeta implements AppMeta {

	@Override
	public String name() {
		return Constants.DEFAULT_META_NAME;
	}

	@Override
	public String version() {
		return Constants.DEFAULT_META_VERSION;
	}

	@Override
	public String displayName() {
		return Constants.DEFAULT_META_DISPLAYNAME;
	}

}
