package game.map;

import static game.GameEngine.RESOURCES_PATH;
import static game.map.MapSaver.saveMap;
import static game.map.MapValidator.isMapValid;
import static game.map.MapShower.showMap;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * MapEditor is used for performing edit operations on the map
 */
public class MapEditor {
    private static final MapManipulator mapManipulator = new MapManipulator();


    /**
     * <p>This method adds a continent to the list of continents in the map</p>
     * @param p_bufferedReader used to read the commands from the user
     * @param p_map reference to the map
     * @param p_fileName file that the user is editing
     */
    public static void editMap(BufferedReader p_bufferedReader, Map p_map, String p_fileName) {

        try {
            System.out.println("Enter commands to 'edit (or) validate (or) save map': ");
            while (true) {
                String l_command = p_bufferedReader.readLine();
                String[] l_args = l_command.split(" ");
                if (l_args.length == 2 && "savemap".equals(l_args[0])) {
                    if (!p_fileName.equals(l_args[1])) {
                        System.out.println("The file name in 'savemap' command is different from the file you are editing.");
                        System.out.println("Enter the right file name in save command!");
                        continue;
                    }
                    if (!isMapValid(p_map)) {
                    	System.out.println("Current map is not valid: aborting the saving process.");
                    	continue;
                    }
                    saveMap(RESOURCES_PATH + p_fileName, p_map);
                    break;
                }
                else if (l_args.length == 1 && "showmap".equals(l_args[0])) {
                    showMap(p_map);
                }
                else if (l_args.length == 1 && "validatemap".equals(l_args[0])) {
                    if (isMapValid(p_map))
                    	System.out.println("The current map is valid!");
                    else
                    	System.out.println("The current map isn't valid.");
                } else if (l_args.length >= 1 && l_args[0].startsWith("edit")) {
                    mapManipulator.processCommand(l_args, p_map);
                } else {
                    throw new IllegalArgumentException("Not a valid command");
                }
            }
        } catch (IOException l_e) {
            throw new RuntimeException(l_e);
        }
    }
}
