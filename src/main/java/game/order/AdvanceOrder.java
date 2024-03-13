package game.order;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Random;

import static game.map.MapHelper.isAdjacent;

public class AdvanceOrder extends Order {

    private int d_armyCount;
    /**
     * This class extends from order class and represents the move order type of orders
     */

    private Country destination;
    private int armyNumber;

    /**
     * Constructor for MoveOrder
     *
     * @param destination Country object representing the destination territory
     * @param initiator   Player object who initiated the order
     * @param armyNumber  Integer representing the number of armies to move
     */
    public AdvanceOrder(Country destination, Player initiator, int armyNumber) {
        super(initiator);
        this.destination = destination;
        this.armyNumber = armyNumber;

    }

    /**
     * Executes the move order, moving armies from the source territory to the destination territory
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
        Random random = new Random();
        int attackingArmies = armyNumber;
        int defendingArmies = target.getD_armyCount();

        while (attackingArmies > 0 && defendingArmies > 0) {
            // Each attacking army has 60% chance of killing one defending army
            for (int i = 0; i < attackingArmies; i++) {
                if (random.nextDouble() <= 0.6) {
                    defendingArmies--;
                }
            }

            // Each defending army has 70% chance of killing one attacking army
            for (int i = 0; i < defendingArmies; i++) {
                if (random.nextDouble() <= 0.7) {
                    attackingArmies--;
                }
            }
        }

        // Update territory armies based on the outcome of the attack
        if (defendingArmies <= 0) {
            // Attacker wins
            target.setD_armyCount(attackingArmies);
            String player= String.valueOf(this.getD_initiator());
            target.setD_initiator(player);
        } else {
            // Defender wins
            target.setD_armyCount(defendingArmies);
        }
    }
}



