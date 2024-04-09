package game.states;

import static game.constants.PlayerStrategies.AI_PLAYER_STRATEGY_TO_NAME;
import static game.constants.PlayerStrategies.PLAYER_STRATEGY_MAP;
import static game.map.MapLoader.loadMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.constants.PlayerStrategies;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;
import game.strategy.Human;
import game.strategy.PlayerStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Represents the phase in the game where initial setup actions are performed. */
public class PlaySetupPhase extends StartUpPhase {

    /**
     * Constructs an PlaySetupPhase object. Adds a log entry to the global LOG_ENTRY_BUFFER
     * indicating the start of this phase.
     */
    public PlaySetupPhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    /**
     * Handles the command to load a map and transitions to the play setup phase.
     *
     * @param p_command The command to load a map.
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge, String p_basePath) {
        String l_fileName = p_command.getD_args().get(0);
        String l_filePath = p_basePath + l_fileName;
        loadMap(l_filePath, p_map);

        if (!isMapValid(p_map)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    "The loaded map is invalid, please load a valid map.");
            p_map.clearMap();
            return;
        }
        p_ge.setD_gamePhase(new PlaySetupPhase());
    }

    /**
     * Handles commands related to adding or removing players from the game.
     *
     * @param p_commandList The list of commands to add or remove players.
     * @param p_map The current map.
     */
    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        for (Command l_command : p_commandList) {
            List<String> l_commandArgs = l_command.getD_args();
            if (l_commandArgs.get(0).equals("-add")) {
                PlayerStrategy l_playerStrategy = resolveStrategyForPlayerAdd(l_commandArgs);
                p_map.addPlayer(l_commandArgs.get(1), l_playerStrategy);
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                        "Player " + l_commandArgs.get(1) + " added");
            } else {
                p_map.removePlayer(l_commandArgs.get(1));
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                        "Player " + l_commandArgs.get(1) + " removed");
            }
        }
    }

    /**
     * Resolves the PlayerStrategy for Add player from CL arguments
     *
     * @param p_commandArgs The CL arguments for Player Add command.
     * @return The player strategy resolved for adding a player
     */
    public PlayerStrategy resolveStrategyForPlayerAdd(List<String> p_commandArgs) {
        if (p_commandArgs.size() < 3) {
            // No PlayerStrategy in Add Player command, will be human by default
            return Human.getHumanStrategy();
        }

        return PlayerStrategies.PLAYER_STRATEGY_MAP.getOrDefault(
                p_commandArgs.get(2), Human.getHumanStrategy());
    }

    /**
     * Assigns countries to players and transitions to the assign resources phase.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @throws Exception If countries cannot be assigned.
     */
    @Override
    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) throws Exception {

        List<Player> l_players = p_map.getD_players();
        List<Country> l_countries = p_map.getD_countries();
        boolean l_countriesAssigned = p_map.assignCountries(l_players, l_countries);
        if (!l_countriesAssigned) {
            throw new Exception("try again.");
        }

        GameEngine.LOG_ENTRY_BUFFER.addLogEntries(
                List.of("Countries have been assigned.", "You have entered the play mode."));

        p_ge.setD_gamePhase(new AssignResourcesPhase());
    }

    /**
     * Handles the command to edit a map. If the map file does not exist, creates a new file.
     *
     * @param p_ge The game engine managing the game state.
     * @param p_command The command to edit the map.
     * @param p_map The current map.
     */
    @Override
    public void handleEditMap(GameEngine p_ge, Command p_command, Map p_map, String p_basePath) {
        String l_fileName = p_command.getD_args().get(0);
        String l_filePath = p_basePath + l_fileName;
        if (!fileExists(l_filePath)) {
            createNewFileForMap(l_filePath);
            p_map.setD_mapName(l_fileName);
        } else {
            loadMap(l_filePath, p_map);
        }
        p_ge.setD_gamePhase(new EditMapPhase());

        GameEngine.LOG_ENTRY_BUFFER.addLogEntry("You have entered the editing mode.");
    }

    /**
     * Handles the command to save a map. Displays an invalid command message as saving a map is not
     * allowed during setup.
     *
     * @param p_command The command to save the map.
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleSaveMapCommand(
            Command p_command, Map p_map, GameEngine p_ge, String p_basePath) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't save a map here";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command that mentions the type to save the map.
     *
     * @param p_command command that mentions the type
     * @param p_map current map
     * @param p_ge game engine managing the game state
     * @param p_basePath path where the map is located
     */
    public void handleSaveMapType(
            Command p_command, Map p_map, GameEngine p_ge, String p_basePath) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't save a map here";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command to validate a map. Displays an invalid command message as validating a
     * map is not allowed during setup.
     *
     * @param p_map The current map.
     */
    @Override
    public void handleValidateMap(Map p_map) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map here";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command to edit countries, continents, or neighbors. Displays an invalid command
     * message as editing a map is not allowed during setup.
     *
     * @param p_commandList The List of Command objects.
     * @param p_map The current map.
     */
    @Override
    public void handleEditCountriesOrContinentOrNeighbor(List<Command> p_commandList, Map p_map) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't edit map while not in the edit mode phase";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the loading of the game state
     *
     * @param p_ge reference for GameEngine object
     * @param p_map the game map
     * @param p_filepath p_filepath binary file path
     * @return the list of players
     * @throws Exception when loading game fails
     */
    @Override
    public List<Player> handleLoadGame(GameEngine p_ge, Map p_map, String p_filepath)
            throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(p_filepath))) {
            Map l_map = (Map) in.readObject();
            p_map.clearMap();
            p_map.setD_mapName(l_map.getD_mapName());
            for (int i = 0; i < l_map.getD_continents().size(); i++) {
                p_map.addContinent(l_map.getD_continents().get(i));
            }
            for (int i = 0; i < l_map.getD_countries().size(); i++) {
                p_map.addCountry(l_map.getD_countries().get(i));
            }
            for (int i = 0; i < l_map.getD_players().size(); i++) {
                p_map.addPlayer(l_map.getD_players().get(i));
            }

            List<Player> l_playersLeftToIssueOrder = (List<Player>) in.readObject();

            Integer l_currentPlayerIndex = (Integer) in.readObject();
            p_ge.setD_currentPlayerIndex(l_currentPlayerIndex);
            p_ge.setD_gamePhase(new IssueOrderPhase());
            return l_playersLeftToIssueOrder;

        } catch (IOException | ClassNotFoundException l_e) {
            if (l_e instanceof FileNotFoundException) {
                System.out.println("The file you entered doesn't exist");
            } else {
                p_map.clearMap();
                System.out.println(
                        "Loading map failed with error: "
                                + l_e.getMessage()
                                + ". So loading stopped.");
            }
            throw new Exception("Try again.");
        }
    }

    /**
     * Handles the execution of the tournament command by parsing the command list and initiating
     * the tournament. It processes the commands to extract tournament configurations such as maps,
     * player strategies, number of games, and maximum number of turns. If any configuration is
     * missing or invalid, it logs an error.
     *
     * @param p_commandList The list of commands related to the tournament configuration. Expected
     *     commands include map selection (-M), player strategies (-P), number of games (-G), and
     *     maximum number of turns (-D).
     */
    @Override
    public void handleTournament(List<Command> p_commandList) {
        List<String> l_maps = new ArrayList<>();
        List<String> l_playerStrategies = new ArrayList<>();
        Integer l_maxNumberOfTurns = null;
        Integer l_numberOfGames = null;

        for (Command l_command : p_commandList) {
            List<String> l_args = l_command.getD_args();
            String l_param = l_args.get(0);

            switch (l_param) {
                case "-M":
                    for (int l_i = 1; l_i < l_args.size(); l_i++) {
                        l_maps.add(l_args.get(l_i));
                    }
                    l_maps = l_maps.stream().distinct().collect(Collectors.toList());
                    if (l_maps.size() < 1 || l_maps.size() > 5) {
                        printInvalidCommandMessage("Forbidden number of maps. Try again!");
                        return;
                    }
                    break;
                case "-P":
                    for (int l_i = 1; l_i < l_args.size(); l_i++) {
                        if (AI_PLAYER_STRATEGY_TO_NAME.containsKey(l_args.get(l_i))) {
                            l_playerStrategies.add(l_args.get(l_i));
                        }
                    }
                    l_playerStrategies =
                            l_playerStrategies.stream().distinct().collect(Collectors.toList());
                    if (l_playerStrategies.size() < 2 || l_playerStrategies.size() > 4) {
                        printInvalidCommandMessage(
                                "Forbidden number of Player Strategies. Try again!");
                        return;
                    }
                    break;
                case "-G":
                    l_numberOfGames = Integer.valueOf(l_args.get(1));
                    if (l_numberOfGames < 1 || l_numberOfGames > 5) {
                        printInvalidCommandMessage("Forbidden number of Games. Try again!");
                        return;
                    }
                    break;
                case "-D":
                    l_maxNumberOfTurns = Integer.valueOf(l_args.get(1));
                    if (l_maxNumberOfTurns < 10 || l_maxNumberOfTurns > 50) {
                        printInvalidCommandMessage("Forbidden number of Turns. Try again!");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid Param: " + l_param + " for tournament command");
            }
        }

        if (l_maps.isEmpty()
                || l_playerStrategies.isEmpty()
                || l_numberOfGames == null
                || l_maxNumberOfTurns == null) {
            printInvalidCommandMessage("Invalid Tournament command. Try again!");
            return;
        }

        String[][] l_tournamentResult =
                runTournament(l_maps, l_playerStrategies, l_maxNumberOfTurns, l_numberOfGames);

        String l_formattedTournamentResultOutput =
                formatTournamentResults(
                        l_tournamentResult,
                        l_maps,
                        l_playerStrategies,
                        l_maxNumberOfTurns,
                        l_numberOfGames);

        System.out.println(l_formattedTournamentResultOutput);
    }

    /**
     * Runs the tournament based on the provided configurations. It sets up the game environment for
     * each map and game combination, executes the games, and collects the results.
     *
     * @param p_maps The list of map files to be used in the tournament.
     * @param p_playerStrategies The list of player strategies participating in the tournament.
     * @param p_maxNumberOfTurns The maximum number of turns allowed for each game.
     * @param p_numberOfGames The number of games to be played on each map.
     * @return A 2D array containing the results of the tournament, with each row representing a map
     *     and each column representing a game.
     */
    public String[][] runTournament(
            List<String> p_maps,
            List<String> p_playerStrategies,
            Integer p_maxNumberOfTurns,
            Integer p_numberOfGames) {
        String[][] l_tournamentResult = new String[p_maps.size()][p_numberOfGames];

        for (int l_mapIndex = 0; l_mapIndex < p_maps.size(); l_mapIndex++) {
            for (int l_gameNumber = 0; l_gameNumber < p_numberOfGames; l_gameNumber++) {
                GameEngine l_gameEngine = new GameEngine(new Map());

                l_gameEngine
                        .getD_gamePhase()
                        .handleLoadMap(
                                new Command("loadmap", List.of(p_maps.get(l_mapIndex))),
                                l_gameEngine.getD_map(),
                                l_gameEngine,
                                GameEngine.RESOURCES_PATH);

                for (String l_playerStrategy : p_playerStrategies) {
                    l_gameEngine
                            .getD_map()
                            .getD_players()
                            .add(
                                    new Player(
                                            AI_PLAYER_STRATEGY_TO_NAME.get(l_playerStrategy),
                                            PLAYER_STRATEGY_MAP.get(l_playerStrategy)));
                }

                l_tournamentResult[l_mapIndex][l_gameNumber] =
                        l_gameEngine.runTournamentLoop(l_gameEngine.getD_map(), p_maxNumberOfTurns);
            }
        }

        return l_tournamentResult;
    }

    /**
     * Formats the results of a tournament into a structured string representation. This method
     * takes the 2D array of tournament results, the list of maps, the list of player strategies,
     * the maximum number of turns, and the number of games, then constructs a string that displays
     * the tournament configuration and outcomes in a readable format with game labels above their
     * respective values.
     *
     * @param p_tournamentResult The 2D array containing the results of the tournament. Each row
     *     represents a map, and each column represents a game.
     * @param p_maps The list of map names used in the tournament.
     * @param p_playerStrategies The list of player strategies participating in the tournament.
     * @param p_maxNumberOfTurns The maximum number of turns allowed for each game.
     * @param p_numberOfGames The number of games to be played on each map.
     * @return A formatted string representing the tournament results.
     */
    public String formatTournamentResults(
            String[][] p_tournamentResult,
            List<String> p_maps,
            List<String> p_playerStrategies,
            Integer p_maxNumberOfTurns,
            Integer p_numberOfGames) {
        StringBuilder sb = new StringBuilder();
        sb.append("M: ");
        p_maps.forEach(map -> sb.append(map).append(", "));
        sb.setLength(sb.length() - 2); // Remove the last comma and space
        sb.append("\n");

        sb.append("P: ");
        p_playerStrategies.forEach(strategy -> sb.append(strategy).append(", "));
        sb.setLength(sb.length() - 2); // Remove the last comma and space
        sb.append("\n");

        sb.append("G: ").append(p_numberOfGames).append("\n");
        sb.append("D: ").append(p_maxNumberOfTurns).append("\n");

        // Game labels
        sb.append("       "); // Padding for alignment
        for (int i = 0; i < p_numberOfGames; i++) {
            sb.append(String.format("Game %d ", i + 1));
        }
        sb.append("\n");

        // Results for each map
        for (int mapIndex = 0; mapIndex < p_tournamentResult.length; mapIndex++) {
            sb.append(p_maps.get(mapIndex)).append(" ");
            for (int gameIndex = 0; gameIndex < p_tournamentResult[mapIndex].length; gameIndex++) {
                sb.append(String.format("%-8s", p_tournamentResult[mapIndex][gameIndex]));
            }
            sb.append("\n");
        }

        return sb.toString().trim();
    }
}
