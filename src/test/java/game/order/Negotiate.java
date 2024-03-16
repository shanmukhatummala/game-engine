package game.order;

import static game.pojo.Player.Card.DIPLOMACY;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;


/** Class representing a blockade order */
public class Negotiate extends Order {
    private Player d_initiator;
    private Player d_targetPlayer;
    Map map;


    public Negotiate(Player p_initiator, Player p_targetPlayer) {
        super(p_initiator);
        this.d_targetPlayer = p_targetPlayer;
    }


    public Player getD_targetPlayer() {
        return d_targetPlayer;
    }


    public void execute() {
        if (valid()) {
            //both players in the set should not attack each other
            getD_initiator().getD_negotiatedPlayers().add(d_targetPlayer.getD_name());

            getD_initiator().getD_cards().remove(DIPLOMACY);
        }
    }

    @Override
    public boolean valid() {

        if (!getD_initiator().getD_cards().contains(DIPLOMACY)) {
            System.out.println("You don't have a DIPLOMACY card. So, cannot execute Negotiation order.");
            return false;
        }

        // check if the target player is in the players list
        String l_targetPlayer = getD_targetPlayer().getD_name();
        List<Player> players = map.getD_players();
        if(!players.contains(l_targetPlayer)) {
            System.out.println("Target player not found in the list of players");
            return false;
        }

        return true;
    }

}