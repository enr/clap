package com.github.enr.clap.test;

import org.fest.assertions.api.AbstractAssert;

import com.github.enr.clap.api.CommandResult;

public class CommandResultAssert extends AbstractAssert<CommandResultAssert, CommandResult> {

	public CommandResultAssert(CommandResult actual) {
		super(actual, CommandResultAssert.class);
	}

	public static CommandResultAssert assertThat(CommandResult actual) {
		return new CommandResultAssert(actual);
	}

	public CommandResultAssert isSuccessful() {
		isNotNull();
		if (actual.isFailure()) {
			throw new AssertionError("Expected result to be successful");
		}
		return this;
	}

	public CommandResultAssert isFailure() {
		isNotNull();
		if (!actual.isFailure()) {
			throw new AssertionError("Expected result to be failure");
		}
		return this;
	}

	public CommandResultAssert hasFailureMessage(String message) {
		isNotNull();
		String errorMessage = String.format("Expected failure message to be <%s> but was <%s>",
				 							message, actual.getFailureMessage());
		if (!actual.getFailureMessage().equals(message)) {
			throw new AssertionError(errorMessage);
		}
		return this;
	}

	public CommandResultAssert hasNoFailureMessage() {
		isNotNull();
		if (!(actual.getFailureMessage() == null)) {
			throw new AssertionError("Expected failure message to be null");
		}
		return this;
	}

	public CommandResultAssert hasExitValue(int code) {
		isNotNull();
		String errorMessage = String.format("Expected exit value to be <%d> but was <%d>",
				 							code, actual.getExitValue());
		if (!(actual.getExitValue() == code)) {
			throw new AssertionError(errorMessage);
		}
		return this;

	}
}
