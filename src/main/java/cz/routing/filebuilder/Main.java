package cz.routing.filebuilder;

import cz.certicon.routing.algorithm.sara.preprocessing.BottomUpPreprocessor;
import cz.certicon.routing.algorithm.sara.preprocessing.PreprocessingInput;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilderSetup;
import cz.certicon.routing.model.basic.IdSupplier;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.graphload.SaraPreprocessor;
import cz.routing.filebuilder.graphload.data.GraphReader;
import cz.routing.filebuilder.preprocessing.OverlayPreprocessor;

import java.io.IOException;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class Main {

    public static void main( String[] args ) throws IOException {
        new Main().run( args );
    }

    public void run( String[] args ) throws IOException {
        SaraGraphLoader saraGraphLoader = buildSaraGraphLoader();
        OverlayPreprocessor overlayPreprocessor = buildOverlayPreprocessor();
        OverlayGraphSaver overlayGraphSaver = buildOverlayGraphSaver();
        FileBuildController fileBuildController = new FileBuildController();
        fileBuildController.run( saraGraphLoader, overlayPreprocessor, overlayGraphSaver );
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
        // TODO fill properties
        Properties connectionProperties = new Properties();
        GraphReader graphReader = new GraphReader();
        SaraPreprocessor saraPreprocessor = buildSaraPreprocessor();
        return new SaraGraphLoader( connectionProperties, graphReader, saraPreprocessor );
    }

    private SaraPreprocessor buildSaraPreprocessor() {
        // TODO load settings - input and id
        PreprocessingInput preprocessingInput = new PreprocessingInput( 10, 1, 0.1, 0.03, 0.6, 1, 2 );
        IdSupplier idSupplier = new IdSupplier( 0 );
        return new SaraPreprocessor( new BottomUpPreprocessor(), preprocessingInput, idSupplier );
    }
}
