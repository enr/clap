package clap.uat.app.hello;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Command;
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
        bind( AppMeta.class ).to( HelloMeta.class );
        //bind( EnvironmentHolder.class ).to( NoExitEnvironmentHolder.class ).in( Singleton.class );
        
        // components
        //bind( Reporter.class ).to( DefaultOutputRetainingReporter.class ).in( Singleton.class );
        
        // commands
        bind( Command.class ).annotatedWith(Names.named("command.echo")).to( EchoCommand.class );
    }
}