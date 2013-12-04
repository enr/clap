package com.atoito.clap.inject;

import java.util.List;

import com.atoito.clap.api.Command;
import com.beust.jcommander.internal.Lists;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

/*
 * Utility methods pertaining Guice bindings. 
 */
public class Bindings {

    private Bindings() {
        // only static mehods
    }

    public static List<Command> getAllCommands(Injector injector) {
        List<Command> commands = Lists.newArrayList();
        List<Binding<Command>> commandBindings = injector.findBindingsByType(TypeLiteral.get(Command.class));
        for (Binding<Command> binding : commandBindings) {
            Command command = binding.getProvider().get();
            commands.add(command);
        }
        return commands;
    }
}
