package game.states;

import static game.map.MapShower.showMap;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.io.BufferedReader;
import java.util.*;

public interface Phase {

    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge);

    public void handleGamePlayer(List<Command> p_commandList, Map p_map);

    public void handleEditMap(GameEngine ge, Command p_command, Map p_map);

    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map);

    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) throws Exception;

    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge);

    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge);

    public void handleValidateMap(Map p_map);

    public void handleDeployOrder();

    public void handleAdvanceOrder();

    public void handleUseCardOrder();

    public void handleCommit();

    public void handleIssuingOrders(Map p_map, GameEngine p_ge, BufferedReader p_bufferedReader);

    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> l_playersToAssignCard);

    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge);

    public default void handleShowMap(Map p_map) {
        showMap(p_map);
    }

    public default void printInvalidCommandMessage(String message) {
        System.out.println(message);
    }
}
