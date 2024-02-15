package game;

import static game.map.MapEditor.editMap;
import static game.map.MapLoader.loadMap;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

import game.map.Map;
import game.map.MapShower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameEngine {

    public static final String RESOURCES_PATH = "src/main/resources/";
    private final Map map;

    public GameEngine(Map map) {
        this.map = map;
    }

    public static void main(String[] args) {

        // Main method runs when we run the project. This is the starting point of the project.
        System.out.println("Hello and welcome!");

        GameEngine gameEngine = new GameEngine(new Map());
        Map map = gameEngine.getMap();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                System.out.println("Enter the command: ");
                String command = bufferedReader.readLine();
                String[] commandArgs = command.split(" ");

                if (commandArgs.length == 2 && "editmap".equals(commandArgs[0])) {
                    String fileName = commandArgs[1];
                    String filePath = RESOURCES_PATH + fileName;
                    if (!fileExists(filePath)) {
                        createNewFileForMap(filePath);
                    } else {
                        loadMap(filePath, map);
                    }
                    editMap(map, filePath);
                }
                else if (commandArgs.length == 1 && "showmap".equals(commandArgs[0])) {
                    MapShower.showMap(map);
                }
                else if (commandArgs.length == 3 && "gameplayer".equals(commandArgs[0])) {
                    if (!isValidGamePlayerCommand(commandArgs)) {
                        System.out.println("Not a valid gameplayer command");
                        System.out.println("It should be like, 'gameplayer -add/-remove playername'");
                    } else {
                        try {
                            if (commandArgs[1].equals("-add")) {
                                map.addPlayer(commandArgs[2]);
                            } else {
                                map.removePlayer(commandArgs[2]);
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Not a valid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map getMap() {
        return map;
    }

    public static void endGame() {
        System.exit(0);
    }

    private static boolean isValidGamePlayerCommand(String[] commandArgs) {

        return "-add".equals(commandArgs[1]) || "-remove".equals(commandArgs[1]);
    }
}