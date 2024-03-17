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

    /**
     * Loop over all the players until they issue all the orders
     *
     * @param p_map map for the game
     * @param p_ge gameEngine reference
     */
    public void handleIssuingOrders(Map p_map, GameEngine p_ge, BufferedReader p_bufferedReader) {
        List<Player> l_playersLeftToIssueOrder = new ArrayList<>(p_map.getD_players());
        while (!l_playersLeftToIssueOrder.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {
                while (true) {
                    try {
                        System.out.println(
                                "Player: " + l_player.getD_name() + ", enter the command: ");
                        String l_commandString = p_bufferedReader.readLine();
                        Command l_command = CommandParser.parse(l_commandString).get(0);
                        if ("showmap".equals(l_command.getCommandType())) {
                            showMap(p_map);
                            continue;
                        } else if ("commit".equals(l_command.getCommandType())) {
                            l_playersLeftToIssueOrder.remove(l_player);
                            break;
                        }

                        IssueOrderHelper.setCommand(l_command);
                        IssueOrderHelper.setMap(p_map);
                        l_player.issue_order();
                        break;
                    } catch (IOException e) {
                        System.out.println(
                                "Error when reading command. Error message: " + e.getMessage());
                    }
                }
                System.out.println(
                        "player: "
                                + l_player.getD_name()
                                + ", reinforcements: "
                                + l_player.getD_reinforcements()
                                + (l_player.getD_cards().isEmpty()
                                        ? ""
                                        : ", cards: " + l_player.getD_cards()));
            }
        }
        System.out.println("Command will be executed.");
        p_ge.setGamePhase(new ExecuteOrderPhase());
    }
}
