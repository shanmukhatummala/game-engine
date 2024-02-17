package game.map;

import static game.GameEngine.RESOURCES_PATH;
import static game.map.MapSaver.saveMap;

import java.io.BufferedReader;
import java.io.IOException;

public class MapEditor {

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
                    saveMap(RESOURCES_PATH + p_fileName, p_map);
                    break;
                } else if (l_args.length == 1 && "validatemap".equals(l_args[0])) {
                    // call validateMap() method from here
                } else if (l_args.length >= 1 && l_args[0].startsWith("edit")) {
                    // edit commands are of 2 types -- add or remove
                    // edit commands would be implemented by Joyjit. Leaving this for them.
                } else {
                    throw new IllegalArgumentException("Not a valid command");
                }
            }
        } catch (IOException l_e) {
            throw new RuntimeException(l_e);
        }
    }
}
