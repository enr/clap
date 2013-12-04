package com.atoito.clap.impl;

import javax.inject.Inject;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Command;
import com.atoito.clap.api.CommandResult;
import com.atoito.clap.api.Constants;
import com.atoito.clap.api.Reporter;

/*
 * default command, executed if no command id is passed to Clap app
 */
public class MainCommand implements Command {

    // private static final String ID = "main";

    private Reporter reporter;

    private AppMeta meta;

    private DefaultMainCommandArgs args = new DefaultMainCommandArgs();

    @Inject
    public MainCommand(AppMeta meta, Reporter reporter) {
        this.meta = meta;
        this.reporter = reporter;
    }

    @Override
    public CommandResult execute() {
        CommandResult result = new CommandResult();
        reporter.debug("main args = %s", args);
        if (args.isVersion()) {
            reporter.out("%s version %s", meta.displayName(), meta.version());
            result.setExitValue(0);
        } else {
            reporter.out("do nothing, as requested");
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
