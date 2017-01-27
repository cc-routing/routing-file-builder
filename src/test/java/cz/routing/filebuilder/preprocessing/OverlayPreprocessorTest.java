package cz.routing.filebuilder.preprocessing;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilderSetup;
import cz.certicon.routing.model.graph.SaraGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class OverlayPreprocessorTest {

    private OverlayPreprocessor overlayPreprocessor;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void overlayGraphIsNotNull() throws Exception {
        OverlayBuilderSetup overlayBuilderSetup = mock( OverlayBuilderSetup.class );
        BiFunction<SaraGraph, OverlayBuilderSetup, OverlayBuilder> factory = mock( BiFunction.class );
        SaraGraph saraGraph = mock( SaraGraph.class );
        OverlayBuilder expected = mock( OverlayBuilder.class );
        when( factory.apply( saraGraph, overlayBuilderSetup ) ).thenReturn( expected );
        overlayPreprocessor = new OverlayPreprocessor( overlayBuilderSetup, factory );
        OverlayBuilder overlayBuilder = overlayPreprocessor.buildOverlay( saraGraph );
        assertThat( overlayBuilder, equalTo( expected ) );
    }
}