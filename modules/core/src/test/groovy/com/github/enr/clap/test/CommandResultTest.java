package com.github.enr.clap.test;

import static com.github.enr.clap.test.ClapAssertions.assertThat;
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
    	assertThat(result).isSuccessful();
    	assertThat(result).hasExitValue(0);
    	assertThat(result).hasNoFailureMessage();
    }

    @Test(description="failure() factory method should create a failing result")
    public void failureFactory() {
    	CommandResult result = CommandResult.failure();
    	assertThat(result).isFailure();
    	assertThat(result).hasExitValue(1);
    	assertThat(result).hasNoFailureMessage();
    }
}
