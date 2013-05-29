package com.github.enr.clap.api;

/**
 * Container for the result of the executed command.
 */
public class CommandResult {

    private boolean success = true;

    private String failureMessage;

    private Integer exitValue;

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

    /**
     * 
     * @return the exit value. If it is not set, this method returns 0 if success == true or 1 otherwise.
     */
    public int getExitValue() {
        return (exitValue == null ? (success ? 0 : 1) : exitValue);
    }

    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    /**
     * Factory method for failing result.
     * 
     * @return a CommandResult with success=false
     */
    public static CommandResult failure() {
        CommandResult result = new CommandResult();
        result.success = false;
        return result;
    }

    /**
     * Factory method for successful result.
     * 
     * @return a CommandResult with success=true
     */
    public static CommandResult successful() {
        CommandResult result = new CommandResult();
        result.success = true;
        return result;
    }

    public CommandResult withFailureMessage(String message) {
        this.failureMessage = message;
        return this;
    }

    public CommandResult withExitStatus(int status) {
        this.exitValue = status;
        return this;
    }
}
