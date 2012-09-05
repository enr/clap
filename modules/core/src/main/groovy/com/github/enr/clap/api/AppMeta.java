package com.github.enr.clap.api;

/*
 * main container for application metadata, such as name and version
 */
public interface AppMeta {

    String name();

    String version();

    String displayName();
}
