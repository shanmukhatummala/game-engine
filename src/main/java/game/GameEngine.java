package game;

import static game.map.MapEditor.editMap;
import static game.map.MapLoader.loadMap;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

import game.map.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameEngine {

    public static final String RESOURCES_PATH = "src/main/resources";
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
                    endGame();
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
}