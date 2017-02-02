package cz.routing.filebuilder.graphload.data;

import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.data.DataTestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * See brand_data for 'magic numbers'
 *
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class GraphReaderTest {

    private Properties properties;
    private Properties saraProperties;
    private Properties data;

    @Before
    public void setUp() throws Exception {
        String resFolderPath = DataTestUtils.getResourceFolderAbsolutePath();
        properties = DataTestUtils.getSpatialiteProperties( resFolderPath, "routing_brand.sqlite" );
        saraProperties = DataTestUtils.getSpatialiteProperties( resFolderPath, "routing_sara_brand.sqlite" );
        data = new Properties();
        data.load( new FileInputStream( new File( resFolderPath + "\\sqlite\\brand_data.properties" ) ) );
    }


    @Test
    public void readGraphReturnsSimpleGraph() throws Exception {
        GraphReader graphReader = new GraphReader( properties );
        Graph graph = graphReader.readGraph();
        assertThat( graph.getNodesCount(), equalTo( Integer.parseInt( data.getProperty( "nodes_count" ) ) ) );
        assertThat( graph.getEdgeCount(), equalTo( Integer.parseInt( data.getProperty( "edges_count" ) ) ) );
    }

    @Test
    public void readSaraGraphReturnsSaraGraph() throws Exception {
        GraphReader graphReader = new GraphReader( saraProperties );
        SaraGraph graph = graphReader.readSaraGraph();
        assertThat( graph.getNodesCount(), equalTo( Integer.parseInt( data.getProperty( "nodes_count" ) ) ) );
        assertThat( graph.getEdgeCount(), equalTo( Integer.parseInt( data.getProperty( "edges_count" ) ) ) );
    }

    @Test
    public void isPreprocessedReturnsFalseForPlainGraph() throws Exception {
        GraphReader graphReader = new GraphReader( properties );
        boolean isPreprocessed = graphReader.isPreprocessed();
        assertThat( isPreprocessed, equalTo( false ) );
    }

    @Test
    public void isPreprocessedReturnsTrueForSaraGraph() throws Exception {
        GraphReader graphReader = new GraphReader( saraProperties );
        boolean isPreprocessed = graphReader.isPreprocessed();
        assertThat( isPreprocessed, equalTo( true ) );
    }
}