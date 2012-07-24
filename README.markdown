Clap
====

Java library to easily create command-line apps.

Based on Guice and JCommander.


Usage
-----

Declare dependency:


    compile 'com.github.enr:clap-core:0.1'


Create your commands, implementing com.github.enr.clap.api.Command, and command parameters (class annotated with com.beust.jcommander.Parameters):


```java
    @Parameters(commandDescription = "List available and configured datasets")
    public class ListCommandArgs {

    }
```

Create an app metadata class (implementing com.github.enr.clap.api.AppMeta):


```java
    public class MyAppMeta implements AppMeta {

        @Override
        public String name() {
            return "appname";
        }

        @Override
        public String version() {
            return "0.1-SNAPSHOT";
        }
        
        @Override
        public String displayName() {
            return "App Name";
        }
    }
```


Create your Guice module, adding your commands and your metadata:


```java
    bind( AppMeta.class ).to( MyAppMeta.class );
    bind( Command.class ).annotatedWith(Names.named("command.import")).to( ImportCommand.class );
    bind( Command.class ).annotatedWith(Names.named("command.list")).to( ListCommand.class );
```


Create a main class:


```java
    import com.github.enr.clap.api.ClapApp;
    import com.github.enr.clap.inject.Bindings;
    import com.github.enr.clap.inject.ClapModule;
    import com.google.inject.Guice;
    import com.google.inject.Injector;
    import com.google.inject.util.Modules;

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



