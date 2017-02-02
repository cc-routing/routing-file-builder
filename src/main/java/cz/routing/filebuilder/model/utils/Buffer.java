package cz.routing.filebuilder.model.utils;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class Buffer {
    private final byte[] bf;

    public Buffer( int size ) {
        this.bf = new byte[size];
    }

    public int putByte( int position, byte value ) {
        return position + Type.BYTE.size;
    }

    public int putShort( int position, short value ) {
        return position + Type.SHORT.size;
    }

    public int putInt( int position, int value ) {
        return position + Type.INT.size;
    }

    public int moveByN( int position, int count, Type type ) {
        return position + count * type.size;
    }

    public enum Type {
        BYTE( 1 ), SHORT( 2 ), INT( 4 ), LONG( 8 );

        final int size;

        Type( int size ) {
            this.size = size;
        }
    }
}
