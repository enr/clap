package com.atoito.clap.api;

/**
 * Interface for classes used to log messages and send output to the user.
 * Messages intended to be logged are written based on a level such as logging libraries.
 * Messages to the user are always sent to system output (standard or error).
 */
public interface Reporter {

    public enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    /**
     * Set the minimum level for messages
     */
    void setLevel(Reporter.Level level);

    void debug(String message);

    void debug(String template, Object... args);

    void info(String message);

    void info(String template, Object... args);

    void warn(String message);

    void warn(String template, Object... args);

    /**
     * Report without level, intended to be used to write to the standard out
     */
    void out(String message);

    /**
     * Report without level, intended to be used to write to the standard out
     */
    void out(String template, Object... args);

    /**
     * Report without level, intended to be used to write to the standard err
     */
    void err(String message);

    /**
     * Report without level, intended to be used to write to the standard err
     */
    void err(String template, Object... args);
}
