package cz.routing.filebuilder.model;

import lombok.Value;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
@Value
public class TurnTableData {
    int turnTableId;
    double[][] matrix;
}
