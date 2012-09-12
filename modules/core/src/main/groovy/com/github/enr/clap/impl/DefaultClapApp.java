package com.github.enr.clap.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.ClapApp;
import com.github.enr.clap.api.Command;
import com.github.enr.clap.api.CommandResult;
import com.github.enr.clap.api.Constants;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.api.Reporter.Level;
import com.github.enr.clap.util.Casts;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public class DefaultClapApp implements ClapApp {

    private List<Command> commands;

    // private Configuration configuration;
    private Reporter reporter;
    private EnvironmentHolder environment;
    private AppMeta meta;
    
    private boolean exited;
    
    private int exitValue;

    /*
     * The command executed if no command id is given. Usually it contains
     * --help, --version, --noop args management.
     */
    @Inject
    @Named(Constants.MAIN_COMMAND_BIND_NAME)
    private Command mainCommand;

    @Inject
    public DefaultClapApp(AppMeta meta, EnvironmentHolder environment, Reporter reporter) {
        this.meta = meta;
        this.environment = environment;
        this.reporter = reporter;
    }

    @Override
    public void run(String[] args) {

        CommonArgsAware mainArgs = Casts.cast(mainCommand.getParametersContainer());
        JCommander jc = new JCommander(mainArgs);
        jc.setProgramName(meta.name());

        for (Command command : commands) {
            if (!Constants.MAIN_COMMAND_ID.equals(command.getId())) {
                jc.addCommand(command.getId(), command.getParametersContainer());
            }
        }
        try {
            jc.parse(args);
        } catch (ParameterException throwable) {
            Throwable cause = Throwables.getRootCause(throwable);
            reporter.warn("something went wrong. catched %s", cause.getClass().getName());
            reporter.warn(cause.getMessage().trim());
            systemExit(1);
            if (exited) {
                return;
            }
        }

        setReportingLevel(reporter, mainArgs);

        String commandId = jc.getParsedCommand();
        reporter.debug("commandId %s", commandId);

        reporter.debug("starting %s with home %s", meta.name(), environment.applicationHome());

        if ((args.length == 0) || (mainArgs.isHelp())) {
            usageForCommand(jc, commandId);
            systemExit(0);
            if (exited) {
                return;
            }
        }

        try {
            CommandResult result = executeCommand(commandId);
            Preconditions.checkState((result != null), "error retrieving result for command '%s'", commandId);
            reporter.debug("result = %s", result);
            manageFailure(result);
            systemExit(result.getExitValue());
        } catch (Throwable throwable) {
            Throwable cause = Throwables.getRootCause(throwable);
            reporter.warn("something went wrong. catched %s", cause.getClass().getName());
            reporter.warn(cause.getMessage());
            if (mainArgs.isStacktrace()) {
                reporter.warn(Throwables.getStackTraceAsString(throwable));
            }
            usageForCommand(jc, commandId);
            systemExit(1);
        }
    }

    private void manageFailure(CommandResult result) {
        if (result.isFailure()) {
            reporter.warn("command failure");
            if (result.getFailureMessage() != null) {
                reporter.warn("> %s", result.getFailureMessage());
            }
        }
    }

    private void setReportingLevel(Reporter reporter, CommonArgsAware mainArgs) {
        if (mainArgs.isInfo()) {
            reporter.setLevel(Level.INFO);
        }
        if (mainArgs.isDebug()) {
            reporter.setLevel(Level.DEBUG);
        }
    }

    private void usageForCommand(JCommander jc, String commandId) {
        if (commandId == null) {
            jc.usage();
        } else {
            jc.usage(commandId);
        }
    }

    /*
     * system exit, but only if environment allowed. probably, this is true in
     * the actual running and false in the acceptance test phase.
     */
    private void systemExit(int value) {
        setExitValue(value);
        exited = true;
        if (environment.canExit()) {
            System.exit(value);
        }
    }

    private CommandResult executeCommand(String commandId) {
        reporter.debug("command       %s", commandId);
        Command command = null;
        if (commandId == null) {
            command = mainCommand;
        } else {
            command = commandFromId(commandId);
        }
        if (command == null) {
            return resultForCommandNotFound();
        }
        return command.execute();

    }

    private CommandResult resultForCommandNotFound() {
        CommandResult result = new CommandResult();
        return result;
    }

    @Override
    public void setAvailableCommands(List<Command> commands) {
        this.commands = commands;
    }

    private Command commandFromId(String commandId) {
        for (Command command : commands) {
            if (commandId.equals(command.getId())) {
                return command;
            }
        }
        return null;
    }

    public int getExitValue() {
        return exitValue;
    }

    private void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

}
