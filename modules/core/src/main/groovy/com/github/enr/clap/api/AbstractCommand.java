package com.github.enr.clap.api;


/*
 * utility class for some common functionality
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
    /*
    @SuppressWarnings("unchecked")
    protected static <T> T args(Object obj, Class<T> type)
    {
        // ? (type.isInstance(obj))
        if ((obj != null) && (type.isAssignableFrom(obj.getClass())))
        {
            return (T) obj;
        }
        return null;
    }
    */
    
    protected void init() {}
    
    abstract protected CommandResult internalExecute();
}
