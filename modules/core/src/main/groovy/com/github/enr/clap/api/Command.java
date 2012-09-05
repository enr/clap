package com.github.enr.clap.api;

/*
 * interface for Clap commands
 */
public interface Command {

    /*
     * returns the id for this command
     */
    String getId();

    CommandResult execute();

    /*
     * It's supposed to return a parameters container object (ie an instance
     * annotated with Jcommander @Parameters
     */
    Object getParametersContainer();
}
