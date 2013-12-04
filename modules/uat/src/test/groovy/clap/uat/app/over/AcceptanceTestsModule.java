package clap.uat.app.over;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Command;
import com.atoito.clap.api.Constants;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/*
 * Main module for a fake app used in acceptance tests.
 */
public class AcceptanceTestsModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
        // configuration
        bind( AppMeta.class ).to( OverMeta.class );
        //bind( EnvironmentHolder.class ).to( NoExitEnvironmentHolder.class ).in( Singleton.class );
        
        // components
        //bind( Reporter.class ).to( DefaultOutputRetainingReporter.class ).in( Singleton.class );
        
        // commands
        bind(Command.class).annotatedWith(Names.named(Constants.MAIN_COMMAND_BIND_NAME)).to(OverMainCommand.class);
        bind( Command.class ).annotatedWith(Names.named("command.echo")).to( EchoCommand.class );
    }
}