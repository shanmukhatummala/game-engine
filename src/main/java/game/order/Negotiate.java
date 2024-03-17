package game.order;

import static game.pojo.Player.Card.DIPLOMACY;

import game.map.Map;
import game.pojo.Player;

import java.util.List;
import java.util.stream.Collectors;

/** Class representing a blockade order */
public class Negotiate extends Order {

    private String d_targetPlayer;

    /**
     * Constructor for the Negotiate order
     *
     * @param p_initiator player who initiated the negotiation
     * @param p_targetPlayer The name of the target player to be negotiated with
     * @param p_map reference to the map
     */
    public Negotiate(Player p_initiator, String p_targetPlayer, Map p_map) {
        super(p_initiator, p_map); // Call the superclass constructor with null destination
        this.d_targetPlayer = p_targetPlayer;
    }

    /**
     * Getter for the target Player to Negotiate with
     *
     * @return target player with whom the initiator want to negotiate with
     */
    public String getD_targetPlayer() {
        return d_targetPlayer;
    }

    /**
     * Executes the Negotiate order and both the initiator and target player can not attack each
     * other till the end of the current turn
     */
    public void execute() {
        if (valid()) {
            getD_initiator().getD_negotiatedPlayers().add(d_targetPlayer);
            getD_initiator().getD_cards().remove(DIPLOMACY);
        }
    }

    /** Checks the validity of the Diplomacy/Negotiate command */
    @Override
    public boolean valid() {

        if (!getD_initiator().getD_cards().contains(DIPLOMACY)) {
            System.out.println(
                    "You don't have a DIPLOMACY card. So, cannot execute Negotiation order.");
            return false;
        }

        // check if the target player is in the players list
        List<Player> l_players = getD_map().getD_players();
        if (l_players.stream()
                .filter(player -> player.getD_name().equals(d_targetPlayer))
                .collect(Collectors.toSet())
                .isEmpty()) {
            System.out.println(
                    "Target player, " + d_targetPlayer + ", not found in the list of players");
            return false;
        }

        return true;
    }
}
