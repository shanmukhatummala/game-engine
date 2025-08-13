package game.map;

import static game.util.FileHelper.fileExists;

import game.mapfile.reader.ConquestFileReader;
import game.mapfile.reader.FileReaderAdapter;
import game.mapfile.reader.MapFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * MapLoader is used for loading the map
 *
 * @author Shanmukha
 */
public class MapLoader {

    /**
     * This method loads the map into map object by reading a file
     *
     * @param p_path path of the file
     * @param p_map reference to the map
     */
    public static void loadMap(String p_path, Map p_map) {
        if (!fileExists(p_path)) {
            System.out.println("The file you entered doesn't exist. Check the file name properly.");
            return;
        }
        String l_firstLine = null;
        try (BufferedReader l_reader = new BufferedReader(new java.io.FileReader(p_path))) {
            l_firstLine = l_reader.readLine();
        } catch (IOException l_e) {
            if (l_e instanceof FileNotFoundException) {
                System.out.println("The file you entered doesn't exist");
            } else {
                p_map.clearMap();
                System.out.println(
                        "Loading map failed with error: "
                                + l_e.getMessage()
                                + ". So loading stopped.");
            }
        }

        MapFileReader l_mapFileReader;
        if ("[Map]".equals(l_firstLine) || "[Continents]".equals(l_firstLine)) {
            l_mapFileReader = new FileReaderAdapter(new ConquestFileReader());
        } else {
            l_mapFileReader = new MapFileReader();
        }
        l_mapFileReader.readFile(p_path, p_map);
    }
}
