package cz.routing.filebuilder;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.preprocessing.OverlayPreprocessor;

import java.io.IOException;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class FileBuildController {
    public void run( SaraGraphLoader loader, OverlayPreprocessor overlayPreprocessor, OverlayGraphSaver saver ) throws IOException {
        SaraGraph graph = loader.loadSara();
        OverlayBuilder overlayGraph = overlayPreprocessor.buildOverlay(graph);
        saver.saveOverlay(overlayGraph);
    }
}
