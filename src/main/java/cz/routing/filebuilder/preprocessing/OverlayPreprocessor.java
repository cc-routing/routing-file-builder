package cz.routing.filebuilder.preprocessing;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilderSetup;
import cz.certicon.routing.model.graph.SaraGraph;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.function.BiFunction;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
@AllArgsConstructor
public class OverlayPreprocessor {
    @NonNull
    private OverlayBuilderSetup overlayBuilderSetup;
    @NonNull
    private BiFunction<SaraGraph, OverlayBuilderSetup, OverlayBuilder> overlayBuilderFactory;

    public OverlayBuilder buildOverlay( SaraGraph saraGraph ) {
        OverlayBuilder overlayBuilder = overlayBuilderFactory.apply( saraGraph, overlayBuilderSetup );
        overlayBuilder.buildOverlays();
        return overlayBuilder;
    }
}
