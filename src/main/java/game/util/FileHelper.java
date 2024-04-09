package game.util;

import static game.GameEngine.RESOURCES_PATH;

import org.apache.commons.io.FileUtils;

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

    /**
     * Gives the filename that is present in thr resources folder by taking the file path
     *
     * @param p_path path of the file
     * @return file name
     */
    public static String getFileNameFromResourcesPath(String p_path) {
        return p_path.substring(RESOURCES_PATH.length());
    }

    /**
     * Creates copy of the file before saving changes to a map file
     *
     * @param p_path path of the map file
     */
    public static void createCopyOfOriginalFileBeforeSaving(String p_path) {
        File l_copiedFile =
                new File(RESOURCES_PATH + "original_" + getFileNameFromResourcesPath(p_path));
        try {
            FileUtils.copyFile(new File(p_path), l_copiedFile);
        } catch (IOException l_e) {
            System.out.println(
                    "Something went wrong when creating a copy before saving. See if anything is wrong with the file you are saving");
        }
    }

    /**
     * deletes the copy of the file that we created before saving a map
     *
     * @param p_path path of the actual file
     */
    public static void deleteFileCopy(String p_path) {
        if (fileExists(RESOURCES_PATH + "original_" + getFileNameFromResourcesPath(p_path))) {
            boolean isFileDeleted =
                    new File(RESOURCES_PATH + "original_" + getFileNameFromResourcesPath(p_path))
                            .delete();
        }
    }

    /**
     * restores the content of the original file when saving changes failed
     *
     * @param p_path path of the original file
     */
    public static void restoreOriginalContent(String p_path) {
        File l_fileAfterEditing = new File(p_path);
        if (l_fileAfterEditing.delete()) {
            File l_renameToFile = new File(p_path);
            boolean l_isRenameSuccessful =
                    new File(RESOURCES_PATH + "original_" + getFileNameFromResourcesPath(p_path))
                            .renameTo(l_renameToFile);
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
