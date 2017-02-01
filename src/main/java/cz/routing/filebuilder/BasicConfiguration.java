package cz.routing.filebuilder;

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

import java.util.Properties;
import java.util.function.BiFunction;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class BasicConfiguration implements Configuration {


    @Override
    public OverlayGraphSaver buildOverlayGraphSaver() {
        return new OverlayGraphSaver();
    }

    @Override
    public OverlayPreprocessor buildOverlayPreprocessor() {
        OverlayBuilderSetup overlayBuilderSetup = new OverlayBuilderSetup();
        overlayBuilderSetup.setKeepSortcuts( false );
        BiFunction<SaraGraph, OverlayBuilderSetup, OverlayBuilder> overlayBuilderFactory =
                ( sg, obs ) -> new OverlayBuilder( sg, obs );
        return new OverlayPreprocessor( overlayBuilderSetup, overlayBuilderFactory );
    }

    @Override
    public SaraGraphLoader buildSaraGraphLoader( ConfigDataProvider configDataProvider ) {
        Properties connectionProperties = configDataProvider.getDatabaseProperties();
        GraphReader graphReader = new GraphReader();
        SaraPreprocessor saraPreprocessor = buildSaraPreprocessor( configDataProvider );
        return new SaraGraphLoader( connectionProperties, graphReader, saraPreprocessor );
    }

    private SaraPreprocessor buildSaraPreprocessor( ConfigDataProvider configDataProvider ) {
        PreprocessingInput preprocessingInput = configDataProvider.getPreprocessingInput();
        IdSupplier idSupplier = configDataProvider.getIdSupplier();
        return new SaraPreprocessor( new BottomUpPreprocessor(), preprocessingInput, idSupplier );
    }
}
