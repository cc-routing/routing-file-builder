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

    private GraphReader graphReader;
    private Properties properties;
    private Properties saraProperties;
    private Properties data;

    @Before
    public void setUp() throws Exception {
        graphReader = new GraphReader();
        String resFolderPath = DataTestUtils.getResourceFolderAbsolutePath();
        properties = DataTestUtils.getSpatialiteProperties( resFolderPath, "routing_brand.sqlite" );
        saraProperties = DataTestUtils.getSpatialiteProperties( resFolderPath, "routing_sara_brand.sqlite" );
        data = new Properties();
        data.load( new FileInputStream( new File( resFolderPath + "\\sqlite\\brand_data.properties" ) ) );
    }


    @Test
    public void readGraphReturnsSimpleGraph() throws Exception {
        Graph graph = graphReader.readGraph( properties );
        assertThat( graph.getNodesCount(), equalTo( Integer.parseInt( data.getProperty( "nodes_count" ) ) ) );
        assertThat( graph.getEdgeCount(), equalTo( Integer.parseInt( data.getProperty( "edges_count" ) ) ) );
    }

    @Test
    public void readSaraGraphReturnsSaraGraph() throws Exception {
        SaraGraph graph = graphReader.readSaraGraph( saraProperties );
        assertThat( graph.getNodesCount(), equalTo( Integer.parseInt( data.getProperty( "nodes_count" ) ) ) );
        assertThat( graph.getEdgeCount(), equalTo( Integer.parseInt( data.getProperty( "edges_count" ) ) ) );
    }

    @Test
    public void isPreprocessedReturnsFalseForPlainGraph() throws Exception {
        boolean isPreprocessed = graphReader.isPreprocessed( properties );
        assertThat( isPreprocessed, equalTo( false ) );
    }

    @Test
    public void isPreprocessedReturnsTrueForSaraGraph() throws Exception {
        boolean isPreprocessed = graphReader.isPreprocessed( saraProperties );
        assertThat( isPreprocessed, equalTo( true ) );
    }
}