package cz.routing.filebuilder.graphload;

import cz.certicon.routing.algorithm.sara.preprocessing.BottomUpPreprocessor;
import cz.certicon.routing.algorithm.sara.preprocessing.Preprocessor;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.graphload.data.GraphReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
@AllArgsConstructor
public class SaraGraphLoader {
    @Setter
    @NonNull
    private GraphReader graphReader;
    @Setter
    @NonNull
    private SaraPreprocessor saraPreprocessor;

    public SaraGraph loadSara() throws IOException {
        SaraGraph saraGraph;
        if ( graphReader.isPreprocessed() ) {
            saraGraph = graphReader.readSaraGraph();
        } else {
            Graph graph = graphReader.readGraph();
            saraGraph = saraPreprocessor.preprocess( graph );
        }
        return saraGraph;
    }
}
