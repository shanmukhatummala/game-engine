package game.order;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

import static game.map.MapHelper.isAdjacent;

public class Advance_order extends Order {

    private int d_armyCount;

    /**
     * This class extends from order class and represents the move order type of orders
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

    /**
     * Executes the Advance order, moving armies from the source territory to the destination territory
     */

    @Override
    public void execute() {
        List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();
        if (destination.getD_name().equals(this.getD_initiator())) {
            int armies = destination.getD_armyCount() + armyNumber;
            System.out.println(armies);

        } else {
            if (!isAdjacent(l_countriesOfInitiator, destination)) {
                attackTerritory(destination, armyNumber);
            }
        }
    }

    private void attackTerritory(Country target, int armyNumber) {}

}
