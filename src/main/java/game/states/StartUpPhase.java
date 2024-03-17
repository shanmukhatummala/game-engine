package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.util.List;
import java.util.Set;

/** Represents a phase in the game where players are setting up the game. */
abstract class StartUpPhase implements Phase {

    public static final String RESOURCES_PATH = "src/main/resources/";

    /**
     * Handles the command to issue orders. Displays an invalid command message as issuing orders is
     * not allowed during the start-up phase.
     *
     * @param p_map The current map.
     * @param p_player The player issuing the orders.
     * @param p_command The command to issue orders.
     */
    @Override
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command) {
        String l_message =
                "Invalid Command in state"
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
                "Invalid Command in state"
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
                "Invalid Command in state"
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
                "Invalid Command in state"
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
                "Invalid Command in state"
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
                "Invalid Command in state"
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
                "Invalid Command in state"
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
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Commit orders start phase";
        printInvalidCommandMessage(l_message);
    }
}
