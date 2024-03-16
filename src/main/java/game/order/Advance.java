package game.order;

import static game.map.MapHelper.isAdjacent;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Random;

/**
 * This class extends from order class and represents the Advance
 *
 * @author Naveen
 */
public class Advance extends Order {

    private final Country destination;
    private final Country source;
    private final Player destinationOwner;
    private final int armyNumber;

    /**
     * Constructor for Advance
     *
     * @param destination Country object representing the destination territory
     * @param destinationOwner Player object represents owner of the destination country
     * @param initiator Player object who initiated the order
     * @param armyNumber Integer representing the number of armies to move
     */
    public Advance(
            Country destination,
            Country source,
            Player destinationOwner,
            Player initiator,
            int armyNumber) {
        super(initiator);
        this.source = source;
        this.destination = destination;
        this.armyNumber = armyNumber;
        this.destinationOwner = destinationOwner;
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
        if (valid()) {

            List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();

            if (l_countriesOfInitiator.contains(destination)) {
                source.setD_armyCount(source.getD_armyCount() - armyNumber);
                destination.setD_armyCount(destination.getD_armyCount() + armyNumber);
            } else {

                if (destinationOwner != null
                        && (getD_initiator()
                                        .getD_negotiatedPlayers()
                                        .contains(destinationOwner.getD_name())
                                || destinationOwner
                                        .getD_negotiatedPlayers()
                                        .contains(getD_initiator().getD_name()))) {
                    System.out.println(
                            "Both players, "
                                    + destinationOwner.getD_name()
                                    + " and "
                                    + getD_initiator().getD_name()
                                    + ", are under negotiation. So, cannot attack.");
                    return;
                }

                source.setD_armyCount(source.getD_armyCount() - armyNumber);
                attackTerritory(destination, armyNumber, destinationOwner, getD_initiator());
            }
        } else {
            System.out.println("Cannot Advance armies to the territory.");
        }
    }

    @Override
    public boolean valid() {
        List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();

        if (!l_countriesOfInitiator.contains(source)) {
            return false;
        }

        if (l_countriesOfInitiator.contains(destination)) {
            return true;
        }

        return isAdjacent(l_countriesOfInitiator, destination);
    }

    /**
     * Simulates an attack on a target territory by simulating battles between attacking and
     * defending armies. This method uses a random number generator to simulate the outcome of
     * battles between the attacking and defending armies. Each attacking army has a 60% chance of
     * killing one defending army, and each defending army has a 70% chance of killing one attacking
     * army. The battle continues until either all attacking or defending armies are eliminated.
     *
     * @param target The target {@link Country} to attack. This country represents the defending
     *     territory.
     * @param armyNumber The number of armies to use in the attack. This represents the attacking
     *     force.
     * @see Country#getD_armyCount()
     * @see Player#getD_countries()
     */
    private void attackTerritory(
            Country target, int armyNumber, Player destinationOwner, Player initiator) {
        Random random = new Random();
        int attackingArmyCount = armyNumber;
        int defendingArmyCount = target.getD_armyCount();

        while (attackingArmyCount > 0 && defendingArmyCount > 0) {
            // Each attacking army has 60% chance of killing one defending army
            for (int i = 0; i < attackingArmyCount; i++) {
                if (random.nextDouble() <= 0.6) {
                    defendingArmyCount--;
                }
            }

            // Each defending army has 70% chance of killing one attacking army
            for (int i = 0; i < defendingArmyCount; i++) {
                if (random.nextDouble() <= 0.7) {
                    attackingArmyCount--;
                }
            }
        }
        // Update territory armies based on the outcome of the attack
        if (defendingArmyCount <= 0) {
            // Attacker wins
            target.setD_armyCount(attackingArmyCount);
            initiator.getD_countries().add(target);
            destinationOwner.getD_countries().remove(target);
        } else {
            // Defender wins
            target.setD_armyCount(defendingArmyCount);
        }
    }
}
