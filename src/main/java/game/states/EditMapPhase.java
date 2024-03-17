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
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't load a map here.";
        printInvalidCommandMessage(l_message);
    }

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

    @Override
    public void handleValidateMap(Map p_map) {
        if (isMapValid(p_map)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("The current map is valid!");
        } else {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("The current map isn't valid.");
        }
    }

    @Override
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map) {
        final MapManipulator l_mapManipulator = new MapManipulator();
        l_mapManipulator.processCommand(p_args, p_map);
    }

    @Override
    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) {
        String l_message = "Invalid Command in state" + this.getClass().getSimpleName();
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleEditMap(GameEngine p_ge, Command p_command, Map p_map) {
        String l_message = "Invalid Command you are already in the Edit Map mode";
        printInvalidCommandMessage(l_message);
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
