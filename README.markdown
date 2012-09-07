Clap
====

Java library to easily create command-line apps.

Based on Guice and JCommander.


What you get using Clap
-----------------------

A ready structure for a command line app using options and command (such as `app run --port 9090`).

Some common fuctionality such as `app --help`, `app --version`, `app --configurations` (shows all configuration files loaded).

A built-in configuration object reading groovy files in a system dir (based on os), user home dir and installation dir.

A built-in Guice based dependency injection.


Add Clap to your project
------------------------

At the moment, the best way to add Clap, is clone this repo.

By the way, note for the future...

Declare dependency:


    compile 'com.github.enr:clap-core:0.3-SNAPSHOT'


To automatically download Clap, declare a repository:


```javascript
add(new org.apache.ivy.plugins.resolver.URLResolver()) {
    name = 'GitHub/Clap'
    addArtifactPattern 'http://cloud.github.com/downloads/enr/clap/[module]-[revision].[ext]'
    addIvyPattern 'http://cloud.github.com/downloads/enr/clap/[module]-[revision].pom'
}
```

Usage
-----

Code shown here is largely taken from the actual Clap tests.


Create your commands, implementing `com.github.enr.clap.api.Command`:


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


Create command parameters (class annotated with `com.beust.jcommander.Parameters`):


```java
@Parameters(commandDescription = "Echo messages")
public class EchoCommandArgs {
    @Parameter(names = { "-m", "--message" }, description = "The message to echo")
    public String message;
}
```


Create an app metadata class (implementing `com.github.enr.clap.api.AppMeta`):


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


Create your Guice module, adding your commands and your metadata:


```java
public class MyAppModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
        // configuration
        bind( AppMeta.class ).to( HelloMeta.class );
        bind( EnvironmentHolder.class ).to( NoExitEnvironmentHolder.class ).in( Singleton.class );
        
        // components
        bind( Reporter.class ).to( OutputRetainingReporter.class ).in( Singleton.class );
        
        // commands
        bind( Command.class ).annotatedWith(Names.named("command.echo")).to( EchoCommand.class );
    }
}
```


Create a main class:


```java
public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(Modules.override(new ClapModule()).with(new MyAppModule()));
        ClapApp app = injector.getInstance(ClapApp.class);
        app.setAvailableCommands(Bindings.getAllCommands(injector));
        app.run(args);
    }
}
```


Enjoy.


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



