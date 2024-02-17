package game.map;

import static game.GameEngine.RESOURCES_PATH;
import static game.map.MapSaver.saveMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapEditor {

    public static void editMap(BufferedReader bufferedReader, Map map, String fileName) {

        try {
            System.out.println("Enter commands to 'edit (or) validate (or) save map': ");
            while (true) {
                String command = bufferedReader.readLine();
                String[] args = command.split(" ");
                if (args.length == 2 && "savemap".equals(args[0])) {
                    if (!fileName.equals(args[1])) {
                        System.out.println("The file name in 'savemap' command is different from the file you are editing.");
                        System.out.println("Enter the right file name in save command!");
                        continue;
                    }
                    saveMap(RESOURCES_PATH + fileName, map);
                    break;
                } else if (args.length == 1 && "validatemap".equals(args[0])) {
                    // call validateMap() method from here
                } else if (args.length >= 1 && args[0].startsWith("edit")) {
                    // edit commands are of 2 types -- add or remove
                    // edit commands would be implemented by Joyjit. Leaving this for them.
                } else {
                    throw new IllegalArgumentException("Not a valid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
