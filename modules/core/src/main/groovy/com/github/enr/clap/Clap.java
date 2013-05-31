package com.github.enr.clap;

import java.io.File;

import com.github.enr.clap.api.ClapApp;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.OutputRetainingReporter;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.inject.Bindings;
import com.github.enr.clap.inject.ConventionalAppModule;
import com.github.enr.clap.inject.ReviewableAppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * Utility class with static methods to start Clap apps.
 */
public class Clap {
    
    public static class RunResult {
        private int exitValue;
        private String output;
        public int getExitValue() {
            return exitValue;
        }
        public void setExitValue(int exitValue) {
            this.exitValue = exitValue;
        }
        public String getOutput() {
            return output;
        }
        public void setOutput(String output) {
            this.output = output;
        }
    }

    /**
     * Run a Clap app with settings following conventions.
     * 
     * @param args user args
     * @param module Guice module defining the app custom components
     */
    public static void runConventionalApp(String[] args, Module module) {
        Injector injector = Guice.createInjector(Modules.override(new ConventionalAppModule()).with(module));
        ClapApp app = injector.getInstance(ClapApp.class);
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(args);
    }
    
    /**
     * Run a Clap app which doesn't call System.exit, keeps the exit value and the output 
     * for a later check and forces app home to a given directory.
     * Useful for testing (see uat module for example of an actual usage).
     * 
     * @param args user args
     * @param appHome app home directory
     * @param module module Guice module defining the app custom components
     * @return an object containing the exit value and the output
     */
    public static RunResult runReviewableApp(String[] args, File appHome, Module module) {
        RunResult result = new RunResult();        
        Injector injector = Guice.createInjector(Modules.override(new ReviewableAppModule()).with(module));
        EnvironmentHolder environment = injector.getInstance(EnvironmentHolder.class);
        environment.forceApplicationHome(appHome);
        Reporter reporter = injector.getInstance(Reporter.class);
        ClapApp app = injector.getInstance(ClapApp.class);
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(args);
        result.setExitValue(app.getExitValue());
        if (reporter instanceof OutputRetainingReporter) {
            result.setOutput(((OutputRetainingReporter) reporter).getOutput().trim());
        } else {
            result.setOutput(null);
        }
        return result;
    }
}
