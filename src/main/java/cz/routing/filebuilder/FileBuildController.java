package cz.routing.filebuilder;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.preprocessing.OverlayPreprocessor;
import lombok.Value;

import java.io.IOException;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class FileBuildController {
    private EventBus eventBus;

    public FileBuildController( EventBus eventBus ) {
        this.eventBus = eventBus;
    }

    public void run( SaraGraphLoader loader, OverlayPreprocessor overlayPreprocessor, OverlayGraphSaver saver ) throws IOException {
        eventBus.register( new SaraLoadEventListener( eventBus, overlayPreprocessor ) );
        eventBus.register( new OverlayEventListener( eventBus, saver ) );
        eventBus.register( new SaveEventLitener( eventBus ) );
        SaraGraph graph = loader.loadSara();
        eventBus.post( graph );
    }

    @Value
    private static class SaraLoadEventListener {
        EventBus eventBus;
        OverlayPreprocessor overlayPreprocessor;

        @Subscribe
        public void onSaraLoaded( SaraGraph saraGraph ) {
            OverlayBuilder overlayBuilder = overlayPreprocessor.buildOverlay( saraGraph );
            eventBus.post( overlayBuilder );
        }
    }

    @Value
    private static class OverlayEventListener {
        EventBus eventBus;
        OverlayGraphSaver overlayGraphSaver;

        @Subscribe
        public void onOverlayBuilt( OverlayBuilder overlayBuilder ) {
            overlayGraphSaver.saveOverlay( overlayBuilder );
            eventBus.post( new SaveEvent() );
        }
    }

    private static class SaveEvent {

    }

    @Value
    private static class SaveEventLitener {
        EventBus eventBus;

        @Subscribe
        public void onSave( SaveEvent saveEvent ) {
            System.out.println( "Finished!" );
        }
    }
}
