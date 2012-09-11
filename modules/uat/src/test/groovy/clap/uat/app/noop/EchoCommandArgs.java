package clap.uat.app.noop;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Echo messages")
public class EchoCommandArgs {
    @Parameter(names = { "-m", "--message" }, description = "The message to echo")
    public String message;

    @Parameter(names = { "-n", "--noop" }, description = "Set noop")
    public boolean noop;
}
