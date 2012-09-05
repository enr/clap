package com.github.enr.clap.impl;

import com.github.enr.clap.api.Reporter;

/*
 * Basic reporter implementation, writing to stdout and stderr.
 */
public class ConsoleReporter implements Reporter {

    Level level = Level.WARN;

    @Override
    public void debug(String message) {
        if (isDebugEnabled()) {
            print(message);
        }
    }

    @Override
    public void debug(String template, Object... args) {
        if (isDebugEnabled()) {
            System.out.println(format(template, args));
        }
    }

    @Override
    public void info(String message) {
        if (isInfoEnabled()) {
            print(message);
        }
    }

    @Override
    public void info(String template, Object... args) {
        if (isInfoEnabled()) {
            System.out.println(format(template, args));
        }
    }

    @Override
    public void warn(String message) {
        print(message);
    }

    @Override
    public void warn(String template, Object... args) {
        System.out.println(format(template, args));
    }

    @Override
    public void out(String message) {
        print(message);
    }

    @Override
    public void out(String template, Object... args) {
        System.out.println(format(template, args));
    }

    @Override
    public void err(String message) {
        System.err.println(String.format(safe(message)));
    }

    @Override
    public void err(String template, Object... args) {
        System.err.println(format(template, args));
    }

    private static String format(String template, Object... args) {
        return String.format(template, args);
    }

    private boolean isDebugEnabled() {
        return (level.equals(Level.DEBUG));
    }

    private boolean isInfoEnabled() {
        return ((level.equals(Level.DEBUG)) || (level.equals(Level.INFO)));
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    private void print(String message) {
        System.out.println(String.format(safe(message)));
    }

    private String safe(String text) {
        return ((text == null) ? "" : text);
    }
}
