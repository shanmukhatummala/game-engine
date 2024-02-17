package game.util;

import java.io.File;
import java.io.IOException;

public class FileHelper {

    public static boolean fileExists(String path) {
        File l_file = new File(path);
        return l_file.exists() && !l_file.isDirectory();
    }

    public static void createNewFileForMap(String path) {

        try {
            boolean l_fileCreated = new File(path).createNewFile();
            if (!l_fileCreated) {
                throw new IllegalStateException("File with same name already exists");
            }
        } catch (IOException l_e) {
            throw new RuntimeException("Error creating new file - " + l_e.getMessage());
        }
    }
}
