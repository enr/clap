package com.atoito.clap.inject;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.ClapApp;
import com.atoito.clap.api.Command;
import com.atoito.clap.api.ComponentsRegistry;
import com.atoito.clap.api.Configuration;
import com.atoito.clap.api.ConfigurationReader;
import com.atoito.clap.api.Constants;
import com.atoito.clap.api.EnvironmentHolder;
import com.atoito.clap.api.Reporter;
import com.atoito.clap.impl.ConfigurationCommand;
import com.atoito.clap.impl.DefaultAppMeta;
import com.atoito.clap.impl.DefaultClapApp;
import com.atoito.clap.impl.DefaultConfiguration;
import com.atoito.clap.impl.DefaultOutputRetainingReporter;
import com.atoito.clap.impl.GroovierFlattenConfigurationReader;
import com.atoito.clap.impl.MainCommand;
import com.atoito.clap.impl.NoExitEnvironmentHolder;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/*
 * Main default module for Clap.
 * Used in the actual launch of application.
 * It contains bindings for all components used in Clap.
 * You can use them or override them, and of course add your own components.
 */
public class ReviewableAppModule extends AbstractModule {

    @Override
    protected void configure() {

        // registry
        requestStaticInjection(ComponentsRegistry.class);
        
        // app
        bind(ClapApp.class).to(DefaultClapApp.class);

        // configuration
        bind(AppMeta.class).to(DefaultAppMeta.class);
        bind(EnvironmentHolder.class).to(NoExitEnvironmentHolder.class).in(Singleton.class);
        bind(Configuration.class).to(DefaultConfiguration.class).in(Singleton.class);
        bind(ConfigurationReader.class).to(GroovierFlattenConfigurationReader.class);

        // components
        bind(Reporter.class).to(DefaultOutputRetainingReporter.class).in(Singleton.class);
        
        // commands
        bind(Command.class).annotatedWith(Names.named(Constants.MAIN_COMMAND_BIND_NAME)).to(MainCommand.class);
        bind(Command.class).annotatedWith(Names.named(Constants.CONFIG_COMMAND_BIND_NAME)).to(ConfigurationCommand.class);
        
    }
}