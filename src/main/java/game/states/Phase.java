package game.states;

import static game.map.MapShower.showMap;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.util.*;

/**
 * The interface defines the structure for state objects in the context of the State design pattern.
 */
public interface Phase {

    /**
     * Handles the command to load a map.
     *
     * @param p_command The command to load a map.
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @param p_basePath Path where the map is located.
     */
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge, String p_basePath);

    /**
     * Handles the command related to game players.
     *
     * @param p_commandList The list of commands related to game players.
     * @param p_map The current map.
     */
    public void handleGamePlayer(List<Command> p_commandList, Map p_map);

    /**
     * Handles the command to edit a map.
     *
     * @param p_ge The game engine managing the game state.
     * @param p_command The command to edit a map.
     * @param p_map The current map.
     * @param p_basePath Path where the map is located.
     */
    public void handleEditMap(GameEngine p_ge, Command p_command, Map p_map, String p_basePath);

    /**
     * Handles the command to edit countries, continents, or neighbors on the map.
     *
     * @param p_args The arguments for editing countries, continents, or neighbors.
     * @param p_map The current map.
     */
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map);

    /**
     * Handles the assignment of countries to players.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @throws Exception If an error occurs during country assignment.
     */
    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) throws Exception;

    /**
     * Handles the assignment of reinforcements to players.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge);

    /**
     * Handles the command to save a map.
     *
     * @param p_command The command to save a map.
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @param p_basePath Path where the map is located.
     */
    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge, String p_basePath);

    /**
     * Handles the command to validate a map.
     *
     * @param p_map The current map.
     */
    public void handleValidateMap(Map p_map);

    /** Handles the "deploy" order. */
    public void handleDeployOrder();

    /** Handles the "advance" order. */
    public void handleAdvanceOrder();

    /** Handles the "use card" order. */
    public void handleUseCardOrder();

    /**
     * Handles the command to commit orders.
     *
     * @param p_playersLeftToIssueOrder The players left to issue orders.
     * @param p_currentPlayer The current player.
     */
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer);

    /**
     * Handles the command to issue orders.
     *
     * @param p_map The current map.
     * @param p_player The player issuing the orders.
     * @param p_command The command to issue orders.
     */
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command);

    /**
     * Handles the execution of orders.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @param l_playersToAssignCard The players to whom cards need to be assigned.
     */
    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> l_playersToAssignCard);

    /**
     * Handles the assignment of cards to players.
     *
     * @param p_players The set of players.
     * @param p_ge The game engine managing the game state.
     */
    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge);

    /**
     * Handles the command to show the map.
     *
     * @param p_map The current map.
     */
    public default void handleShowMap(Map p_map) {
        showMap(p_map);
    }

    /**
     * Prints an invalid command message.
     *
     * @param p_message The message indicating the command is invalid.
     */
    public default void printInvalidCommandMessage(String p_message) {
        System.out.println(p_message);
    }
}
