package game;

import static game.map.MapEditor.editMap;
import static game.map.MapHelper.playerOwnsContinent;
import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

import game.commands.Command;
import game.commands.CommandParser;
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
                    String l_usrInput = l_bufferedReader.readLine();
                    List<Command> l_commandList = CommandParser.parse(l_usrInput);

                    if (l_commandList.get(0).getCommandType().equals("gameplayer")) {
                        for (Command l_command : l_commandList) {
                            List<String> l_commandArgs = l_command.getArgs();
                            if (l_commandArgs.get(0).equals("-add")) {
                                d_map.addPlayer(l_commandArgs.get(1));
                                System.out.println("Player " + l_commandArgs.get(1) + " added");
                            } else {
                                d_map.removePlayer(l_commandArgs.get(1));
                                System.out.println("Player " + l_commandArgs.get(1) + " removed");
                            }
                        }
                    } else {
                        Command l_command = l_commandList.get(0);
                        if ("editmap".equals(l_command.getCommandType())) {
                            String l_fileName = l_command.getArgs().get(0);
                            String l_filePath = RESOURCES_PATH + l_fileName;
                            if (!fileExists(l_filePath)) {
                                createNewFileForMap(l_filePath);
                            } else {
                                loadMap(l_filePath, d_map);
                            }
                            editMap(l_bufferedReader, d_map, l_fileName);
                        } else if ("loadmap".equals(l_command.getCommandType())) {
                            loadMap(RESOURCES_PATH + l_command.getArgs().get(0), d_map);
                            if (!isMapValid(d_map)) {
                                System.out.println(
                                        "The loaded map is invalid, please load a valid map.");
                                d_map.clearMap();
                            }
                        } else if ("showmap".equals(l_command.getCommandType())) {
                            showMap(d_map);
                        } else if ("assigncountries".equals(l_command.getCommandType())) {
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
                            String l_commandString = p_bufferedReader.readLine();
                            Command command = CommandParser.parse(l_commandString).get(0);
                            if ("showmap".equals(command.getCommandType())) {
                                showMap(p_map);
                                continue;
                            }
                            l_player.issue_order(p_map, command);
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

    /** Stops the program or in other words ends the game */
    public static void endGame() {
        System.exit(0);
    }
}
