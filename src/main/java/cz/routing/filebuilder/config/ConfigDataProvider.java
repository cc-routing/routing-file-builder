package cz.routing.filebuilder.config;

import cz.blahami2.utils.configuration.Configuration;
import cz.certicon.routing.algorithm.sara.preprocessing.PreprocessingInput;
import cz.certicon.routing.model.basic.IdSupplier;

import java.util.Properties;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class ConfigDataProvider {

    private final Configuration configuration;

    public ConfigDataProvider( Configuration configuration ) {
        this.configuration = configuration;
    }

    public Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.setProperty( "driver", getParameter( "routing.db.driver" ) );
        properties.setProperty( "url", getParameter( "routing.db.url" ) );
        properties.setProperty( "spatialite_path", getParameter( "routing.db.spatialite_path" ) );
        return properties;
    }

    public PreprocessingInput getPreprocessingInput() {
        return new PreprocessingInput(
                getParameter( "routing.preprocessing.cell_size", Integer.class ),
                getParameter( "routing.preprocessing.cell_ratio", Double.class ),
                getParameter( "routing.preprocessing.core_ratio", Double.class ),
                getParameter( "routing.preprocessing.low_interval_probability", Double.class ),
                getParameter( "routing.preprocessing.low_interval_limit", Double.class ),
                getParameter( "routing.preprocessing.number_of_assembly_runs", Integer.class ),
                getParameter( "routing.preprocessing.number_of_layers", Integer.class )
        );
    }

    public IdSupplier getIdSupplier() {
        return new IdSupplier(
                getParameter( "routing.preprocessing.current_max_id", Integer.class )
        );
    }


    private String getParameter( String key ) {
        return configuration.getValue( key ).orElseThrow( () -> new IllegalStateException( "Unknown parameter: " + key ) );
    }

    private <T> T getParameter( String key, Class<T> clazz ) {
        return configuration.getValue( key, clazz ).orElseThrow( () -> new IllegalStateException( "Unknown parameter: " + key ) );
    }
}
