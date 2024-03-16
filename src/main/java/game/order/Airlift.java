package game.order;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Random;

public class Airlift extends Order{

    /**
     * The constructor of Order class that initialize the attribute
     *
     * @param p_initiator Player object who initiated the order
     */
    private Player destinationOwner;
    private final Country destination;
    private final Country source;
    private final int armyNumber;
    public Airlift(Player p_initiator, Player destinationOwner,Country destination, Country source, int armyNumber) {
        super(p_initiator);
        this.destination = destination;
        this.source = source;
        this.armyNumber = armyNumber;
        this.destinationOwner = destinationOwner;
    }

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
        }
        else {
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
        return false;
    }
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

