package cz.routing.filebuilder.filewrite.data;

import cz.certicon.routing.algorithm.sara.preprocessing.overlay.OverlayBuilder;
import cz.certicon.routing.data.basic.DataDestination;
import cz.routing.filebuilder.graphload.data.GraphReader;

import java.io.*;
import java.nio.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class BinaryGraphWriter {
    private final FastBinaryWriter writer;
    private final ByteBuffer buffer;
    private final GraphReader graphReader;

    public BinaryGraphWriter( DataDestination destination, GraphReader graphReader ) throws IOException {
        this.writer = new FastBinaryWriter( destination.getOutputStream() );
        this.graphReader = graphReader;
        this.buffer = ByteBuffer.wrap( new byte[1 << 28] );
    }

    public void writeOverlayGraph( OverlayBuilder overlayBuilder ) {
        // write tt index
        int position = 4;
        int layerLen = writeLayers( position, overlayBuilder );
        position += layerLen;
        buffer.putInt( 0, position );
        int ttLen = writeTurnTables( position, overlayBuilder );
    }

    private int writeTurnTables( int position, OverlayBuilder overlayBuilder ) {
        // write count 2byte
        buffer.putShort( position,
                (short) StreamSupport.stream( overlayBuilder.getSaraGraph().getNodes().spliterator(), false )
                        .map( node -> node.getTurnTable() )
                        .distinct()
                        .count()
        );
        // write map - # of turntables * 4byte
        // write tts
        return 0;
    }

    private int writeLayers( int position, OverlayBuilder overlayBuilder ) {

        return 0;
    }

}
