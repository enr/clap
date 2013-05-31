package com.github.enr.clap.test;

import org.fest.assertions.api.Assertions;

import com.github.enr.clap.api.CommandResult;

/**
 * Clap Fest assertions module: extending make all standard Fest assertions available.
 *
 */
public class ClapAssertions extends Assertions { 

	public static CommandResultAssert assertThat(CommandResult actual) {
		return new CommandResultAssert(actual);
	}
}