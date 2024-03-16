package game.states;

import static game.map.MapShower.showMap;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.util.*;

public interface Phase {

    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge);

    public void handleGamePlayer(List<Command> p_commandList, Map p_map);

    public void handleEditMap(GameEngine ge, Command p_command, Map p_map);
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map);

    public void handleAssignCountries(Map p_map, GameEngine p_ge);

    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge);

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
