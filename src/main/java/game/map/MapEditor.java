package game.map;

import game.map.MapManipulation.MapManipulator;

import static game.GameEngine.RESOURCES_PATH;
import static game.map.MapSaver.saveMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapEditor {
    private static final MapManipulator mapManipulator = new MapManipulator();

    public static void editMap(Map map, String fileName) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter commands to 'edit (or) validate (or) save map': ");
            while (true) {
                String command = reader.readLine();
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
                    mapManipulator.processCommand(args, map);
                } else {
                    throw new IllegalArgumentException("Not a valid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
