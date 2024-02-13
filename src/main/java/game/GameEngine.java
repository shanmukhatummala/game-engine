package game;

import game.map.Map;
import game.map.MapShower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static game.map.MapCreator.createNewMap;
import static game.map.MapLoader.loadMap;
import static game.util.FileHelper.fileExists;

public class GameEngine {

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
                    String path = "src/main/resources/" + fileName;
                    if (!fileExists(path)) {
                        createNewMap(fileName);
                    }
                    loadMap(path, map);
                    // endGame();
                }
                else if (commandArgs.length == 1 && "showmap".equals(commandArgs[0])) {
                    // Map Map;
                    MapShower.mapShower(map);
                }
                else {
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
}
