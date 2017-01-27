package cz.routing.filebuilder;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayGraph;
import cz.certicon.routing.data.basic.DataDestination;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.preprocessing.Preprocessor;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class FileBuildController {
    public void run( SaraGraphLoader loader, Preprocessor preprocessor, OverlayGraphSaver saver ) {
        SaraGraph graph = loader.loadSara();
        OverlayGraph overlayGraph = preprocessor.buildOverlay(graph);
        saver.saveOverlay(overlayGraph);
    }
}
