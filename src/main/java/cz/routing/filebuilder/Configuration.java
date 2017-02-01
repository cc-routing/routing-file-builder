package cz.routing.filebuilder;

import cz.routing.filebuilder.config.ConfigDataProvider;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.preprocessing.OverlayPreprocessor;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public interface Configuration {

    OverlayGraphSaver buildOverlayGraphSaver();

    OverlayPreprocessor buildOverlayPreprocessor();

    SaraGraphLoader buildSaraGraphLoader( ConfigDataProvider configDataProvider );
}
