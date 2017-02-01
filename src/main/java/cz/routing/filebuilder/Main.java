package cz.routing.filebuilder;

import cz.blahami2.utils.configuration.Configuration;
import cz.blahami2.utils.configuration.ConfigurationBuilder;
import cz.blahami2.utils.configuration.data.ConfigurationReader;
import cz.blahami2.utils.configuration.data.PropertiesReader;
import cz.blahami2.utils.configuration.data.readers.PropertiesConfigurationReader;
import cz.blahami2.utils.configuration.utils.ArgumentParser;
import cz.certicon.routing.algorithm.sara.preprocessing.BottomUpPreprocessor;
import cz.certicon.routing.algorithm.sara.preprocessing.PreprocessingInput;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilderSetup;
import cz.certicon.routing.model.basic.IdSupplier;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.config.ConfigDataProvider;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.graphload.SaraPreprocessor;
import cz.routing.filebuilder.graphload.data.GraphReader;
import cz.routing.filebuilder.preprocessing.OverlayPreprocessor;
import cz.routing.filebuilder.utils.Environment;

import java.io.IOException;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class Main {

    private static final String CONFIG_ARG_NAME = "routing.config.path";
    private static final String CONFIG_DEFAULT_PATH = "default_config.properties";
    private static final String RESOURCES_PATH = "src/main/resources";

    public static void main( String[] args ) throws IOException {
        new Main().run( args );
    }

    private ConfigDataProvider config;

    public void run( String[] args ) throws IOException {
        this.config = new ConfigDataProvider( loadConfiguration( args ) );
        SaraGraphLoader saraGraphLoader = buildSaraGraphLoader();
        OverlayPreprocessor overlayPreprocessor = buildOverlayPreprocessor();
        OverlayGraphSaver overlayGraphSaver = buildOverlayGraphSaver();
        FileBuildController fileBuildController = new FileBuildController();
//        fileBuildController.run( saraGraphLoader, overlayPreprocessor, overlayGraphSaver );
    }

    private Configuration loadConfiguration( String[] args ) {
        ConfigurationBuilder builder = ConfigurationBuilder.newInstance()
                .setArguments( args )
                .setUseEnvironmentVariables( true )
                .setConfigPathArgumentName( CONFIG_ARG_NAME )
                .setDefaultProperties( Environment.getResourceFolderAbsolutePath( RESOURCES_PATH ) + "/" + CONFIG_DEFAULT_PATH );
        return builder.build();
    }

    private OverlayGraphSaver buildOverlayGraphSaver() {
        return new OverlayGraphSaver();
    }

    private OverlayPreprocessor buildOverlayPreprocessor() {
        OverlayBuilderSetup overlayBuilderSetup = new OverlayBuilderSetup();
        overlayBuilderSetup.setKeepSortcuts( false );
        BiFunction<SaraGraph, OverlayBuilderSetup, OverlayBuilder> overlayBuilderFactory =
                ( sg, obs ) -> new OverlayBuilder( sg, obs );
        return new OverlayPreprocessor( overlayBuilderSetup, overlayBuilderFactory );
    }

    private SaraGraphLoader buildSaraGraphLoader() {
        Properties connectionProperties = config.getDatabaseProperties();
        GraphReader graphReader = new GraphReader();
        SaraPreprocessor saraPreprocessor = buildSaraPreprocessor();
        return new SaraGraphLoader( connectionProperties, graphReader, saraPreprocessor );
    }

    private SaraPreprocessor buildSaraPreprocessor() {
        PreprocessingInput preprocessingInput = config.getPreprocessingInput();
        IdSupplier idSupplier = config.getIdSupplier();
        return new SaraPreprocessor( new BottomUpPreprocessor(), preprocessingInput, idSupplier );
    }
}
