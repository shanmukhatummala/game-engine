package game;

import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static game.map.MapEditor.editMap;
import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

/**
 * GameEngine is responsible for reading the main commands from the players and calling required methods to perform the actions
 */
public class GameEngine {

    public static final String RESOURCES_PATH = "src/main/resources/";
    private final Map d_map;

    public GameEngine(Map p_map) {
        this.d_map = p_map;
    }

    /**
     * <p>Starts the game, and reads the input commands from the user and calls the required methods</p>
     */
    public static void main(String[] args) {

        // Main method runs when we run the project. This is the starting point of the project.
        System.out.println("Hello and welcome!");

        GameEngine l_gameEngine = new GameEngine(new Map());
        Map l_map = l_gameEngine.getD_map();

        try (BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                System.out.println("Enter the command: ");
                String l_command = l_bufferedReader.readLine();
                String[] l_commandArgs = l_command.split(" ");

                if (l_commandArgs.length == 2 && "editmap".equals(l_commandArgs[0])) {
                    String l_fileName = l_commandArgs[1];
                    String l_filePath = RESOURCES_PATH + l_fileName;
                    if (!fileExists(l_filePath)) {
                        createNewFileForMap(l_filePath);
                    } else {
                        loadMap(l_filePath, l_map);
                    }
                    editMap(l_bufferedReader, l_map, l_fileName);
                }
                else if (l_commandArgs.length == 1 && "showmap".equals(l_commandArgs[0])) {
                    showMap(l_map);
                }
                else if (l_commandArgs.length >= 1 && "gameplayer".equals(l_commandArgs[0])) {
                    if (!isValidGamePlayerCommand(l_commandArgs)) {
                        System.out.println("Not a valid gameplayer command");
                        System.out.println("It should be like, 'gameplayer -add/-remove playername'");
                    } else {
                        try {
                            for (int l_idx = 1; l_idx < l_commandArgs.length; l_idx = l_idx + 2) {
                                if (l_commandArgs[l_idx].equals("-add")) {
                                    l_map.addPlayer(l_commandArgs[l_idx + 1]);
                                } else {
                                    l_map.removePlayer(l_commandArgs[l_idx + 1]);
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else if (l_commandArgs.length == 1 && l_commandArgs[0].equals("assigncountries")) {

                    List<Player> players = l_map.getD_players();
                    List<Country> countries = l_map.getD_countries();
                    l_map.assignCountries(players, countries);
                    startGameLoop(l_map);
                    System.out.println("Game over - all orders executed");
                    endGame();
                } else {
                    throw new IllegalArgumentException("Not a valid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startGameLoop(Map p_map) {
        assignReinforcements(p_map);
        issueOrders(p_map);
        executeOrders(p_map);
    }

    private static void assignReinforcements(Map p_map) {
        // to assign reinforcements
    }

    private static void issueOrders(Map p_map) {
        Set<Player> l_playersLeftToIssueOrder = new HashSet<>(p_map.getD_players());
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (l_player.getD_reinforcements() != 0) {
                    l_player.issue_order();
                } else {
                    l_playersLeftToIssueOrder.remove(l_player);
                }
            }
        }
    }

    private static void executeOrders(Map p_map) {
        Set<Player> l_playersLeftToExecuteOrders = new HashSet<>(p_map.getD_players());
        while (!l_playersLeftToExecuteOrders.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (!l_player.getD_orderList().isEmpty()) {
                    l_player.next_order().execute();
                } else {
                    l_playersLeftToExecuteOrders.remove(l_player);
                }
            }
        }
    }

    /**
     * <p>This method checks if a gameplayer command is valid</p>
     * @param commandArgs arguments in the gameplayer command
     * @return true if the command is valid, else returns false
     */
    public static boolean isValidGamePlayerCommand(String[] commandArgs) {

        for (int l_i = 1; l_i < commandArgs.length; l_i++) {
            if (l_i % 2 != 0) {
                if (!("-add".equals(commandArgs[l_i]) || "-remove".equals(commandArgs[l_i]))) {
                    return false;
                }
            } else {
                if ("-add".equals(commandArgs[l_i]) || "-remove".equals(commandArgs[l_i])) {
                    return false;
                }
            }
        }

        return !"-add".equals(commandArgs[commandArgs.length - 1]) && !"-remove".equals(commandArgs[commandArgs.length - 1]);
    }

    public Map getD_map() {
        return d_map;
    }

    public static void endGame() {
        System.exit(0);
    }
}