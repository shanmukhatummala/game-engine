package game;

import game.commands.Command;
import game.commands.CommandParser;
import game.logger.LogEntryBuffer;
import game.logger.LogFileWriter;
import game.logger.StdOutWriter;
import game.map.Map;
import game.pojo.Player;
import game.states.ExecuteOrderPhase;
import game.states.Phase;
import game.states.PlaySetupPhase;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * GameEngine is responsible for reading the main commands from the players and calling required
 * methods to perform the actions
 */
public class GameEngine {

    /** This static variable stores the path for the resources directory */
    public static final String RESOURCES_PATH = "src/main/resources/";

    /** This static variable is used for logging */
    public static final LogEntryBuffer LOG_ENTRY_BUFFER = new LogEntryBuffer(new ArrayList<>());

    @Getter @Setter private Phase d_gamePhase;
    private final Map d_map;

    /**
     * Constructor with map argument for GameEngine
     *
     * @param p_map map for the game
     */
    public GameEngine(Map p_map) {
        this.d_map = p_map;
        this.d_gamePhase = new PlaySetupPhase();
        LOG_ENTRY_BUFFER.attach(new LogFileWriter("log.txt"));
        LOG_ENTRY_BUFFER.attach(new StdOutWriter());
    }

    /** Constructor without arguments for GameEngine */
    public GameEngine() {
        this(new Map());
    }

    /**
     * Starts the game, and reads the input commands from the user and calls the required methods
     */
    public void startGame() {

        try (BufferedReader l_bufferedReader =
                new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                try {
                    // take the command and validate it
                    String l_message =
                            (d_gamePhase.getClass().getSimpleName().equals("EditMapPhase"))
                                    ? "Enter commands to 'edit (or) validate (or) save map':"
                                    : "Enter the command";
                    System.out.println(l_message);
                    String l_usrInput = l_bufferedReader.readLine();
                    List<Command> l_commandList = CommandParser.parse(l_usrInput);
                    String l_commandType = l_commandList.get(0).getD_commandType();
                    Command l_command = l_commandList.get(0);
                    if ("editmap".equals(l_commandType)) {
                        d_gamePhase.handleEditMap(this, l_command, d_map, RESOURCES_PATH);
                    } else if ("gameplayer".equals(l_commandType)) {
                        d_gamePhase.handleGamePlayer(l_commandList, d_map);
                    } else if ("loadmap".equals(l_commandType)) {
                        d_gamePhase.handleLoadMap(l_command, d_map, this, RESOURCES_PATH);
                    } else if ("showmap".equals(l_commandType)) {
                        d_gamePhase.handleShowMap(d_map);
                    } else if ("savemap".equals(l_commandType)) {
                        d_gamePhase.handleSaveMap(l_command, d_map, this, RESOURCES_PATH);
                    } else if ("validatemap".equals(l_commandType)) {
                        d_gamePhase.handleValidateMap(d_map);
                    } else if ("editcontinent".equals(l_commandType)
                            || "editcountry".equals(l_commandType)
                            || "editneighbor".equals(l_commandType)) {
                        d_gamePhase.handleEditCountriesOrContinentOrNeighbor(
                                l_usrInput.split(" "), d_map);
                    } else if ("assigncountries".equals(l_commandType)) {
                        d_gamePhase.handleCountriesAssignment(d_map, this);
                        startGameLoop(d_map, l_bufferedReader);
                        System.out.println("Game over - all orders executed");
                        endGame();
                    } else {
                        d_gamePhase.printInvalidCommandMessage(
                                "Invalid Command in state "
                                        + d_gamePhase.getClass().getSimpleName());
                    }
                } catch (Exception l_e) {
                    System.out.println(l_e.getMessage());
                }
            }
        } catch (IOException l_e) {
            throw new RuntimeException(l_e);
        }
    }

    /**
     * Starts the game loop - calls assign reinforcements, issue orders, execute orders
     *
     * @param p_map map for the game
     */
    private void startGameLoop(Map p_map, BufferedReader p_bufferedReader) {

        while (p_map.getD_players().size() > 1) {
            d_gamePhase.handleReinforcementsAssignment(p_map, this);
            takeOrders(p_map, p_bufferedReader);
            Set<Player> l_playersToAssignCard = new HashSet<>();
            d_gamePhase.handleExecutingOrders(p_map, this, l_playersToAssignCard);
            p_map.getD_players().forEach(l_player -> l_player.getD_negotiatedPlayers().clear());
            d_gamePhase.handleCardAssignment(l_playersToAssignCard, this);
            p_map.getD_players().removeIf(l_player -> l_player.getD_countries().isEmpty());
            d_gamePhase.handleShowMap(p_map);
        }

        if (p_map.getD_players().size() == 1) {
            System.out.println(
                    "Congratulations, "
                            + p_map.getD_players().get(0).getD_name()
                            + ", you are the winner!");
        }
    }

    /**
     * Loop over all the players until they issue all the orders
     *
     * @param p_map map for the game
     */
    private void takeOrders(Map p_map, BufferedReader p_bufferedReader) {
        List<Player> l_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (!l_playersLeftToIssueOrder.contains(l_player)) {
                    continue;
                }
                while (true) {
                    try {
                        System.out.println(
                                "Player: "
                                        + l_player.getD_name()
                                        + ", enter the command "
                                        + "(reinforcements available before the start of this round: "
                                        + l_player.getD_reinforcements()
                                        + (l_player.getD_cards().isEmpty()
                                                ? ""
                                                : " and cards available before the start of this round: "
                                                        + l_player.getD_cards())
                                        + "):");
                        String l_commandString = p_bufferedReader.readLine();
                        Command l_command;
                        try {
                            l_command = CommandParser.parse(l_commandString).get(0);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        if ("showmap".equals(l_command.getD_commandType())) {
                            d_gamePhase.handleShowMap(d_map);
                        } else if ("commit".equals(l_command.getD_commandType())) {
                            d_gamePhase.handleCommit(l_playersLeftToIssueOrder, l_player);
                            break;
                        } else {
                            d_gamePhase.handleIssuingOrders(d_map, l_player, l_command);
                            break;
                        }
                    } catch (IOException e) {
                        d_gamePhase.printInvalidCommandMessage(
                                "Error when reading command. Error message: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Commands will be executed");
        this.setD_gamePhase(new ExecuteOrderPhase());
    }

    /** Stops the program or in other words ends the game */
    public static void endGame() {
        System.exit(0);
    }
}
