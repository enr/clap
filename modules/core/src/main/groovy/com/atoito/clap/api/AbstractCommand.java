package com.atoito.clap.api;

/**
 * Base class for commands, with some common functionality.
 */
public abstract class AbstractCommand implements Command {

    protected Object args;

    @Override
    public CommandResult execute() {
        init();
        return internalExecute();
    }

    @Override
    public Object getParametersContainer() {
        return args;
    }

    protected void init() {
    }

    abstract protected CommandResult internalExecute();
}
