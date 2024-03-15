package game.states;

import static game.pojo.Player.Card.AIRLIFT;
import static game.pojo.Player.Card.BLOCKADE;
import static game.pojo.Player.Card.BOMB;
import static game.pojo.Player.Card.DIPLOMACY;

import game.pojo.Player;

import java.util.Random;

public class AssignResourcesPhase {

    public void assignRandomCard(Player p_player) {

        switch (new Random().nextInt(4)) {
            case 0:
                p_player.addCard(BOMB);
                break;
            case 1:
                p_player.addCard(BLOCKADE);
                break;
            case 2:
                p_player.addCard(AIRLIFT);
                break;
            case 3:
                p_player.addCard(DIPLOMACY);
                break;
            default:
                break;
        }
    }
}
