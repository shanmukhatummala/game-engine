package game.order;

import game.pojo.Player;

/** and abstract class to represent the type of order a player can give */
public abstract class Order {
    private Player d_initiator;

    /**
     * The constructor of Order class that initialize the attribute
     *
     * @param p_initiator Player object who initiated the order
     */
    public Order(Player p_initiator) {
        this.d_initiator = p_initiator;
    }

    /**
     * Getter for the initiator attribute
     *
     * @return the player who initiated the order
     */
    public Player getD_initiator() {
        return d_initiator;
    }

    /** this method is responsible for the behavior of the orders */
    public abstract void execute();
}
