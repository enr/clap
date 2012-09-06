package com.github.enr.clap.api;

import javax.inject.Inject;

/*
 * Temporary work-around to get Guice components in static methods.
 */
public class ComponentsRegistry {

    @Inject private static Reporter reporter;
    
    public static Reporter getReporter() {
        return ComponentsRegistry.reporter;
    }
}
