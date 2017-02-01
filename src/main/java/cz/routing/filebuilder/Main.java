package cz.routing.filebuilder;

import com.google.common.eventbus.EventBus;
import cz.blahami2.utils.configuration.ConfigurationBuilder;
import cz.routing.filebuilder.config.ConfigDataProvider;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.preprocessing.OverlayPreprocessor;
import cz.routing.filebuilder.utils.Environment;

import java.io.IOException;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class Main {

    private static final String CONFIG_ARG_NAME = "routing.configDataProvider.path";
    private static final String CONFIG_DEFAULT_PATH = "default_config.properties";
    private static final String RESOURCES_PATH = "src/main/resources";

    public static void main( String[] args ) throws IOException {
        new Main().run( args );
    }

    private ConfigDataProvider configDataProvider;
    private Configuration configuration;

    public void run( String[] args ) throws IOException {
        this.configuration = new BasicConfiguration();
        this.configDataProvider = new ConfigDataProvider( prepareConfigurationBuilder( args ).build() );
        SaraGraphLoader saraGraphLoader = configuration.buildSaraGraphLoader( configDataProvider );
        OverlayPreprocessor overlayPreprocessor = configuration.buildOverlayPreprocessor();
        OverlayGraphSaver overlayGraphSaver = configuration.buildOverlayGraphSaver();
        FileBuildController fileBuildController = new FileBuildController( new EventBus() );
        fileBuildController.run( saraGraphLoader, overlayPreprocessor, overlayGraphSaver );
    }

    private ConfigurationBuilder prepareConfigurationBuilder( String[] args ) {
        ConfigurationBuilder builder = ConfigurationBuilder.newInstance()
                .setArguments( args )
                .setUseEnvironmentVariables( true )
                .setConfigPathArgumentName( CONFIG_ARG_NAME )
                .setDefaultProperties( Environment.getResourceFolderAbsolutePath( RESOURCES_PATH ) + "/" + CONFIG_DEFAULT_PATH );
        return builder;
    }
}
