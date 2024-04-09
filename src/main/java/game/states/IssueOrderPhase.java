package game.states;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;
import game.util.IssueOrderHelper;

import java.io.*;
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
     * Handles the saving of the current game.
     *
     * @param p_map the current map to save
     * @param p_playersLeftToIssueOrder list of players left to issue order
     * @param p_currentPlayerIndex the current player that saved the game
     * @param p_filepath the path of the file to be saved
     */
    @Override
    public void handleSaveGame(
            Map p_map,
            List<Player> p_playersLeftToIssueOrder,
            Integer p_currentPlayerIndex,
            String p_filepath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(p_filepath))) {
            out.writeObject(p_map);
            out.writeObject(p_playersLeftToIssueOrder);
            out.writeObject(p_currentPlayerIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allow player to create orders. Loop over all the players until they issue all the orders
     *
     * @param p_map map for the game
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
        IssueOrderHelper.setMap(p_map);
        while (!p_playersLeftToIssueOrder.isEmpty()) {
            for (int i = p_currentPlayerIndex; i < p_map.getD_players().size(); i++) {
                System.out.println(
                        "immediatly after the for: current: "
                                + p_currentPlayerIndex
                                + " the i is: "
                                + i);
                if (!p_playersLeftToIssueOrder.contains(p_map.getD_players().get(i))) {
                    p_currentPlayerIndex = (i + 1) % p_map.getD_players().size();
                    continue;
                }
                System.out.println(
                        "after the if it means the list is valid: current: "
                                + p_currentPlayerIndex
                                + " the i is: "
                                + i);
                Player l_player = p_map.getD_players().get(i);
                while (true) {
                    // Generates command according to the strategy
                    Command l_command = l_player.generateCommand();
                    if ("showmap".equals(l_command.getD_commandType())) {
                        this.handleShowMap(p_map);
                    } else if ("savegame".equals(l_command.getD_commandType())) {
                        this.handleSaveGame(
                                p_map,
                                p_playersLeftToIssueOrder,
                                p_currentPlayerIndex,
                                GameEngine.RESOURCES_PATH + l_command.getD_args().get(0));
                    } else if ("commit".equals(l_command.getD_commandType())) {
                        this.handleCommit(p_playersLeftToIssueOrder, l_player);
                        break;
                    } else {
                        // Creates the order and adds it to the list
                        IssueOrderHelper.setCommand(l_command);
                        l_player.issue_order();
                        break;
                    }
                }
                p_currentPlayerIndex = (i + 1) % p_map.getD_players().size();
                System.out.println("the current player index: " + p_currentPlayerIndex);
            }
        }
        System.out.println("Commands will be executed");
        p_ge.setD_gamePhase(new ExecuteOrderPhase());
    }
}
