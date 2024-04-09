package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.util.*;

/** Represents a phase in the game where players are actively playing. */
public abstract class PlayPhase implements Phase {

    /**
     * Handles the command to load a map. Displays an invalid command message as loading a map is
     * not allowed during gameplay.
     *
     * @param p_command The command to load a map.
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge, String p_basePath) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you have already loaded the map you are going to play with";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the command to add or remove players from the game. Displays an invalid command
     * message as player modification is not allowed during gameplay.
     *
     * @param p_commandList The list of commands to add or remove players.
     * @param p_map The current map.
     */
    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't add or remove player in play mode";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the command to edit the map. Displays an invalid command message as map editing is
     * not allowed during gameplay.
     *
     * @param p_ge The game engine managing the game state.
     * @param p_command The command to edit the map.
     * @param p_map The current map.
     */
    @Override
    public void handleEditMap(GameEngine p_ge, Command p_command, Map p_map, String p_basePath) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't edit a map in the play mode.";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the command to assign countries to players. Displays an invalid command message as
     * country assignment is not allowed during gameplay.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleCountriesAssignment(Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't AssignCountries in play mode";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the command to save the map. Displays an invalid command message as map saving is not
     * allowed during gameplay.
     *
     * @param p_command The command to save the map.
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleSaveMapCommand(
            Command p_command, Map p_map, GameEngine p_ge, String p_basePath) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't save a map in the play mode";
        printInvalidCommandMessage(message);
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
                        + " you can't save a map in the play mode";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the command to validate the map. Displays an invalid command message as map
     * validation is not allowed during gameplay.
     *
     * @param p_map The current map.
     */
    @Override
    public void handleValidateMap(Map p_map) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map in play mode";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the command to edit countries, continents, or neighbors of the map. Displays an
     * invalid command message as map editing is not allowed during gameplay.
     *
     * @param p_commandList The List of Command objects
     * @param p_map The current map.
     */
    @Override
    public void handleEditCountriesOrContinentOrNeighbor(List<Command> p_commandList, Map p_map) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't edit map in the play phase";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the command to issue orders. This method is abstract and must be implemented by
     * subclasses.
     *
     * @param p_map The current map.
     * @param p_playersLeftToIssueOrder the list of players that haven't committed yet.
     * @param p_currentPlayerIndex index of the player currently issuing orders.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleIssuingOrders(
            Map p_map,
            List<Player> p_playersLeftToIssueOrder,
            Integer p_currentPlayerIndex,
            GameEngine p_ge) {}

    /**
     * Handles the execution of orders. This method is abstract and must be implemented by
     * subclasses.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @param l_playersToAssignCard The set of players to whom cards will be assigned after
     *     executing orders.
     */
    @Override
    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> l_playersToAssignCard) {}

    /**
     * Handles the assignment of reinforcements. This method is abstract and must be implemented by
     * subclasses.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge) {}

    /**
     * Handles the assignment of cards. This method is abstract and must be implemented by
     * subclasses.
     *
     * @param p_players The set of players.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge) {}

    /** Handles the deploy order. This method is abstract and must be implemented by subclasses. */
    @Override
    public void handleDeployOrder() {}

    /** Handles the advance order. This method is abstract and must be implemented by subclasses. */
    @Override
    public void handleAdvanceOrder() {}

    /**
     * Handles the use card order. This method is abstract and must be implemented by subclasses.
     */
    @Override
    public void handleUseCardOrder() {}

    /**
     * Handles the commit order. Displays an invalid command message as committing orders is not
     * allowed during gameplay.
     *
     * @param p_playersLeftToIssueOrder The list of players left to issue orders.
     * @param p_currentPlayer The current player.
     */
    @Override
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        String message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't commit here.";
        printInvalidCommandMessage(message);
    }

    /**
     * Handles the saving of the game state
     *
     * @param p_map the game map
     * @param p_playersLeftToIssueOrder list of players left to issue order
     * @param p_currentPlayerIndex index of the current player
     * @param p_filepath path to the file that saves game state
     */
    @Override
    public void handleSaveGame(
            Map p_map,
            List<Player> p_playersLeftToIssueOrder,
            Integer p_currentPlayerIndex,
            String p_filepath) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't save the game in start phase";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the loading of the game state
     *
     * @param ge the reference to GameEngine object
     * @param p_map the game map
     * @param p_filepath path to the file to load game state
     * @return list of players
     */
    @Override
    public List<Player> handleLoadGame(GameEngine ge, Map p_map, String p_filepath) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't Load the game in start phase";
        printInvalidCommandMessage(l_message);
        return null;
    }

    /**
     * Handles the tournament command. Displays an invalid command message if the command is not
     * allowed in the phase
     *
     * @param p_commandList The list of commands related to the tournament
     */
    @Override
    public void handleTournament(List<Command> p_commandList) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't start a tournament in this phase";
        printInvalidCommandMessage(l_message);
    }
}
