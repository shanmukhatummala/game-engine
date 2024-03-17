package game.states;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;
import game.util.IssueOrderHelper;

import java.util.List;

/** Represents the state where players issue orders. */
public class IssueOrderPhase extends PlayPhase {

    /**
     * Constructs an IssueOrderPhase object. Adds a log entry to the global LOG_ENTRY_BUFFER
     * indicating the start of this phase.
     */
    public IssueOrderPhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    /**
     * Handles the commitment of players who have issued orders.
     *
     * @param p_playersLeftToIssueOrder The list of players left to issue orders.
     * @param p_currentPlayer The player who has committed orders.
     */
    @Override
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        p_playersLeftToIssueOrder.remove(p_currentPlayer);
    }

    /**
     * Allow player to create orders
     *
     * @param p_map map for the game
     * @param p_player The player issuing orders.
     * @param p_command The command representing the issued order.
     */
    @Override
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command) {
        IssueOrderHelper.setCommand(p_command);
        IssueOrderHelper.setMap(p_map);
        p_player.issue_order();
    }
}
