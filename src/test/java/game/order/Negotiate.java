package game.order;

import static game.pojo.Player.Card.DIPLOMACY;
import game.map.Map;
import game.pojo.Player;

import java.util.List;


/** Class representing a blockade order */
public class Negotiate extends Order {
    private Player d_initiator;
    private Player d_targetPlayer;
    private Map map;

    /**
     * Constructor for the Negotiate order
     *
     * @param p_initiator    player who initiated the negotiation
     * @param p_targetPlayer The name of the target player to be negotiated with
     * @param p_map reference to the map
     */
    public Negotiate(Player p_initiator, Player p_targetPlayer, Map p_map) {
        super(p_initiator);
        this.d_targetPlayer = p_targetPlayer;
        this.map = p_map;
    }

    /**
     * Getter for the target Player to Negotiate with
     *
     * @return target player with whom the initiator want to negotiate with
     */
    public Player getD_targetPlayer() {
        return d_targetPlayer;
    }

    /**
     * Executes the Negotiate order and both the initiator and target player can not attack each other
     * till the end of the current turn
     */
    public void execute() {
        if (valid()) {
            //both players in the set should not attack each other
            getD_initiator().getD_negotiatedPlayers().add(d_targetPlayer.getD_name());
            getD_initiator().getD_cards().remove(DIPLOMACY);
        }
    }

    /** Checks the validity of the Diplomacy/Negotiate command */
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