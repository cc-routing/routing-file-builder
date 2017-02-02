package cz.routing.filebuilder.graphload.data;

import cz.certicon.routing.data.GraphDAO;
import cz.certicon.routing.data.SqliteGraphDAO;
import cz.certicon.routing.data.basic.database.SimpleDatabase;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class GraphReader {

    private final Properties connectionProperties;

    public GraphReader( Properties connectionProperties ) {
        assert connectionProperties.containsKey( "driver" );
        assert connectionProperties.containsKey( "url" );
        assert connectionProperties.containsKey( "spatialite_path" );
        this.connectionProperties = connectionProperties;
    }

    public boolean isPreprocessed() throws IOException {
        try {
            SimpleDatabase database = SimpleDatabase.newSqliteDatabase( connectionProperties );
            ResultSet resultSet = database.read( "SELECT name FROM sqlite_master WHERE type='table' AND name='cells';" );
            return resultSet.next();
        } catch ( SQLException e ) {
            throw new IOException( e );
        }
    }

    public SaraGraph readSaraGraph() throws IOException {
        GraphDAO graphDAO = new SqliteGraphDAO( connectionProperties );
        return graphDAO.loadSaraGraph();
    }

    public Graph readGraph() throws IOException {
        GraphDAO graphDAO = new SqliteGraphDAO( connectionProperties );
        return graphDAO.loadGraph();
    }
}
