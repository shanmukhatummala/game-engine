package strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

public abstract class PlayerStrategy {
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



