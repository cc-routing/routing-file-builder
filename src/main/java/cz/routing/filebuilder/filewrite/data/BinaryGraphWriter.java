package cz.routing.filebuilder.filewrite.data;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverLayer;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayCell;
import cz.certicon.routing.data.basic.DataDestination;
import cz.routing.filebuilder.graphload.data.GraphReader;
import cz.routing.filebuilder.model.NodeData;
import cz.routing.filebuilder.model.TurnTableData;
import cz.routing.filebuilder.model.utils.Buffer;

import java.io.*;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class BinaryGraphWriter {
    private final FastBinaryWriter writer;
    private final Buffer buffer;
    private final GraphReader graphReader;

    public BinaryGraphWriter( DataDestination destination, GraphReader graphReader ) throws IOException {
        this.writer = new FastBinaryWriter( destination.getOutputStream() );
        this.graphReader = graphReader;
        this.buffer = new Buffer( 1 << 28 );
    }

    public void writeOverlayGraph( OverlayBuilder overlayBuilder ) throws IOException {
        // write tt index
        int position = buffer.moveByN( 0, 1, Buffer.Type.INT );
        position = writeLayers( position, overlayBuilder );
        buffer.putInt( 0, position );
        position = writeTurnTables( position, overlayBuilder );
    }

    private int writeTurnTables( int position, OverlayBuilder overlayBuilder ) throws IOException {
        Map<Integer, TurnTableData> turnTables = graphReader.readTurnTables();
        // write count 2byte
        position = buffer.putShort( position, (short) turnTables.size() );
        // write map - # of turntables * 4byte
        int mapPosition = position;
        position = buffer.moveByN( position, turnTables.size(), Buffer.Type.INT );
        // write tts
        for ( TurnTableData turnTableData : turnTables.values() ) {
            mapPosition = buffer.putInt( mapPosition, position );
            position = buffer.putShort( position, (short) turnTableData.getMatrix().length );
            double[][] matrix = turnTableData.getMatrix();
            for ( int i = 0; i < matrix.length; i++ ) {
                double[] row = matrix[i];
                for ( int j = 0; j < row.length; j++ ) {
                    position = buffer.putByte( position, row[j] > 0 ? Byte.MAX_VALUE : 0 );
                }
            }
        }
        return position;
    }

    private int writeLayers( int position, OverlayBuilder overlayBuilder ) throws IOException {
        int layerCount = (int) StreamSupport.stream( overlayBuilder.getLayers().spliterator(), false ).count();
        position = buffer.putByte( position, (byte) layerCount );
        int mapPosition = position;
        position = buffer.moveByN( position, layerCount, Buffer.Type.INT );
        boolean first = true;
        for ( OverLayer overLayer : overlayBuilder.getLayers() ) {
            mapPosition = buffer.putInt( mapPosition, position );
            position = first ? writeZeroLayer( position, overlayBuilder, overLayer ) : writeOverLayer( position, overlayBuilder, overLayer );
            first = false;
        }
        return position;
    }

    private int writeZeroLayer( int position, OverlayBuilder overlayBuilder, OverLayer overLayer ) throws IOException {
        int cellCount = overLayer.getCells().size();
        position = buffer.putInt( position, cellCount );
        int mapPosition = position;
        position = buffer.moveByN( position, cellCount, Buffer.Type.INT );
        for ( OverlayCell cell : overLayer.getCells() ) {
            mapPosition = buffer.putInt( mapPosition, position );
            position = writeZeroCell( position, overlayBuilder, cell );
        }
        return position;
    }

    private int writeZeroCell( int position, OverlayBuilder overlayBuilder, OverlayCell cell ) throws IOException {
        // TODO
        // space for byte count
        int startPosition = position;
        position = buffer.moveByN( position, 1, Buffer.Type.INT );
        // nodes
        Map<Integer, NodeData> nodeDataMap = graphReader.readNodes( cell.getId() );
        position = buffer.putShort( position, (short) nodeDataMap.size() );
        for ( NodeData nodeData : nodeDataMap.values() ) {

        }
        int edgeCount = 0;
        position = buffer.putInt( position, edgeCount );
        // write edges TODO
        buffer.putInt( startPosition, position - startPosition );
        return position;
    }

    private int writeOverLayer( int position, OverlayBuilder overlayBuilder, OverLayer overLayer ) {
        // TODO
        return position;
    }


}
