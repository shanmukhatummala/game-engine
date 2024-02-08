package game.util;

import java.io.File;

public class FileHelper {

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }
}
