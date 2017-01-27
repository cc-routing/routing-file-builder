package cz.routing.filebuilder;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayGraph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.filewrite.OverlayGraphSaver;
import cz.routing.filebuilder.graphload.SaraGraphLoader;
import cz.routing.filebuilder.preprocessing.Preprocessor;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class FileBuildControllerTest {
    private FileBuildController controller;

    @Before
    public void setUp() throws Exception {
        controller = new FileBuildController();
    }

    @Test
    public void run() throws Exception {
        SaraGraph saraGraph = mock( SaraGraph.class );
        SaraGraphLoader loader = mock( SaraGraphLoader.class );
        when( loader.loadSara() ).thenReturn( saraGraph );
        OverlayGraph overlayGraph = mock( OverlayGraph.class );
        Preprocessor preprocessor = mock( Preprocessor.class );
        when( preprocessor.buildOverlay( saraGraph ) ).thenReturn( overlayGraph );
        OverlayGraphSaver saver = mock( OverlayGraphSaver.class );
        controller.run( loader, preprocessor, saver );
        verify( saver, times( 1 ) ).saveOverlay( overlayGraph );
    }

}