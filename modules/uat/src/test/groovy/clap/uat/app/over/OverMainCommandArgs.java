package clap.uat.app.over;

import com.atoito.clap.impl.CommonArgsAware;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/*
 * parameters for Over app main command.
 */
@Parameters
public class OverMainCommandArgs implements CommonArgsAware {

    @Parameter(names = { "-w", "--wersion" }, description = "Print version")
    private boolean version;

    @Parameter(names = { "-e", "--elp" }, description = "Print help")
    public boolean help = false;

    @Parameter(names = { "-v", "--verbose" }, description = "Set output level to verbose (debug)")
    public boolean debug = false;

    @Parameter(names = { "-h", "--hinfo" }, description = "Set output level to info")
    public boolean info = false;

    @Parameter(names = { "-z", "--ztacktrace" }, description = "Show stacktrace if an exception is thrown")
    public boolean stacktrace = false;

    public boolean isVersion() {
        return version;
    }

    @Override
    public boolean isHelp() {
        return help;
    }

    @Override
    public boolean isInfo() {
        return info;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public boolean isStacktrace() {
        return stacktrace;
    }

}
