package game.strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.io.Serializable;

/**
 * Abstract class representing the different behaviors of the Player class. The strategy is
 * responsible for creating the commands to create the orders that are inserted in the stack of
 * orders.
 *
 * <p>Each strategy has to implement the Singleton design pattern. This is to ensure that each
 * player that are equal and have the same strategy will be equal and have the same hash
 */
public abstract class PlayerStrategy implements Serializable {
    /**
     * Creates a command to create an order based on the current behavior
     *
     * @param p_map the map being played
     * @param p_player the player using the strategy
     * @return Command used to create the order
     */
    public abstract Command createOrder(Map p_map, Player p_player);

    /**
     * Two strategies are equal if their class is the same.
     *
     * @param p_otherObject the object to which this object is compared
     * @return true if the strategies are the same.
     */
    @Override
    public boolean equals(Object p_otherObject) {
        return this.getClass() == p_otherObject.getClass();
    }
}
