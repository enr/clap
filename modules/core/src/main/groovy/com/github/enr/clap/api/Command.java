package com.github.enr.clap.api;

/**
 * Interface for Clap commands.
 */
public interface Command {

    /**
     * @return the id for this command.
     */
    String getId();

    CommandResult execute();

    /**
     * @return a parameters container object (ie an instance annotated with Jcommander Parameters annotation).
     */
    Object getParametersContainer();
}
