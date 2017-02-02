package cz.routing.filebuilder.graphload.data;

import cz.certicon.routing.data.GraphDAO;
import cz.certicon.routing.data.SqliteGraphDAO;
import cz.certicon.routing.data.basic.database.SimpleDatabase;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.model.TurnTableData;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class GraphReader {

    private final Properties connectionProperties;
    private final SimpleDatabase database;

    public GraphReader( Properties connectionProperties ) {
        assert connectionProperties.containsKey( "driver" );
        assert connectionProperties.containsKey( "url" );
        assert connectionProperties.containsKey( "spatialite_path" );
        this.connectionProperties = connectionProperties;
        this.database = SimpleDatabase.newSqliteDatabase( connectionProperties );
    }

    public boolean isPreprocessed() throws IOException {
        try {
            ResultSet resultSet = database.read( "SELECT name FROM sqlite_master WHERE type='table' AND name='cells';" );
            return resultSet.next();
        } catch ( SQLException e ) {
            throw new IOException( e );
        }
    }

    public Map<Integer, TurnTableData> readTurnTables() throws IOException {
        try {
            Map<Integer, TurnTableData> turnTableDataMap = new HashMap<>();
            ResultSet rs = database.read( "SELECT * FROM turn_tables" );
            while ( rs.next() ) {
                int id = rs.getInt( "id" );
                int size = rs.getInt( "size" );
                turnTableDataMap.put( id, new TurnTableData( id, new double[size][size] ) );
            }
            rs = database.read( "SELECT * FROM turn_table_values" );
            while ( rs.next() ) {
                int id = rs.getInt( "turn_table_id" );
                int rowId = rs.getInt( "row_id" );
                int colId = rs.getInt( "column_id" );
                double value = rs.getDouble( "value" );
                TurnTableData turnTableData = turnTableDataMap.get( id );
                double[][] matrix = turnTableData.getMatrix();
                matrix[rowId][colId] = value;
            }
            return turnTableDataMap;
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
