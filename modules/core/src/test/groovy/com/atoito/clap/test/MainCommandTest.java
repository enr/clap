package com.atoito.clap.test;

import static com.atoito.clap.test.ClapAssertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Command;
import com.atoito.clap.api.CommandResult;
import com.atoito.clap.impl.DefaultMainCommandArgs;
import com.atoito.clap.impl.DefaultOutputRetainingReporter;
import com.atoito.clap.impl.MainCommand;

@Test(suiteName = "Util package")
public class MainCommandTest {
	
	private static class MainCommandTestAppMeta implements AppMeta {
		@Override
		public String name() {
			return "MainCommandTestAppMeta";
		}
		@Override
		public String version() {
			return "0.5";
		}
		@Override
		public String displayName() {
			return "The MainCommandTestAppMeta";
		}
	}

	Command mainCommand;
	AppMeta meta = new MainCommandTestAppMeta();
	DefaultOutputRetainingReporter reporter;
	DefaultMainCommandArgs args;

    @BeforeMethod
    public void beforeMethod() {
    	reporter = new DefaultOutputRetainingReporter();
        mainCommand = new MainCommand(meta, reporter);
        args = (DefaultMainCommandArgs) mainCommand.getParametersContainer();
    }

    @Test
    public void versionArg() {
    	args.version = true;
    	CommandResult result = mainCommand.execute();
    	assertThat(result).hasExitValue(0);
    	assertThat(reporter.getOutput()).as("reporter output").isEqualTo("The MainCommandTestAppMeta version 0.5\n");
    }

}
