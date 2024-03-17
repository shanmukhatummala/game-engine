package game.states;

import static game.map.MapHelper.playerOwnsContinent;
import static game.pojo.Player.Card.AIRLIFT;
import static game.pojo.Player.Card.BLOCKADE;
import static game.pojo.Player.Card.BOMB;
import static game.pojo.Player.Card.DIPLOMACY;
import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Player;

import java.util.List;
import java.util.Random;
import java.util.Set;

/** Assigns resources after the completion of each round in the game */
public class AssignResourcesPhase extends PlayPhase {

    public AssignResourcesPhase() {
        GameEngine.d_logEntryBuffer.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }

    /**
     * The method assign army's to each player
     *
     * @param p_map map for the game
     */
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge) {

        final int l_reinforcements_per_player = 5; // Default reinforcements per player

        for (Player l_player : p_map.getD_players()) {
            l_player.setD_reinforcements(l_reinforcements_per_player);

            int l_additionalReinforcements = 0;
            for (Continent l_continent : p_map.getD_continents()) {
                if (playerOwnsContinent(p_map, l_player, l_continent)) {
                    // If the player owns the continent, add the bonus reinforcements
                    l_additionalReinforcements += l_continent.getD_bonus();
                }
            }
            // Set the total reinforcements for the player
            l_player.setD_reinforcements(
                    l_player.getD_reinforcements() + l_additionalReinforcements);
        }

        GameEngine.d_logEntryBuffer.addLogEntry("Reinforcements are assigned");

        p_ge.setGamePhase(new IssueOrderPhase());
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
        p_ge.setGamePhase(new IssueOrderPhase());
    }
}
