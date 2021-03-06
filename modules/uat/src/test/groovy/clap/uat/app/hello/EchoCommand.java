package clap.uat.app.hello;

import javax.inject.Inject;

import com.atoito.clap.api.Command;
import com.atoito.clap.api.CommandResult;
import com.atoito.clap.api.Reporter;

/*
 * echo messages
 */
public class EchoCommand implements Command {

	private static final String COMMAND_ID = "echo";
	
	private Reporter reporter;

	private EchoCommandArgs args = new EchoCommandArgs();
	
	@Inject
	public EchoCommand(Reporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public CommandResult execute() {
		reporter.out(args.message);
		return new CommandResult();
	}

	@Override
	public String getId() {
		return COMMAND_ID;
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}

}
