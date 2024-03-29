package game.states;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Represents the state where orders are executed. */
public class ExecuteOrderPhase extends PlayPhase {

    /**
     * Constructs an ExecuteOrderPhase object. Adds a log entry to the global LOG_ENTRY_BUFFER
     * indicating the start of this phase.
     */
    public ExecuteOrderPhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    /**
     * Loop over all the players until all the orders are executed
     *
     * @param p_map map for the game
     * @param p_ge The game engine managing the game state.
     * @param p_playersToAssignCard A set of players to assign cards to.
     */
    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> p_playersToAssignCard) {
        List<Player> l_playersLeftToExecuteOrders = new ArrayList<>(p_map.getD_players());
        while (!l_playersLeftToExecuteOrders.isEmpty()) {
            for (Player l_player : p_map.getD_players()) {

                Set<Integer> l_countryIdsBeforeExecution =
                        l_player.getD_countries().stream()
                                .map(Country::getD_id)
                                .collect(Collectors.toSet());

                if (l_player.getD_orderList().isEmpty()) {
                    l_playersLeftToExecuteOrders.remove(l_player);
                    continue;
                }
                l_player.next_order().execute();

                Set<Integer> l_countryIdsAfterExecution =
                        l_player.getD_countries().stream()
                                .map(Country::getD_id)
                                .collect(Collectors.toSet());
                if (!l_countryIdsBeforeExecution.containsAll(l_countryIdsAfterExecution)) {
                    p_playersToAssignCard.add(l_player);
                }
            }
        }
        p_ge.setD_gamePhase(new AssignResourcesPhase());
    }
}
