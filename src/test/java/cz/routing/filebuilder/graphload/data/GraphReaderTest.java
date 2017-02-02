package cz.routing.filebuilder.graphload.data;

import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.data.DataTestUtils;
import cz.routing.filebuilder.model.NodeData;
import cz.routing.filebuilder.model.TurnTableData;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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

    @Test
    public void readTurnTablesReturnsCorrectResults() throws Exception {
        GraphReader graphReader = new GraphReader( properties );
        Map<Integer, TurnTableData> result = graphReader.readTurnTables();
        Map<Integer, TurnTableData> expected = new HashMap<Integer, TurnTableData>() {{
            put( 1, new TurnTableData( 1, new double[][]{ { Double.MAX_VALUE, 0, 0 }, { 0, Double.MAX_VALUE, 0 }, { 0, 0, Double.MAX_VALUE } } ) );
            put( 2, new TurnTableData( 2, new double[][]{ { Double.MAX_VALUE } } ) );
            put( 3, new TurnTableData( 3, new double[][]{ { Double.MAX_VALUE, 0, 0, 0 }, { 0, Double.MAX_VALUE, 0, 0 }, { 0, 0, Double.MAX_VALUE, 0 }, { 0, 0, 0, Double.MAX_VALUE } } ) );
            put( 4, new TurnTableData( 4, new double[][]{ { Double.MAX_VALUE, 0 }, { 0, Double.MAX_VALUE } } ) );
            put( 5, new TurnTableData( 5, new double[][]{ { Double.MAX_VALUE, 0 }, { Double.MAX_VALUE, Double.MAX_VALUE } } ) );
            put( 6, new TurnTableData( 6, new double[][]{ { Double.MAX_VALUE, Double.MAX_VALUE }, { 0, Double.MAX_VALUE } } ) );
        }};
        assertThat( result, equalTo( expected ) );
    }

    @Test
    public void readNodeDataReturnsCorrectResults() throws Exception {
        GraphReader graphReader = new GraphReader( saraProperties );
        Map<Integer, NodeData> result = graphReader.readNodes( 1l );
        Map<Integer, NodeData> expected = new HashMap<Integer, NodeData>() {{
            put( 6, new NodeData( 6, 1 ) );
            put( 37, new NodeData( 37, 1 ) );
            put( 54, new NodeData( 54, 2 ) );
            put( 72, new NodeData( 72, 1 ) );
            put( 73, new NodeData( 73, 4 ) );
            put( 108, new NodeData( 108, 4 ) );
            put( 111, new NodeData( 111, 2 ) );
        }};
        assertThat( result, equalTo( expected ) );
    }
}