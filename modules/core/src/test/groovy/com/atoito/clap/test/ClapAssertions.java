package com.atoito.clap.test;

import org.assertj.core.api.Assertions;

import com.atoito.clap.api.CommandResult;

/**
 * Clap Fest assertions module: extending make all standard Fest assertions available.
 *
 */
public class ClapAssertions extends Assertions { 

	public static CommandResultAssert assertThat(CommandResult actual) {
		return new CommandResultAssert(actual);
	}
}