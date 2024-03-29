package game.map;

import game.reader.ConquestFileReader;
import game.reader.FileReaderAdapter;
import game.reader.MapFileReader;

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
        try (BufferedReader l_reader = new BufferedReader(new java.io.FileReader(p_path))) {
            String l_firstLine = l_reader.readLine();
            if (l_firstLine.equals("[Map]") || l_firstLine.equals("[Continents]")) {
                MapFileReader l_mapFileReader = new FileReaderAdapter(new ConquestFileReader());
                l_mapFileReader.readFile(p_path, p_map);
            } else {
                MapFileReader l_mapFileReader = new MapFileReader();
                l_mapFileReader.readFile(p_path, p_map);
            }
        } catch (IOException l_e) {
            if (l_e instanceof FileNotFoundException) {
                System.out.println("The file you entered doesn't exist");
            } else {
                throw new RuntimeException(l_e.getMessage());
            }
        }
    }
}
