package game.states;

import static game.map.MapSaver.saveMap;
import static game.map.MapValidator.isMapValid;
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.map.MapManipulation.MapManipulator;

import java.util.List;

/** Represents the state where the game map is being edited. */
public class EditMapPhase extends StartUpPhase {

    /**
     * Constructs an EditMapPhase object. Adds a log entry to the global LOG_ENTRY_BUFFER indicating
     * the start of this phase.
     */
    public EditMapPhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    /**
     * Handles the command to load a map.
     *
     * @param p_command The command to load a map.
     * @param p_map Current map for the game.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't load a map here.";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command to save the map and saves the map if it is valid and given an error message if invalid.
     *
     * @param p_command The command to save the map.
     * @param p_map Current map for the game.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge) {
        if (!p_map.getD_mapName().equals(p_command.getD_args().get(0))) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntries(
                    List.of(
                            "The file name in 'savemap' command is different from the file you are editing.",
                            "Enter the right file name in save command!"));
            return;
        }
        if (!isMapValid(p_map)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    "Current map is not valid: aborting the saving process.");
            //            continue;
        }
        saveMap(RESOURCES_PATH + p_map.getD_mapName(), p_map);
        p_ge.setD_gamePhase(new PlaySetupPhase());
    }

    /**
     * Handles the command to validate the map and confirm if the map is valid or not.
     *
     * @param p_map Current map for the game.
     */
    @Override
    public void handleValidateMap(Map p_map) {
        if (isMapValid(p_map)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("The current map is valid!");
        } else {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("The current map isn't valid.");
        }
    }

    /**
     * Handles the command to edit countries, continents, or neighbors using a MapManipulator object.
     *
     * @param p_args The arguments of the command.
     * @param p_map Current map for the game.
     */
    @Override
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map) {
        final MapManipulator l_mapManipulator = new MapManipulator();
        l_mapManipulator.processCommand(p_args, p_map);
    }

    /**
     * Handles the command to assign countries to players and displays an invalid command message
     * as assigning countries is not allowed in the edit map phase
     *
     * @param p_map Current map for the game.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) {
        String l_message = "Invalid Command in state" + this.getClass().getSimpleName();
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command to edit the map and displays an invalid command message as the game is already
     * in the edit map mode.
     *
     * @param p_ge The game engine managing the game state.
     * @param p_command The command to edit the map.
     * @param p_map Current map for the game.
     */
    @Override
    public void handleEditMap(GameEngine p_ge, Command p_command, Map p_map) {
        String l_message = "Invalid Command you are already in the Edit Map mode";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command related to game players and displays an invalid command message
     * as adding or removing players is not allowed while editing a map.
     *
     * @param p_commandList The list of commands related to game players.
     * @param p_map Current map for the game.
     */
    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't add or remove player while editing a map";
        printInvalidCommandMessage(message);
    }
}
