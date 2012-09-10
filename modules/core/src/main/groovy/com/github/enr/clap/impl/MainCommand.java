package com.github.enr.clap.impl;

import java.util.Map;

import javax.inject.Inject;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Command;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.Constants;
import com.github.enr.clap.api.Reporter;

/*
 * default command, executed if no command id is passed to Clap app
 */
public class MainCommand implements Command {

    // private static final String ID = "main";

    private Reporter reporter;

    private AppMeta meta;

    private Configuration configuration;

    private MainCommandArgs args = new MainCommandArgs();

    @Inject
    public MainCommand(Configuration configuration, AppMeta meta, Reporter reporter) {
        this.configuration = configuration;
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
//        } else if (args.isConfigurations()) {
//            reporter.out("Configuration files:");
//            for (Map.Entry<String, Boolean> entry : configuration.getPaths().entrySet()) {
//                reporter.out("- %s (%s)", entry.getKey(), entry.getValue());
//            }
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
