package game.order;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

import static game.map.MapHelper.isAdjacent;

public class Advance_order extends Order {
    /**
     * This class extends from order class and represents the Advance_order
     * @param destination represents the destination country where armies are being deployed or moved.
     * @param armyNumber represents number of armies to be deployed or moved to the destination country.
     * @author Naveen
     */
    private Country destination;
    private int armyNumber;

    /**
     * Constructor for Advance_order
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
     * Executes the deployment of armies to a specified destination country.
     * <p>
     * This method checks if the destination country is owned by the initiator of the order.
     * If the destination is owned by the initiator, it adds the specified number of armies to the destination's army count.
     * If the destination is not adjacent to any of the initiator's countries, it initiates an attack on the destination.
     * </p>
     * @throws IllegalArgumentException if the destination country is not adjacent to any of the initiator's countries and an attack is attempted.
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

    private void attackTerritory(Country target, int armyNumber) {

    }

}
