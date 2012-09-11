package clap.uat.app.over;

import javax.inject.Inject;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Command;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Constants;
import com.github.enr.clap.api.Reporter;

/*
 * default command, executed if no command id is passed to Clap app
 */
public class OverMainCommand implements Command {

    private Reporter reporter;

    private AppMeta meta;

    private OverMainCommandArgs args = new OverMainCommandArgs();

    @Inject
    public OverMainCommand(AppMeta meta, Reporter reporter) {
        this.meta = meta;
        this.reporter = reporter;
    }

    @Override
    public CommandResult execute() {
        CommandResult result = new CommandResult();
        reporter.info("args_info=%s", args.isInfo());
        if (args.isVersion()) {
            reporter.out("%s Wersion %s", meta.displayName(), meta.version());
            result.setExitValue(0);
        } else {
            reporter.out("over_output");
        }
        return result;
    }

    @Override
    public String getId() {
        return Constants.MAIN_COMMAND_ID;
    }

    @Override
    public Object getParametersContainer() {
        return args;
    }

}
