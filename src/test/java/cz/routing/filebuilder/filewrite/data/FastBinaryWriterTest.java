package cz.routing.filebuilder.filewrite.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class FastBinaryWriterTest {

    private FastBinaryWriter writer;
    private File testFile;
    private FileOutputStream fileOutputStream;

    @Before
    public void setUp() throws Exception {
        testFile = new File( "tmp_test_writer.tmp" );
        fileOutputStream = new FileOutputStream( testFile );
        writer = new FastBinaryWriter( fileOutputStream, 1 << 16 );
    }

    @After
    public void tearDown() throws Exception {
        writer.close();
        testFile.delete();
    }

    @Test( timeout = 1000 )
    public void writeOneHundredMillionBytesInUnderOneSecond() throws Exception {
        for ( int i = 0; i < 100000000; i++ ) {
            writer.write( (byte) 0xfe );
        }
    }

//    @Test
//    public void comparison() throws Exception {
//        for ( int i = 10; i < 20; i++ ) {
//            long ms = System.currentTimeMillis();
//            fileOutputStream = new FileOutputStream( testFile );
//            writer = new FastBinaryWriter( fileOutputStream, 1 << i );
//            for ( int j = 0; j < 1000000000; j++ ) {
//                writer.write( (byte) 0xfe );
//            }
//            writer.close();
//            System.out.println( "For 1 << " + i + ": runtime = " + ( System.currentTimeMillis() - ms ) + " ms." );
//        }
//        fail();
//    }
}