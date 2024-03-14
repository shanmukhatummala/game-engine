package game.order;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Random;

import static game.map.MapHelper.isAdjacent;

public class Advance_order extends Order {
    /**
     * This class extends from order class and represents the Advance_order
     *
     * @param destination represents the destination country where armies are being deployed or
     *     moved.
     * @param armyNumber represents number of armies to be deployed or moved to the destination
     *     country.
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
     *
     * <p>This method checks if the destination country is owned by the initiator of the order. If
     * the destination is owned by the initiator, it adds the specified number of armies to the
     * destination's army count. If the destination is not adjacent to any of the initiator's
     * countries, it initiates an attack on the destination.
     *
     * @throws IllegalArgumentException if the destination country is not adjacent to any of the
     *     initiator's countries and an attack is attempted.
     */
    @Override
    public void execute() {
        List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();
        if (destination.getD_name().equals(this.getD_initiator())) {
            int armies = destination.getD_armyCount() + armyNumber;
            System.out.println(armies);

        } else {
            if (isAdjacent(l_countriesOfInitiator, destination)) {
                attackTerritory(destination, armyNumber);
            }
            else
            {
                System.out.println("Cannot Advance armies to the territory.");
            }
        }
    }

    /**
     * An attack on a target territory by simulating battles between attacking and defending armies.
     *
     * <p>This method uses a random number generator to simulate the outcome of battles between the
     * attacking and defending armies. Each attacking army has a 60% chance of killing one defending
     * army, and each defending army has a 70% chance of killing one attacking army. The battle
     * continues until either all attacking or defending armies are eliminated.
     *
     * @param target The target {@link Country} to attack.
     * @param armyNumber The number of armies to use in the attack.
     */
    private void attackTerritory(Country target, int armyNumber) {
        Random random = new Random();
        int Attacking_Armies = armyNumber;
        int Defending_Armies = target.getD_armyCount();

        while (Attacking_Armies > 0 && Defending_Armies > 0) {
            // Each attacking army has 60% chance of killing one defending army
            for (int i = 0; i < Attacking_Armies; i++) {
                if (random.nextDouble() <= 0.6) {
                    Defending_Armies--;
                }
            }

            // Each defending army has 70% chance of killing one attacking army
            for (int i = 0; i < Defending_Armies; i++) {
                if (random.nextDouble() <= 0.7) {
                    Attacking_Armies--;
                }
            }
        }
        // Update territory armies based on the outcome of the attack
        if (Defending_Armies <= 0) {
            // Attacker wins
            target.setD_armyCount(Attacking_Armies);


        } else {
            // Defender wins
            target.setD_armyCount(Defending_Armies);
        }
    }




}
