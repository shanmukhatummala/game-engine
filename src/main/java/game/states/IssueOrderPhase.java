package game.states;

<<<<<<< HEAD
=======
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
>>>>>>> e7c673e222ce1f12b449f9f818499006c2153834
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;
import game.util.IssueOrderHelper;

import java.util.List;

public class IssueOrderPhase extends PlayPhase {

    public IssueOrderPhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    @Override
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        p_playersLeftToIssueOrder.remove(p_currentPlayer);
    }

    /**
     * Allow player to create orders
     *
     * @param p_map map for the game
     */
    @Override
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command) {
        IssueOrderHelper.setCommand(p_command);
        IssueOrderHelper.setMap(p_map);
        p_player.issue_order();
    }
}
