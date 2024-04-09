package game.states;

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
import java.util.List;

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
     */
    public PlayerStrategy resolveStrategyForPlayerAdd(List<String> p_commandArgs) {
        if (p_commandArgs.size() < 3) {
            // No PlayerStrategy in Add Player command, will be human by default
            return Human.getHumanStrategy();
        }

        return PlayerStrategies.playerStrategyMap.getOrDefault(p_commandArgs.get(2),
                Human.getHumanStrategy());
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
     * @param p_ge
     * @param p_filepath
     * @return
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
}
