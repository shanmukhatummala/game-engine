package game;

import game.commands.Command;
import game.commands.CommandParser;
import game.logger.LogEntryBuffer;
import game.logger.LogFileWriter;
import game.logger.StdOutWriter;
import game.map.Map;
import game.pojo.Player;
import game.states.Phase;
import game.states.PlaySetupPhase;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Getter private final Map d_map;

    @Getter @Setter private Phase d_gamePhase;
    @Getter @Setter private Integer d_currentPlayerIndex;
    @Getter @Setter private boolean savingInProgress;

    /**
     * Constructor with map argument for GameEngine
     *
     * @param p_map map for the game
     */
    public GameEngine(Map p_map) {
        this.d_map = p_map;
        this.d_gamePhase = new PlaySetupPhase();
        this.d_currentPlayerIndex = 0;
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
                    promptForUserInput();
                    String l_usrInput = l_bufferedReader.readLine();
                    if (savingInProgress) {
                        l_usrInput = "savefiletype " + l_usrInput;
                    }
                    List<Command> l_commandList = CommandParser.parse(l_usrInput);
                    String l_commandType = l_commandList.get(0).getD_commandType();
                    Command l_command = l_commandList.get(0);
                    switch (l_commandType) {
                        case "editmap":
                            d_gamePhase.handleEditMap(this, l_command, d_map, RESOURCES_PATH);
                            break;
                        case "gameplayer":
                            d_gamePhase.handleGamePlayer(l_commandList, d_map);
                            break;
                        case "loadmap":
                            d_gamePhase.handleLoadMap(l_command, d_map, this, RESOURCES_PATH);
                            break;
                        case "loadgame":
                            List<Player> l_playersLeftToIssueOrder =
                                    d_gamePhase.handleLoadGame(
                                            this,
                                            d_map,
                                            RESOURCES_PATH + l_command.getD_args().get(0));
                            gameMode(
                                    l_bufferedReader,
                                    l_playersLeftToIssueOrder,
                                    d_currentPlayerIndex);
                            break;
                        case "showmap":
                            d_gamePhase.handleShowMap(d_map);
                            break;
                        case "savemap":
                            d_gamePhase.handleSaveMapCommand(
                                    l_command, d_map, this, RESOURCES_PATH);
                            break;
                        case "savefiletype":
                            d_gamePhase.handleSaveMapType(l_command, d_map, this, RESOURCES_PATH);
                            break;
                        case "validatemap":
                            d_gamePhase.handleValidateMap(d_map);
                            break;
                        case "editcontinent":
                        case "editcountry":
                        case "editneighbor":
                            d_gamePhase.handleEditCountriesOrContinentOrNeighbor(
                                    l_commandList, d_map);
                            break;
                        case "assigncountries":
                            d_gamePhase.handleCountriesAssignment(d_map, this);
                            gameMode(
                                    l_bufferedReader,
                                    new ArrayList<>(d_map.getD_players()),
                                    d_currentPlayerIndex);
                            break;
                        case "tournament":
                            d_gamePhase.handleTournament(l_commandList, this);
                            endGame();
                            break;
                        default:
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

    private void promptForUserInput() {
        String l_message;
        if (savingInProgress) {
            l_message =
                    "Enter in which format you want to save this map:\n1. Domination\n2. Conquest";
        } else {
            l_message =
                    (d_gamePhase.getClass().getSimpleName().equals("EditMapPhase"))
                            ? "Enter commands to 'edit (or) validate (or) save map':"
                            : "Enter the command";
        }
        System.out.println(l_message);
    }

    private void gameMode(
            BufferedReader P_bufferedReader,
            List<Player> p_playersLeftToIssueOrder,
            Integer p_currentPlayer) {
        startGameLoop(d_map, P_bufferedReader, p_playersLeftToIssueOrder, p_currentPlayer);
        System.out.println("Game over - all orders executed");
        endGame();
    }

    /**
     * Starts the game loop - calls assign reinforcements, issue orders, execute orders
     *
     * @param p_map map for the game
     */
    private void startGameLoop(
            Map p_map,
            BufferedReader p_bufferedReader,
            List<Player> p_playersLeftToIssueOrder,
            Integer p_currentPlayerIndex) {

        while (p_map.getD_players().size() > 1) {
            d_gamePhase.handleReinforcementsAssignment(p_map, this);
            d_gamePhase.handleIssuingOrders(
                    p_map, p_playersLeftToIssueOrder, p_currentPlayerIndex, this);
            Set<Player> l_playersToAssignCard = new HashSet<>();
            d_gamePhase.handleExecutingOrders(p_map, this, l_playersToAssignCard);
            p_map.getD_players().forEach(l_player -> l_player.getD_negotiatedPlayers().clear());
            d_gamePhase.handleCardAssignment(l_playersToAssignCard, this);
            p_map.getD_players().removeIf(l_player -> l_player.getD_countries().isEmpty());
            d_gamePhase.handleShowMap(p_map);
            p_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
            p_currentPlayerIndex = 0;
        }

        if (p_map.getD_players().size() == 1) {
            System.out.println(
                    "Congratulations, "
                            + p_map.getD_players().get(0).getD_name()
                            + ", you are the winner!");
        }
    }

    /**
     * Runs the Tournament game loop - calls assign reinforcements, issue orders, execute orders
     *
     * @return String: Player Name of winner, or "Draw" if it is a draw
     * @param p_map map for the game
     * @param p_maxNumberOfTurns Max number of turns allowed in a game
     * @param p_gameEngine GameEngine Object
     */
    public String runTournamentLoop(
            Map p_map, Integer p_maxNumberOfTurns, GameEngine p_gameEngine) {
        try {
            p_gameEngine.getD_gamePhase().handleCountriesAssignment(p_map, p_gameEngine);
            while (p_map.getD_players().size() > 1 && p_maxNumberOfTurns-- > 0) {
                p_gameEngine.getD_gamePhase().handleReinforcementsAssignment(p_map, p_gameEngine);
                p_gameEngine
                        .getD_gamePhase()
                        .handleIssuingOrders(
                                p_map, new ArrayList<>(p_map.getD_players()), 0, p_gameEngine);
                Set<Player> l_playersToAssignCard = new HashSet<>();
                p_gameEngine
                        .getD_gamePhase()
                        .handleExecutingOrders(p_map, p_gameEngine, l_playersToAssignCard);
                p_map.getD_players().forEach(l_player -> l_player.getD_negotiatedPlayers().clear());
                p_gameEngine
                        .getD_gamePhase()
                        .handleCardAssignment(l_playersToAssignCard, p_gameEngine);
                p_map.getD_players().removeIf(l_player -> l_player.getD_countries().isEmpty());
                p_gameEngine.getD_gamePhase().handleShowMap(p_map);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        if (p_map.getD_players().size() == 1) {
            return p_map.getD_players().get(0).getD_name();
        } else {
            return "Draw";
        }
    }

    /** Stops the program or in other words ends the game */
    public static void endGame() {
        System.exit(0);
    }
}
