package com.atoito.clap.impl;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Constants;

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
