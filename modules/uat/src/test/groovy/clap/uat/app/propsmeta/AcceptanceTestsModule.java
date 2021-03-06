package clap.uat.app.propsmeta;

import com.atoito.clap.api.AppMeta;
import com.atoito.clap.api.Command;
import com.atoito.clap.impl.PropertiesBackedAppMeta;
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
        bind( AppMeta.class ).toInstance( PropertiesBackedAppMeta.from("clap-uat-propsmeta.properties") );

        // commands
        bind( Command.class ).annotatedWith(Names.named("command.echo")).to( EchoCommand.class );
    }
}