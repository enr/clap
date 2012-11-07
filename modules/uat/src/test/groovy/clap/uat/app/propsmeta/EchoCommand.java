package clap.uat.app.propsmeta;

import javax.inject.Inject;

import com.github.enr.clap.api.Command;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Reporter;

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
