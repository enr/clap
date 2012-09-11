package clap.uat.app.noop;

import javax.inject.Inject;

import com.github.enr.clap.api.AbstractNoopAwareCommand;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Reporter;

/*
 * echo messages
 */
public class EchoCommand extends AbstractNoopAwareCommand {

	private static final String COMMAND_ID = "echo";
	
	//private Reporter reporter;

	protected EchoCommandArgs args = new EchoCommandArgs();
	
	@Inject
	public EchoCommand(Reporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public String getId() {
		return COMMAND_ID;
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}

    @Override
    protected boolean isNoop() {
        return args.noop;
    }

    @Override
    protected String explain() {
        return "echo_explain";
    }

    @Override
    protected CommandResult internalExecute() {
        reporter.out(args.message);
        return new CommandResult();
    }

}
