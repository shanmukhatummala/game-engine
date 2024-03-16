package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.io.BufferedReader;
import java.util.*;

public abstract class PlayPhase implements Phase {
    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you have already loaded the map you are going to play with";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't add or remove player in play mode";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleEditMap(GameEngine ge, Command p_command, Map p_map, BufferedReader p_bufferedReader) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't edit a map in the play mode.";
        printInvalidCommandMessage(message);
    }


    @Override
    public void handleAssignCountries(Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't AssignCountries in play mode";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleSaveMap(String p_fileName, Command p_command, Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't save a map in the play mode";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleValidateMap(Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map in play mode";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleDeployOrder() {}

    @Override
    public void handleAdvanceOrder() {}

    @Override
    public void handleUseCardOrder() {}

    @Override
    public void handleCommit() {}
}
