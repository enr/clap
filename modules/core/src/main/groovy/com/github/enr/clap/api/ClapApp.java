package com.github.enr.clap.api;

import java.util.List;

/*
 * the actual Clap application
 */
public interface ClapApp {

    void setAvailableCommands(List<Command> commands);

    void run(String[] args);

    /*
     * overrides default binding using app module
     */
    // void overrideBindings(Module module);
}
