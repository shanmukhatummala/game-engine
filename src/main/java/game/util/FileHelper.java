package game.util;

import java.io.File;
import java.io.IOException;

/**
 * FileHelper provides helper methods to deal with files
 *
 * @author Shanmukha
 */
public class FileHelper {

    /**
     * This method gives the continent by taking the id of the continent as input
     *
     * @param p_path path to the file
     * @return true if the file exists, false if the file doesn't exist
     */
    public static boolean fileExists(String p_path) {
        File l_file = new File(p_path);
        return l_file.exists() && !l_file.isDirectory();
    }

    /**
     * This method creates a new file for a map
     *
     * @param p_path path where to create the file
     */
    public static void createNewFileForMap(String p_path) {

        try {
            boolean l_fileCreated = new File(p_path).createNewFile();
            if (!l_fileCreated) {
                throw new IllegalStateException("File with same name already exists");
            }
        } catch (IOException l_e) {
            throw new RuntimeException("Error creating new file - " + l_e.getMessage());
        }
    }
}
