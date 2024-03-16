package game.states;

import static game.map.MapShower.showMap;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.util.*;

public interface Phase {

    public void handleLoadMap(Command p_command, Map p_map, GameEngine ge);

    public void handleGamePlayer(List<Command> p_commandList, Map p_map);

    public void handleEditMap(GameEngine ge);

    public void handleAssignCountries(Map p_map, GameEngine ge);

    public void handleSaveMap(String p_fileName, Command p_command, Map p_map);

    public void handleValidateMap(Map p_map);

    public void handleDeployOrder();

    public void handleAdvanceOrder();

    public void handleUseCardOrder();

    public void handleCommit();

    public default void handleShowMap(Map p_map) {
        showMap(p_map);
    }

    public default void printInvalidCommandMessage(String message) {
        System.out.println(message);
    }
}
