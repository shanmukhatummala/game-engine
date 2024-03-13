package game.order;

import game.pojo.Country;
import game.pojo.Player;

public class Advance_order extends Order {

    private int d_armyCount;

    /**
     * This class extends from order class and represents the move order type of orders
     *
     * @author Naveen
     */
    private Country destination;

    private int armyNumber;

    /**
     * Constructor for AdvanceOrder
     *
     * @param destination Country object representing the destination territory
     * @param initiator Player object who initiated the order
     * @param armyNumber Integer representing the number of armies to move
     */
    public Advance_order(Country destination, Player initiator, int armyNumber) {
        super(initiator);
        this.destination = destination;
        this.armyNumber = armyNumber;
    }

    @Override
    public void execute() {}
}
