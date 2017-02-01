package cz.routing.filebuilder.utils;

import java.io.File;

/**
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class Environment {

    public static String getResourceFolderAbsolutePath( String resourceRelativePath ) {
        return new File( resourceRelativePath ).getAbsolutePath();
    }
}
