package game.states;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Player;

import java.util.Random;
import java.util.Set;

import static game.map.MapHelper.playerOwnsContinent;
import static game.pojo.Player.Card.*;
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

/** Assigns resources after the completion of each round in the game */
public class AssignResourcesPhase extends PlayPhase {

    /**
     * Constructs an AssignResourcesPhase object.
     * Adds a log entry to the global LOG_ENTRY_BUFFER indicating the start of this phase.
     */
    public AssignResourcesPhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    /**
     * The method assign army's to each player
     *
     * @param p_map map for the game
     */
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge) {
        // Minimal number of reinforcement armies for any player
        final int l_MIN_REINFORCEMENTS = 3;

        for (Player l_player : p_map.getD_players()) {
            // Calculate number of l_reinforcements based on owned territories
            int l_territoriesOwned = l_player.getD_countries().size();
            int l_reinforcements = Math.max(l_MIN_REINFORCEMENTS, l_territoriesOwned / 3);

            // Check for continent control bonuses
            for (Continent l_continent : p_map.getD_continents()) {
                if (playerOwnsContinent(p_map, l_player, l_continent)) {
                    l_reinforcements += l_continent.getD_bonus();
                }
            }

            // Set the total l_reinforcements for the player
            l_player.setD_reinforcements(l_reinforcements);
        }

        System.out.println("Reinforcements are assigned");

        GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Reinforcements are assigned");

        p_ge.setD_gamePhase(new IssueOrderPhase());
    }

    /**
     * Assigns a random card to all players who are eligible for a card
     *
     * @param p_players players eligible to get a card
     */
    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge) {

        p_players.forEach(
                l_player -> {
                    switch (new Random().nextInt(4)) {
                        case 0:
                            l_player.addCard(BOMB);
                            break;
                        case 1:
                            l_player.addCard(BLOCKADE);
                            break;
                        case 2:
                            l_player.addCard(AIRLIFT);
                            break;
                        case 3:
                            l_player.addCard(DIPLOMACY);
                            break;
                        default:
                            break;
                    }
                });
        p_ge.setD_gamePhase(new IssueOrderPhase());
    }
}
