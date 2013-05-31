package com.github.enr.clap.api;

import java.util.List;

/**
 * Interface for the actual Clap application.
 */
public interface ClapApp {

    void setAvailableCommands(List<Command> commands);

    void run(String[] args);
    
    /**
     * Returns the int exit value stored before the actual exiting.
     * ATM useful only if Environment.canExit() returns false.
     */
    int getExitValue();

}
