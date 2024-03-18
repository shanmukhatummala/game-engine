package game.order;

import game.map.Map;
import game.pojo.Player;

/** and abstract class to represent the type of order a player can give */
public abstract class Order {
    private final Player d_initiator;
    private final Map d_map;

    /**
     * The constructor of Order class that initialize the attribute
     *
     * @param p_initiator Player object who initiated the order
     * @param p_map Map where the game is played
     */
    public Order(Player p_initiator, Map p_map) {
        this.d_initiator = p_initiator;
        this.d_map = p_map;
    }

    /**
     * Getter for the initiator attribute
     *
     * @return the player who initiated the order
     */
    public Player getD_initiator() {
        return d_initiator;
    }

    /**
     * Getter for the map attribute
     *
     * @return the map
     */
    public Map getD_map() {
        return d_map;
    }

    /** this method is responsible for the behavior of the orders */
    public abstract void execute();

    /**
     * Responsible for making sure the order can be executed before executing it
     *
     * @return true if the order can be executed
     */
    public abstract boolean valid();
}
