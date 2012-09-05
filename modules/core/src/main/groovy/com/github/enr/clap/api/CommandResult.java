package com.github.enr.clap.api;

/*
 * container for the result of the executed command.
 */
public class CommandResult {

    private boolean success = true;

    private String failureMessage;

    private int exitValue = 0;

    public CommandResult() {
    }

    public boolean isFailure() {
        return (!success);
    }

    public void fail() {
        success = false;
    }

    public void failWithMessage(String failureMessage) {
        success = false;
        setFailureMessage(failureMessage);
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public int getExitValue() {
        return exitValue;
    }

    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
}
