Clap
====

Java library to easily create command-line apps.

Based on [Guice](http://code.google.com/p/google-guice/) and [JCommander](http://jcommander.org/).

[![Build Status](https://secure.travis-ci.org/enr/clap.png?branch=master)](http://travis-ci.org/enr/clap)

[Docs WIP](http://enr.github.io/clap/)

What you get using Clap
-----------------------

- A ready structure for a command line app using options and command (such as `app run --port 9090`).

- Some common fuctionality such as `app --help` and `app --version`.

- Configuration management:
  * built-in configuration object reading groovy files in a system dir (based on os), user home dir and installation dir.
  * configuration command `app config --files` `app config --list` `app config --get <key>`

- A built-in Guice based dependency injection.

- Testability, using Clap components.


Add Clap to your project
------------------------

To use the last released version, add to your build file:

- repository: `http://dl.bintray.com/enrico/maven`

- dependency group: `com.atoito.clap` module: `core` version: `0.5`


Example for a Gradle build:

```groovy
repositories {
    maven { url 'http://dl.bintray.com/enrico/maven' }
}
dependencies {
    compile 'com.atoito.clap:core:0.5'
}
```

For the very last version, clone this repo, then run `./gradlew publishToMavenLocal`.


Usage
-----

Code shown here is largely taken from the actual Clap tests.

At minimum, a Clap app contains:

- metadata describing name and version of the app

- a Guice module registering components

- an entrypoint, ie a class mith a `main` method

**Create metadata**

You can set metadata in two ways.

Creating an app metadata class (implementing `com.atoito.clap.api.AppMeta`):

```java
public class HelloMeta implements AppMeta {
    @Override
    public String name() {
        return "hello";
    }
    @Override
    public String version() {
        return "0.1-SNAPSHOT";
    }
    @Override
    public String displayName() {
        return "Hello";
    }
}
```

If you don't like to hardcode version in a class, you can use `com.atoito.clap.impl.PropertiesBackedAppMeta`.

This way you can create a properties file with the proper meta keys and put it in the classpath.

```groovy
clap.meta.name=YetAnotherClapApp
clap.meta.version=3.4.5
clap.meta.displayname=Yet another Clap application
```

**Create a Guice module**

Create a Guice module, adding your metadata (and other used components):

```java
public class MyAppModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
        // if you like the metadata class
        bind( AppMeta.class ).to( HelloMeta.class );
        // if you like properties:
        bind( AppMeta.class ).toInstance( PropertiesBackedAppMeta.from("your-metadata.properties") );
        // more components...
    }
}
```

The minimal requirement for a Clap app are components implementing:

  * `com.atoito.clap.api.EnvironmentHolder`
  * `com.atoito.clap.api.Reporter`
  * `com.atoito.clap.api.Configuration`
  * `com.atoito.clap.api.ConfigurationReader`

Of course, you can overwrite the default components binding these interfaces to your classes in the app Guice module;
components registered here overwrite the default ones.

**Create the entrypoint**

Create a main class using the utility method:

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Clap.runConventionalApp(args, new MyAppModule())
    }
}
```

or, if you need some customization, something like:

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(Modules
                                                .override(new ClapModule())
                                                .with(new MyAppModule()));
        ClapApp app = injector.getInstance(ClapApp.class);
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(args);
    }
}
```

Now you have a working app, responding to some default command, but not so interesting.

You have to add functionalities, and you can do it thorough commands.

In a Clap based app, the actual logic of your software lives in "commands".

As example, if you want that your app will be callable as

    $>theapp echo -m "the best message ever"

you'll have to:

- create an "echo" command

- create a class acting as container for the command parameters (the `-m` part)

- bind the call to "echo" to the actual class


**Create command**

Commands have to implement `com.atoito.clap.api.Command`:

```java
public class EchoCommand implements Command {

	private static final String COMMAND_ID = "echo";

	private Reporter reporter;

	private EchoCommandArgs args = new EchoCommandArgs();

	@Inject
	public EchoCommand(Reporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public CommandResult execute() {
		reporter.out(args.message);
		return new CommandResult();
	}

	@Override
	public String getId() {
		return COMMAND_ID;
	}

	@Override
	public Object getParametersContainer() {
		return args;
	}
}
```

**Create parameters class**

Command parameters classes are annotated with `com.beust.jcommander.Parameters`:

```java
@Parameters(commandDescription = "Echo messages")
public class EchoCommandArgs {
    @Parameter(names = { "-m", "--message" }, description = "The message to echo")
    public String message;
}
```

**Register command**

Add command to your module.

```java
public class MyAppModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
        // ... other components...
        // register your commands
        bind( Command.class ).annotatedWith(Names.named("command.echo")).to( EchoCommand.class );
    }
}
```


Now, you can run your app, and see something similar to:

    $>pick --help
    Usage: pick [options] [command] [command options]
      Options:
        -d, --debug        Set output level to debug
                           Default: false
        -h, --help         Print help
                           Default: false
        -i, --info         Set output level to info
                           Default: false
        -s, --stacktrace   Show stacktrace if an exception is thrown
                           Default: false
        -v, --version      Print version
                           Default: false
      Commands:
        import      Import dataset
          Usage: import [options] The dataset id to fetch
        list      List available and configured datasets
          Usage: list [options]
        elasticsearch      Start Elasticsearch instance
          Usage: elasticsearch [options]
            Options:
              -p, --port   Port
                           Default: 9206

        config      Informations about configuration
          Usage: config [options]
            Options:
              -f, --files   List all configuration files
                            Default: false
              -g, --get     Get the value for a given key
              -l, --list    List all variables set in config files
                            Default: false

    $>pick --version
    Pick version 0.1-SNAPSHOT
    $>pick config --files
    Configuration files:
    - /path/to/install/pick/conf/pick.groovy (true)
    - /opt/pick/0.1-SNAPSHOT/pick.groovy (false)
    - /home/enr/.pick/0.1-SNAPSHOT/pick.groovy (false)
    $>pick config --list
    elasticsearch.host=http://localhost
    elasticsearch.port=9206
    $>pick config --get elasticsearch.port
    9206



Test your app
-------------

Clap try to help you in developing acceptance tests.

You can look at [user acceptance test module](https://github.com/enr/clap/tree/master/modules/uat) to see the actual tests for Clap (using Cucumber JVM).

By the way, you can write your own test using the framework you prefer.

You can run your app using the utility method:


```java
    RunResult result = Clap.runReviewableApp(args, this.sutHome, new MyAppModule());
    this.sutExitValue = result.getExitValue();
    this.sutOutput = result.getOutput();
```

Or, if you need some customization:

Write a dedicated Guice module, using the same components of your real app, but overriding `Reporter` and `EnvironmentHolder` with other built-in components: `DefaultOutputRetainingReporter` and `NoExitEnvironmentHolder`:

```java
public class AcceptanceTestsModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
        bind( AppMeta.class ).to( HelloMeta.class );
        bind( EnvironmentHolder.class ).to( NoExitEnvironmentHolder.class ).in( Singleton.class );
        bind( Reporter.class ).to( DefaultOutputRetainingReporter.class ).in( Singleton.class );
        bind( Command.class ).annotatedWith(Names.named("command.echo")).to( EchoCommand.class );
    }
}
```

Then, run the app programmatically using something like:

```java
    // [...]

    Injector injector = Guice.createInjector(Modules
                                            .override(new ClapModule())
                                            .with(new AcceptanceTestsModule()));
    EnvironmentHolder environment = injector.getInstance(EnvironmentHolder.class);
    environment.forceApplicationHome(this.sutHome);
    Reporter reporter = injector.getInstance(Reporter.class);
    ClapApp app = injector.getInstance(ClapApp.class);
    app.setAvailableCommands(Bindings.getAllCommands(injector));
    app.run(argsAsString.split("\\s"));
    this.sutExitValue = app.getExitValue();
    if (reporter instanceof OutputRetainingReporter) {
        this.sutOutput = ((OutputRetainingReporter) reporter).getOutput().trim();
    } else {
        this.sutOutput = null;
    }

    // [...]
```

Now you can check the app behaviour, its output (in the snippet `this.sutOutput`) and its exit value (`this.sutExitValue`).


Override default args
---------------------

You can see at [OverApp](https://github.com/enr/clap/tree/master/modules/uat/src/test/groovy/clap/uat/app/over) to see working code overriding defaults.

To replicate, you need to:

Write a Parameter class implementing `CommonArgsAware` and setting the args keys (well, you could hardcode the reporting levels):

```java
@Parameters
public class OverMainCommandArgs implements CommonArgsAware {
    @Parameter(names = { "-w", "--wersion" }, description = "Print version")
    private boolean version;
    @Parameter(names = { "-e", "--elp" }, description = "Print help")
    public boolean help = false;
    @Parameter(names = { "-v", "--verbose" }, description = "Set output level to verbose (debug)")
    public boolean debug = false;
    @Parameter(names = { "-h", "--hinfo" }, description = "Set output level to info")
    public boolean info = false;
    @Parameter(names = { "-z", "--ztacktrace" }, description = "Show stacktrace")
    public boolean stacktrace = false;
    public boolean isVersion() {
        return version;
    }
    @Override
    public boolean isHelp() {
        return help;
    }
    @Override
    public boolean isInfo() {
        return info;
    }
    @Override
    public boolean isDebug() {
        return debug;
    }
    @Override
    public boolean isStacktrace() {
        return stacktrace;
    }
}
```

Write a main command (ie a class implementing `Command` and using your `Parameter` class)

```java
public class OverMainCommand implements Command {
    private Reporter reporter;
    private AppMeta meta;
    private OverMainCommandArgs args = new OverMainCommandArgs();
    @Inject
    public OverMainCommand(AppMeta meta, Reporter reporter) {
        this.meta = meta;
        this.reporter = reporter;
    }
    @Override
    public CommandResult execute() {
        // do things...
    }
    @Override
    public String getId() {
        return Constants.MAIN_COMMAND_ID;
    }
    @Override
    public Object getParametersContainer() {
        return args;
    }
}
```

Bind your command class to `Constants.MAIN_COMMAND_BIND_NAME` in your Guice module:

```java
    bind(Command.class).annotatedWith(Names
                                        .named(Constants.MAIN_COMMAND_BIND_NAME))
                                        .to(OverMainCommand.class);
```


Noop switch
-----------

If your command needs a no-operation switch:

Add to your Parameter class the option (named as you want, in this case `-n | --noop`):

```java
    @Parameter(names = { "-n", "--noop" }, description = "Set noop")
    public boolean noop;
```

Create your command extending `AbstractNoopAwareCommand`

Now, if you command is ran using noop switch, it will outputs the string returned from its method `explain()`


Development
-----------

**Build**

    ./gradlew check

**Installation in local Maven repo**

    ./gradlew publishToMavenLocal

**Release**

Ensure you have no uncommitted files or local changes to push, then:

    ./gradlew release -Pbintray.username=*** -Pbintray.apikey=***

Bintray data are needed because the release task depends on artifacts publishing.

**Publishing**

Ensure version is not a SNAPSHOT, then:

    ./gradlew publish -Pbintray.username=*** -Pbintray.apikey=***

**Code coverage**

To create Jacoco reports:

    ./gradlew coverage

Reports are written in `modules/[core|uat]/build/reports/jacoco`.


Licensing
---------

Copyright (C) 2012 - https://github.com/enr

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.

You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and
limitations under the License.
