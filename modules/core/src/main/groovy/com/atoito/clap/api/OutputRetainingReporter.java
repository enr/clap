package com.atoito.clap.api;

/**
 * Interface for Reporters returning the full output as String from the method getOutput().
 * Useful in testing, to verify the actual commands output.
 *
 */
public interface OutputRetainingReporter extends Reporter {

    String getOutput();

}
