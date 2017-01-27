package cz.routing.filebuilder.data;

import cz.certicon.routing.data.GraphDAO;
import cz.certicon.routing.data.SqliteGraphDAO;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class DataTestUtils {

    public static String getResourceFolderAbsolutePath() {
        return new File( "src/test/resources" ).getAbsolutePath();
    }

    public static Properties getSpatialiteProperties( String resFolderPath, String fileName ) {
        Properties properties = new Properties();
        properties.setProperty( "driver", "org.sqlite.JDBC" );
        properties.setProperty( "url", "jdbc:sqlite:" + resFolderPath + "\\sqlite\\" + fileName );
        properties.setProperty( "spatialite_path", "mod_spatialite.dll" );
        return properties;
    }

    public static Properties getSpatialiteProperties( String fileName ) {
        return getSpatialiteProperties( getResourceFolderAbsolutePath(), fileName );
    }

    public static Graph loadGraph( String fileName ) throws IOException {
        GraphDAO graphDAO = new SqliteGraphDAO( getSpatialiteProperties( fileName ) );
        return graphDAO.loadGraph();
    }

    public static SaraGraph loadSaraGraph( String fileName ) throws IOException {
        GraphDAO graphDAO = new SqliteGraphDAO( getSpatialiteProperties( fileName ) );
        return graphDAO.loadSaraGraph();
    }
}
