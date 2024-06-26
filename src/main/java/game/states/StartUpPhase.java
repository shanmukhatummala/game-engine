package game.states;

import game.GameEngine;
import game.map.Map;
import game.pojo.Player;

import java.util.List;
import java.util.Set;

/** Represents a phase in the game where players are setting up the game. */
abstract class StartUpPhase implements Phase {

    /**
     * Handles the command to issue orders. Displays an invalid command message as issuing orders is
     * not allowed during the start-up phase.
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
            GameEngine p_ge) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map here";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the execution of orders. Displays an invalid command message as executing orders is
     * not allowed during the start-up phase.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     * @param l_playersToAssignCard The players to whom cards need to be assigned.
     */
    @Override
    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> l_playersToAssignCard) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't execute orders";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the assignment of reinforcements. Displays an invalid command message as assigning
     * reinforcements is not allowed during the start-up phase.
     *
     * @param p_map The current map.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't assign reinforcements";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the assignment of cards. Displays an invalid command message as assigning cards is
     * not allowed during the start-up phase.
     *
     * @param p_players The set of players.
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't assign cards";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the "deploy" order. Displays an invalid command message as deploying armies is not
     * allowed during the start-up phase.
     */
    @Override
    public void handleDeployOrder() {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't deploy armies while in the start phase";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the "advance" order. Displays an invalid command message as advancing armies is not
     * allowed during the start-up phase.
     */
    @Override
    public void handleAdvanceOrder() {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't advance armies while in the start phase";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the "use card" order. Displays an invalid command message as using cards is not
     * allowed during the start-up phase.
     */
    @Override
    public void handleUseCardOrder() {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't use cards while in the start phase";
        printInvalidCommandMessage(l_message);
    }

    /**
     * Handles the "commit" order. Displays an invalid command message as committing orders is not
     * allowed during the start-up phase.
     *
     * @param p_playersLeftToIssueOrder The players left to issue orders.
     * @param p_currentPlayer The current player.
     */
    @Override
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't Commit orders start phase";
        printInvalidCommandMessage(l_message);
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
    public List<Player> handleLoadGame(GameEngine ge, Map p_map, String p_filepath)
            throws Exception {
        String l_message =
                "Invalid Command in state "
                        + this.getClass().getSimpleName()
                        + " you can't Load the game in start phase";
        printInvalidCommandMessage(l_message);
        return null;
    }
}
