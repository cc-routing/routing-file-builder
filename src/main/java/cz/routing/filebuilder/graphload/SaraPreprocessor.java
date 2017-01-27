package cz.routing.filebuilder.graphload;

import cz.certicon.routing.algorithm.sara.preprocessing.PreprocessingInput;
import cz.certicon.routing.algorithm.sara.preprocessing.Preprocessor;
import cz.certicon.routing.model.basic.IdSupplier;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
@AllArgsConstructor
@Setter
@Getter
public class SaraPreprocessor {

    @NonNull
    private Preprocessor preprocessor;
    @NonNull
    private PreprocessingInput preprocessingInput;
    @NonNull
    private IdSupplier idSupplier;

    public SaraGraph preprocess( Graph graph ) {
        return preprocessor.preprocess( graph, preprocessingInput, idSupplier );
    }
}
