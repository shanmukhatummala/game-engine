package game;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static game.map.MapEditor.editMap;
import static game.map.MapLoader.loadMap;
import static game.map.MapHelper.playerOwnsContinent;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;
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
                } else if (l_commandArgs.length == 2 && "loadmap".equals(l_commandArgs[0])) {
                    loadMap(RESOURCES_PATH + l_commandArgs[1], l_map);
                    if (!isMapValid(l_map)) {
                    	System.out.println("The loaded map is invalid, please load a valid map.");
                    	l_map = new Map();
                    }
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
                                    System.out.println("Player added");
                                } else {
                                    l_map.removePlayer(l_commandArgs[l_idx + 1]);
                                    System.out.println("Player removed");
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
                    System.out.println("Countries have been assigned");
                    startGameLoop(l_map);
                    System.out.println("Game over - all orders executed");
                    endGame();
                } else {
                    System.out.println("Not a valid command. Try again");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Starts the game loop - calls assign reinforcements, issue orders, execute orders</p>
     * @param p_map map for the game
     */
    private static void startGameLoop(Map p_map) {
        assignReinforcements(p_map);
        issueOrders(p_map);
        executeOrders(p_map);
    }

    /**
     * <p>The method assign army's to each player</p>
     * @param p_map map for the game
     * @author Naveen
     */

    public static void assignReinforcements(Map p_map) {

        final int l_reinforcements_per_player = 5; // Default reinforcements per player

        for (Player l_player : p_map.getD_players()) {
            int l_currentReinforcements = l_player.getD_reinforcements(); // current reinforcements

            for (Continent continent : p_map.getD_continents()) {
                if (playerOwnsContinent(p_map,l_player, continent)) {
                    // If the player owns the continent, add the bonus armies
                    l_currentReinforcements += continent.getD_bonus();
                }
            }
            // Set the total reinforcements for the player
            l_player.setD_reinforcements(l_currentReinforcements + l_reinforcements_per_player);
        }
        System.out.println("Reinforcement armies are assigned");
    }

    /**
     * <p>Loop over all the players until they issue all the orders</p>
     * @param p_map map for the game
     */
    private static void issueOrders(Map p_map) {
        List<Player> l_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
        Scanner l_scanner ;
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (l_player.getD_reinforcements() != 0) {
                    l_scanner = new Scanner(System.in);
                    while(true){
                        System.out.println("Player: " + l_player.getD_name() + ", enter the command: ");
                        String l_command = l_scanner.nextLine();
                        String[] l_commandArgs = l_command.split(" ");
                        if("showmap".equals(l_commandArgs[0]) || "deploy".equals(l_commandArgs[0])){
                            if (l_commandArgs.length == 1 && "showmap".equals(l_commandArgs[0])) {
                                showMap(p_map);
                            }else{
                                ByteArrayInputStream l_inputStream = new ByteArrayInputStream(l_command.getBytes());
                                l_scanner = new Scanner(l_inputStream);
                                Player.Scanner = l_scanner;
                                l_player.issue_order();
                                System.out.println("Deploy Command has been issued");
                                break;
                            }
                        }else {
                            System.out.println("Invalid command, try again");
                        }
                    }
                    System.out.println("player: "+l_player.getD_name()+", reinforcements: "+l_player.getD_reinforcements());
                } else {
                    l_playersLeftToIssueOrder.remove(l_player);
                }
            }
        }
        System.out.println("Command will be executed.");
    }

    /**
     * <p>Loop over all the players until all the orders are executed</p>
     * @param p_map map for the game
     */
    private static void executeOrders(Map p_map) {
        List<Player> l_playersLeftToExecuteOrders = new ArrayList<>(p_map.getD_players());
        while (!l_playersLeftToExecuteOrders.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (!l_player.getD_orderList().isEmpty()) {
                    l_player.next_order().execute();
                } else {
                    l_playersLeftToExecuteOrders.remove(l_player);
                }
            }
        }
        showMap(p_map);
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

    /**
     * <p>Getter for the map</p>
     * @return map that contains all countries, continents and players
     */
    public Map getD_map() {
        return d_map;
    }

    /**
     * <p>Stops the program or in other words ends the game</p>
     */
    public static void endGame() {
        System.exit(0);
    }
}