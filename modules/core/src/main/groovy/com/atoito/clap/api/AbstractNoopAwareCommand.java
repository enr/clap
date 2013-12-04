package com.atoito.clap.api;

/**
 * Base class for commands allowing a noop option.
 */
public abstract class AbstractNoopAwareCommand implements Command {

    protected Object args;
    
    protected Reporter reporter;

    @Override
    public CommandResult execute() {
        init();
        if (isNoop()) {
            reporter.out(explain());
            return new CommandResult();
        }
        return internalExecute();
    }

    @Override
    public Object getParametersContainer() {
        return args;
    }

    protected void init() {
    }

    abstract protected boolean isNoop();

    abstract protected String explain();

    abstract protected CommandResult internalExecute();
}
