package game;

import static game.map.MapEditor.editMap;
import static game.map.MapHelper.playerOwnsContinent;
import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * GameEngine is responsible for reading the main commands from the players and calling required
 * methods to perform the actions
 */
public class GameEngine {

    /** This static variable stores the path for the resources directory */
    public static final String RESOURCES_PATH = "src/main/resources/";

    private final Map d_map;

    /**
     * Constructor with map argument for GameEngine
     *
     * @param p_map map for the game
     */
    public GameEngine(Map p_map) {
        this.d_map = p_map;
    }

    /** Constructor without arguments for GameEngine */
    public GameEngine() {
        this(new Map());
    }

    /**
     * Starts the game, and reads the input commands from the user and calls the required methods
     */
    public void start() {

        // Main method runs when we run the project. This is the starting point of the project.
        System.out.println("Hello and welcome!");

        try (BufferedReader l_bufferedReader =
                new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                try {
                    System.out.println("Enter the command: ");
                    String l_command = l_bufferedReader.readLine();
                    String[] l_commandArgs = l_command.split(" ");

                    if (l_commandArgs.length == 2 && "editmap".equals(l_commandArgs[0])) {
                        String l_fileName = l_commandArgs[1];
                        String l_filePath = RESOURCES_PATH + l_fileName;
                        if (!fileExists(l_filePath)) {
                            createNewFileForMap(l_filePath);
                        } else {
                            loadMap(l_filePath, d_map);
                        }
                        editMap(l_bufferedReader, d_map, l_fileName);
                    } else if (l_commandArgs.length == 2 && "loadmap".equals(l_commandArgs[0])) {
                        loadMap(RESOURCES_PATH + l_commandArgs[1], d_map);
                        if (!isMapValid(d_map)) {
                            System.out.println(
                                    "The loaded map is invalid, please load a valid map.");
                            d_map.clearMap();
                        }
                    } else if (l_commandArgs.length == 1 && "showmap".equals(l_commandArgs[0])) {
                        showMap(d_map);
                    } else if (l_commandArgs.length >= 1 && "gameplayer".equals(l_commandArgs[0])) {
                        if (!isValidGamePlayerCommand(l_commandArgs)) {
                            System.out.println("Not a valid gameplayer command");
                            System.out.println(
                                    "It should be like, 'gameplayer -add/-remove playername'");
                        } else {
                            try {
                                for (int l_idx = 1;
                                        l_idx < l_commandArgs.length;
                                        l_idx = l_idx + 2) {
                                    if (l_commandArgs[l_idx].equals("-add")) {
                                        d_map.addPlayer(l_commandArgs[l_idx + 1]);
                                        System.out.println(
                                                "Player " + l_commandArgs[l_idx + 1] + " added");
                                    } else {
                                        d_map.removePlayer(l_commandArgs[l_idx + 1]);
                                        System.out.println(
                                                "Player " + l_commandArgs[l_idx + 1] + " removed");
                                    }
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else if (l_commandArgs.length == 1
                            && l_commandArgs[0].equals("assigncountries")) {

                        List<Player> players = d_map.getD_players();
                        List<Country> countries = d_map.getD_countries();
                        boolean countriesAssigned = d_map.assignCountries(players, countries);
                        if (!countriesAssigned) {
                            continue;
                        }
                        System.out.println("Countries have been assigned");
                        startGameLoop(d_map, l_bufferedReader);
                        System.out.println("Game over - all orders executed");
                        endGame();
                    } else {
                        System.out.println("Not a valid command. Try again");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the game loop - calls assign reinforcements, issue orders, execute orders
     *
     * @param p_map map for the game
     */
    private static void startGameLoop(Map p_map, BufferedReader p_bufferedReader) {
        assignReinforcements(p_map);
        issueOrders(p_map, p_bufferedReader);
        executeOrders(p_map);
    }

    /**
     * The method assign army's to each player
     *
     * @param p_map map for the game
     * @author Naveen
     */
    public static void assignReinforcements(Map p_map) {

        final int l_reinforcements_per_player = 5; // Default reinforcements per player

        for (Player l_player : p_map.getD_players()) {
            l_player.setD_reinforcements(l_reinforcements_per_player);

            int l_additionalReinforcements = 0;
            for (Continent l_continent : p_map.getD_continents()) {
                if (playerOwnsContinent(p_map, l_player, l_continent)) {
                    // If the player owns the continent, add the bonus reinforcements
                    l_additionalReinforcements += l_continent.getD_bonus();
                }
            }
            // Set the total reinforcements for the player
            l_player.setD_reinforcements(
                    l_player.getD_reinforcements() + l_additionalReinforcements);
        }
        System.out.println("Reinforcements are assigned");
    }

    /**
     * Loop over all the players until they issue all the orders
     *
     * @param p_map map for the game
     */
    private static void issueOrders(Map p_map, BufferedReader p_bufferedReader) {
        List<Player> l_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (l_player.getD_reinforcements() != 0 || !l_player.getD_cards().isEmpty()) {
                    while (true) {
                        try {
                            System.out.println(
                                    "Player: " + l_player.getD_name() + ", enter the command: ");

                            String l_command = p_bufferedReader.readLine();
                            String[] l_commandArgs = l_command.split(" ");

                            if (l_commandArgs.length == 1 && "showmap".equals(l_commandArgs[0])) {
                                showMap(p_map);
                                continue;
                            }

                            if (Arrays.asList("deploy", "bomb").contains(l_commandArgs[0])) {
                                l_player.createOrder(l_commandArgs);
                            } else {
                                System.out.println("Invalid command. Try again: ");
                            }
                            break;
                        } catch (IOException e) {
                            System.out.println(
                                    "Error when reading command. Error message: " + e.getMessage());
                        }
                    }
                    System.out.println(
                            "player: "
                                    + l_player.getD_name()
                                    + ", reinforcements: "
                                    + l_player.getD_reinforcements()
                                    + (l_player.getD_cards().isEmpty()
                                            ? ""
                                            : ", cards " + l_player.getD_cards()));
                } else {
                    l_playersLeftToIssueOrder.remove(l_player);
                }
            }
        }
        System.out.println("Command will be executed.");
    }

    /**
     * Loop over all the players until all the orders are executed
     *
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
     * This method checks if a gameplayer command is valid
     *
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
        return !"-add".equals(commandArgs[commandArgs.length - 1])
                && !"-remove".equals(commandArgs[commandArgs.length - 1]);
    }

    /**
     * Getter for the map
     *
     * @return map that contains all countries, continents and players
     */
    public Map getD_map() {
        return d_map;
    }

    /** Stops the program or in other words ends the game */
    public static void endGame() {
        System.exit(0);
    }
}
