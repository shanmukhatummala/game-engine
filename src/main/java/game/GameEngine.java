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
import game.states.Phase;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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

    @Setter
    private Phase gamePhase;

    private final Map d_map;

    /**
     * Constructor with map argument for GameEngine
     *
     * @param p_map map for the game
     */
    public GameEngine(Map p_map) {
        this.d_map = p_map;
    }


    /**
     * Starts the game, and reads the input commands from the user and calls the required methods
     *
     * @param args contains the arguments passed to main - there won't be any for build 1
     */
    public static void main(String[] args) {

        // Main method runs when we run the project. This is the starting point of the project.
        System.out.println("Hello and welcome!");

        GameEngine l_gameEngine = new GameEngine(new Map());
        Map l_map = l_gameEngine.getD_map();

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
                                l_map.addPlayer(l_commandArgs.get(1));
                                System.out.println("Player " + l_commandArgs.get(1) + " added");
                            } else {
                                l_map.removePlayer(l_commandArgs.get(1));
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
                                loadMap(l_filePath, l_map);
                            }
                            editMap(l_bufferedReader, l_map, l_fileName);
                        } else if ("loadmap".equals(l_command.getCommandType())) {
                            loadMap(RESOURCES_PATH + l_command.getArgs().get(0), l_map);
                            if (!isMapValid(l_map)) {
                                System.out.println(
                                        "The loaded map is invalid, please load a valid map.");
                                l_map = new Map();
                            }
                        } else if ("showmap".equals(l_command.getCommandType())) {
                            showMap(l_map);
                        } else if ("assigncountries".equals(l_command.getCommandType())) {
                            List<Player> players = l_map.getD_players();
                            List<Country> countries = l_map.getD_countries();
                            boolean countriesAssigned = l_map.assignCountries(players, countries);
                            if (!countriesAssigned) {
                                continue;
                            }
                            System.out.println("Countries have been assigned");
                            startGameLoop(l_map);
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


    public void startGame(){

    }




    /**
     * Starts the game loop - calls assign reinforcements, issue orders, execute orders
     *
     * @param p_map map for the game
     */
    private static void startGameLoop(Map p_map) {
        assignReinforcements(p_map);
        issueOrders(p_map);
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
    private static void issueOrders(Map p_map) {
        List<Player> l_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
        Scanner l_scanner;
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (l_player.getD_reinforcements() != 0) {
                    l_scanner = new Scanner(System.in);
                    while (true) {
                        System.out.println(
                                "Player: " + l_player.getD_name() + ", enter the command: ");
                        String l_command = l_scanner.nextLine();
                        String[] l_commandArgs = l_command.split(" ");
                        if ("showmap".equals(l_commandArgs[0])
                                || "deploy".equals(l_commandArgs[0])) {
                            if (l_commandArgs.length == 1 && "showmap".equals(l_commandArgs[0])) {
                                showMap(p_map);
                            } else {
                                ByteArrayInputStream l_inputStream =
                                        new ByteArrayInputStream(l_command.getBytes());
                                l_scanner = new Scanner(l_inputStream);
                                Player.Scanner = l_scanner;
                                l_player.issue_order();
                                System.out.println("Deploy Command has been issued");
                                break;
                            }
                        } else {
                            System.out.println("Invalid command, try again");
                        }
                    }
                    System.out.println(
                            "player: "
                                    + l_player.getD_name()
                                    + ", reinforcements: "
                                    + l_player.getD_reinforcements());
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
