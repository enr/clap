package com.atoito.clap.impl;

public interface CommonArgsAware {
    boolean isHelp();

    boolean isInfo();

    boolean isDebug();

    boolean isStacktrace();
}
