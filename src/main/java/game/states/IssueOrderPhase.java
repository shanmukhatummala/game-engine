package game.states;

import static game.map.MapShower.showMap;

import game.GameEngine;
import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Player;
import game.util.IssueOrderHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IssueOrderPhase extends PlayPhase {

    @Override
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        p_playersLeftToIssueOrder.remove(p_currentPlayer);
    }

    /**
     * Loop over all the players until they issue all the orders
     *
     * @param p_map map for the game
     *
     */
    @Override
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command) {
        IssueOrderHelper.setCommand(p_command);
        IssueOrderHelper.setMap(p_map);
        p_player.issue_order();

    }

}
