package com.github.enr.clap.test;

import static org.fest.assertions.api.Assertions.assertThat;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.enr.clap.api.CommandResult;

@Test(suiteName = "Api package")
public class CommandResultTest {

    @BeforeClass
    public void initData() {
    }

    @Test(description="success() factory method should create a successful result")
    public void successFactory() {
    	CommandResult result = CommandResult.successful();
    	assertThat(result.isFailure()).as("result.isFailure").isFalse();
    	assertThat(result.getExitValue()).as("result.getExitValue").isEqualTo(0);
    	assertThat(result.getFailureMessage()).as("result.getFailureMessage").isNull();
    }

    @Test(description="failure() factory method should create a failing result")
    public void failureFactory() {
    	CommandResult result = CommandResult.failure();
    	assertThat(result.isFailure()).as("result.isFailure").isTrue();
    	assertThat(result.getExitValue()).as("result.getExitValue").isEqualTo(1);
    	assertThat(result.getFailureMessage()).as("result.getFailureMessage").isNull();
    }
}
