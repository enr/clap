package com.github.enr.clap.api;

/**
 * Interface for Reporters returning the full output as String from the method getOutput().
 *
 */
public interface OutputRetainingReporter extends Reporter {

    String getOutput();

}
