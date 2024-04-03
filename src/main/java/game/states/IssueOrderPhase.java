package game.states;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;
import game.util.IssueOrderHelper;

import java.util.ArrayList;
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
     * Allow player to create orders. Loop over all the players until they issue all the orders
     *
     * @param p_map map for the game
     * @param p_ge The game engine managing the game state.
     */
    @Override
    public void handleIssuingOrders(Map p_map, GameEngine p_ge) {
        IssueOrderHelper.setMap(p_map);
        List<Player> l_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                if (!l_playersLeftToIssueOrder.contains(l_player)) {
                    continue;
                }
                while (true) {
                    // Generates command according to the strategy
                    Command l_command = l_player.generateCommand();
                    if ("showmap".equals(l_command.getD_commandType())) {
                        this.handleShowMap(p_map);
                    } else if ("commit".equals(l_command.getD_commandType())) {
                        this.handleCommit(l_playersLeftToIssueOrder, l_player);
                        break;
                    } else {
                        // Creates the order and adds it to the list
                        IssueOrderHelper.setCommand(l_command);
                        l_player.issue_order();
                        break;
                    }
                }
            }
        }
        System.out.println("Commands will be executed");
        p_ge.setD_gamePhase(new ExecuteOrderPhase());
    }
}
