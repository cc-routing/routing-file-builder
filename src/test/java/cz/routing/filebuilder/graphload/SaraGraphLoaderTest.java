package cz.routing.filebuilder.graphload;

import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.graphload.data.GraphReader;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class SaraGraphLoaderTest {
    private SaraGraphLoader loader;
    private Properties properties;
    private GraphReader graphReader;
    private SaraPreprocessor saraPreprocessor;

    @Before
    public void setUp() throws Exception {
        properties = new Properties();
        properties.setProperty( "driver", "" );
        properties.setProperty( "url", "" );
        properties.setProperty( "spatialite_path", "" );
        graphReader = mock( GraphReader.class );
        saraPreprocessor = mock( SaraPreprocessor.class );
        loader = new SaraGraphLoader( properties, graphReader, saraPreprocessor );
    }

    @Test
    public void loadSaraReturnsSaraForPlainGraph() throws Exception {
        when( graphReader.isPreprocessed( properties ) ).thenReturn( false );
        Graph graph = mock( Graph.class );
        when( graphReader.readGraph( properties ) ).thenReturn( graph );
        SaraGraph expected = mock( SaraGraph.class );
        when( saraPreprocessor.preprocess( graph ) ).thenReturn( expected );
        SaraGraph saraGraph = loader.loadSara();
        assertThat( saraGraph, equalTo( expected ) );
    }

    @Test
    public void laodSaraReturnsSaraForSara() throws Exception {
        when( graphReader.isPreprocessed( properties ) ).thenReturn( true );
        SaraGraph expected = mock( SaraGraph.class );
        when( graphReader.readSaraGraph( properties ) ).thenReturn( expected );
        SaraGraph saraGraph = loader.loadSara();
        assertThat( saraGraph, equalTo( expected ) );

    }
}