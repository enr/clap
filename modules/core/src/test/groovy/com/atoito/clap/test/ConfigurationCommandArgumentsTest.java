package com.atoito.clap.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.atoito.clap.impl.DefaultMainCommandArgs;
import com.beust.jcommander.JCommander;

@Test(suiteName = "Command Line Arguments")
public class ConfigurationCommandArgumentsTest {

    JCommander jc;
    DefaultMainCommandArgs cm;

    @BeforeMethod
    public void setupJcommander() {
        cm = new DefaultMainCommandArgs();
        jc = new JCommander(cm);
    }

    @Test(description = "No arguments parsing")
    public void noArgs() {
        jc.parse(new String[0]);
        //assertThat(cm.isConfigurations()).as("--configuration").isFalse();
        assertThat(cm.isDebug()).as("--debug").isFalse();
        assertThat(cm.isHelp()).as("--help").isFalse();
        assertThat(cm.isInfo()).as("--info").isFalse();
        assertThat(cm.isStacktrace()).as("--stacktrace").isFalse();
        assertThat(cm.isVersion()).as("--version").isFalse();
    }

    @Test(description = "'help' argument parsing")
    public void helpArgs() {
        jc.parse("-h");
        //assertThat(cm.isConfigurations()).as("--configuration").isFalse();
        assertThat(cm.isDebug()).as("--debug").isFalse();
        assertThat(cm.isHelp()).as("--help").isTrue();
        assertThat(cm.isInfo()).as("--info").isFalse();
        assertThat(cm.isStacktrace()).as("--stacktrace").isFalse();
        assertThat(cm.isVersion()).as("--version").isFalse();
    }

    @Test(description = "multiple arguments parsing")
    public void multipleArgs() {
        jc.parse("-h", "--stacktrace", "-d");
        //assertThat(cm.isConfigurations()).as("--configuration").isFalse();
        assertThat(cm.isDebug()).as("--debug").isTrue();
        assertThat(cm.isHelp()).as("--help").isTrue();
        assertThat(cm.isInfo()).as("--info").isFalse();
        assertThat(cm.isStacktrace()).as("--stacktrace").isTrue();
        assertThat(cm.isVersion()).as("--version").isFalse();
    }
}