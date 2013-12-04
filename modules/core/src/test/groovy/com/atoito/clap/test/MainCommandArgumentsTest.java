package com.atoito.clap.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.atoito.clap.impl.ConfigurationCommandArgs;
import com.beust.jcommander.JCommander;

@Test(suiteName = "Command Line Arguments")
public class MainCommandArgumentsTest {

    JCommander jc;
    ConfigurationCommandArgs args;

    @BeforeMethod
    public void setupJcommander() {
        args = new ConfigurationCommandArgs();
        jc = new JCommander(args);
    }

    @Test(description = "No arguments parsing")
    public void noArgs() {
        jc.parse(new String[0]);
        assertThat(args.files).as("--files").isFalse();
        assertThat(args.list).as("--list").isFalse();
        assertThat(args.get).as("--get").isNull();
    }

    @Test(description = "'files' argument parsing")
    public void fileArgs() {
        jc.parse("-f");
        assertThat(args.files).as("--files").isTrue();
        assertThat(args.list).as("--list").isFalse();
        assertThat(args.get).as("--get").isNull();
    }

    @Test(description = "'list' argument parsing")
    public void listArgs() {
        jc.parse("-l");
        assertThat(args.files).as("--files").isFalse();
        assertThat(args.list).as("--list").isTrue();
        assertThat(args.get).as("--get").isNull();
    }

    @Test(description = "'get' argument parsing")
    public void getArgs() {
        jc.parse("-g", "test.key");
        assertThat(args.files).as("--files").isFalse();
        assertThat(args.list).as("--list").isFalse();
        assertThat(args.get).as("--get").isEqualTo("test.key");
    }
}