package game.util;

import java.io.File;
import java.io.IOException;

public class FileHelper {

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    public static void createNewFileForMap(String path) {

        try {
            boolean fileCreated = new File(path).createNewFile();
            if (!fileCreated) {
                throw new IllegalStateException("File with same name already exists");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating new file - " + e.getMessage());
        }
    }
}
