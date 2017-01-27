package cz.routing.filebuilder.graphload;

import cz.certicon.routing.algorithm.sara.preprocessing.BottomUpPreprocessor;
import cz.certicon.routing.algorithm.sara.preprocessing.PreprocessingInput;
import cz.certicon.routing.algorithm.sara.preprocessing.Preprocessor;
import cz.certicon.routing.model.basic.IdSupplier;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.SaraGraph;
import cz.routing.filebuilder.data.DataTestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class SaraPreprocessorTest {
    private SaraPreprocessor saraPreprocessor;
    private Graph graph;
    private SaraGraph expected;
    private Preprocessor preprocessor;

    @Before
    public void setUp() throws Exception {
        PreprocessingInput input = new PreprocessingInput( 10,1, 0.1, 0.03, 0.6, 1, 2);
        preprocessor = mock(Preprocessor.class);
        IdSupplier idSupplier = mock(IdSupplier.class);
        expected = mock(SaraGraph.class);
        graph = mock(Graph.class);
        when(preprocessor.preprocess( graph, input, idSupplier )).thenReturn( expected );
        saraPreprocessor = new SaraPreprocessor( preprocessor, input, idSupplier );
    }

    @Test
    public void preprocessedSaraGraphHasSameStats() throws Exception {
        SaraGraph saraGraph = saraPreprocessor.preprocess( graph );
        assertThat( saraGraph, equalTo( expected ) );
    }
}