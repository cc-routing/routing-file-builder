package cz.routing.filebuilder.filewrite.data;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class FastBinaryWriter {
    private OutputStream os;
    private ByteBuffer byteBuffer;

    public FastBinaryWriter( OutputStream os, int bufferSize ) {
        this.os = os;
        this.byteBuffer = ByteBuffer.wrap( new byte[bufferSize] );
        byteBuffer.mark();
    }

    public FastBinaryWriter( OutputStream os ) {
        this.os = os;
        this.byteBuffer = ByteBuffer.wrap( new byte[1 << 16] );
        byteBuffer.mark();
    }

    public void close() throws IOException {
        flush();
        os.close();
    }

    public void write( byte value ) {
        if ( byteBuffer.remaining() < 1 ) {
            flush();
        }
        byteBuffer.put( value );
    }

    public void write( short value ) {
        if ( byteBuffer.remaining() < 2 ) {
            flush();
        }
        byteBuffer.putShort( value );
    }

    public void write( int value ) {
        if ( byteBuffer.remaining() < 4 ) {
            flush();
        }
        byteBuffer.putInt( value );
    }


    public void write( long value ) {
        if ( byteBuffer.remaining() < 4 ) {
            flush();
        }
        byteBuffer.putLong( value );
    }

    public void write( float value ) {
        if ( byteBuffer.remaining() < 4 ) {
            flush();
        }
        byteBuffer.putFloat( value );
    }


    public void write( double value ) {
        if ( byteBuffer.remaining() < 4 ) {
            flush();
        }
        byteBuffer.putDouble( value );
    }

    public void flush() {
        try {
            os.write( byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position() );
            os.flush();
            byteBuffer.reset();
        } catch ( IOException e ) {
            throw new RuntimeException( "Error flushing array.", e );
        }
    }
}
