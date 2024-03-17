package game.states;

import static game.map.MapSaver.saveMap;
import static game.map.MapValidator.isMapValid;
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.map.MapManipulation.MapManipulator;

import java.util.List;

public class EditMapPhase extends StartUpPhase {

    public EditMapPhase() {
        GameEngine.d_logEntryBuffer.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }
    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't load a map here.";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge) {
        if (!p_map.getD_mapName().equals(p_command.getArgs().get(0))) {
            GameEngine.d_logEntryBuffer.addLogEntries(
                    List.of("The file name in 'savemap' command is different from the file you are editing.",
                    "Enter the right file name in save command!"));
            return;
        }
        if (!isMapValid(p_map)) {
            GameEngine.d_logEntryBuffer.addLogEntry("Current map is not valid: aborting the saving process.");
            //            continue;
        }
        saveMap(RESOURCES_PATH + p_map.getD_mapName(), p_map);
        p_ge.setGamePhase(new PlaySetupPhase());
    }

    @Override
    public void handleValidateMap(Map p_map) {
        if (isMapValid(p_map)) {
            GameEngine.d_logEntryBuffer.addLogEntry("The current map is valid!");
        } else {
            GameEngine.d_logEntryBuffer.addLogEntry("The current map isn't valid.");
        }
    }

    @Override
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map) {
        final MapManipulator mapManipulator = new MapManipulator();
        mapManipulator.processCommand(p_args, p_map);
    }

    @Override
    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) {
        String message = "Invalid Command in state" + this.getClass().getSimpleName();
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleEditMap(GameEngine ge, Command p_command, Map p_map) {
        String message = "Invalid Command you are already in the Edit Map mode";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't add or remove player while editing a map";
        printInvalidCommandMessage(message);
    }
}
