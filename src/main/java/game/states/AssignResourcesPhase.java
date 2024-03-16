package game.states;

import static game.pojo.Player.Card.AIRLIFT;
import static game.pojo.Player.Card.BLOCKADE;
import static game.pojo.Player.Card.BOMB;
import static game.pojo.Player.Card.DIPLOMACY;

import game.pojo.Player;

import java.util.Random;
import java.util.Set;

/** Assigns resources after the completion of each round in the game */
public class AssignResourcesPhase {

    /**
     * Assigns a random card to all players who are eligible for a card
     *
     * @param p_players players eligible to get a card
     */
    public void assignRandomCard(Set<Player> p_players) {

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
    }
}
