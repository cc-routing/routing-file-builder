package cz.routing.filebuilder.data;

import java.io.File;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class DataTestUtils {

    public static String getResourceFolderAbsolutePath(){
        return new File( "src/test/resources" ).getAbsolutePath();
    }
}
