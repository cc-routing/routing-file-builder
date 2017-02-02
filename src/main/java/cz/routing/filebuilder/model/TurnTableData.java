package cz.routing.filebuilder.model;

import lombok.Value;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
@Value
public class TurnTableData {
    long turnTableId;
    double[][] matrix;
}
