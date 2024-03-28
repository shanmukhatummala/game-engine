package game.states;

import static game.map.MapLoader.loadMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;
import game.reader.ConquestFileReader;
import game.reader.FileReaderAdapter;
import game.reader.MapFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        try (BufferedReader l_reader =
                new BufferedReader(
                        new java.io.FileReader(p_basePath + p_command.getD_args().get(0)))) {
            String l_firstLine = l_reader.readLine();
            if (l_firstLine.equals("[Map]") || l_firstLine.equals("[Continents]")) {
                MapFileReader l_mapFileReader = new FileReaderAdapter(new ConquestFileReader());
                l_mapFileReader.readFile(p_basePath + p_command.getD_args().get(0), p_map);
            } else {
                MapFileReader l_mapFileReader = new MapFileReader();
                l_mapFileReader.readFile(p_basePath + p_command.getD_args().get(0), p_map);
            }
        } catch (IOException l_e) {
            if (l_e instanceof FileNotFoundException) {
                System.out.println("The file you entered doesn't exist");
            } else {
                throw new RuntimeException(l_e.getMessage());
            }
        }

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
                p_map.addPlayer(l_commandArgs.get(1));
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
    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge, String p_basePath) {
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
     * @param p_args The command arguments.
     * @param p_map The current map.
     */
    @Override
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't edit map while not in the edit mode phase";
        printInvalidCommandMessage(l_message);
    }
}
