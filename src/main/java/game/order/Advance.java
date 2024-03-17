package game.order;

import static game.map.MapHelper.getCountryByName;
import static game.map.MapHelper.getCountryOwner;
import static game.map.MapHelper.isAdjacent;

import game.GameEngine;
import game.map.Map;
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

    private final String d_destinationName;
    private final String d_sourceName;
    private final int d_armyNumber;

    /**
     * Constructor for Advance
     *
     * @param p_sourceName Country representing the source territory
     * @param p_destinationName Country representing the p_destination territory
     * @param p_initiator Player object who initiated the order
     * @param p_armyNumber Integer representing the number of armies to move
     */
    public Advance(
            String p_destinationName,
            String p_sourceName,
            Player p_initiator,
            int p_armyNumber,
            Map p_map) {
        super(p_initiator, p_map);
        this.d_sourceName = p_sourceName;
        this.d_destinationName = p_destinationName;
        this.d_armyNumber = p_armyNumber;
    }

    /**
     * Executes the deployment of armies to a specified destination country.
     *
     * <p>This method checks if the destination country is owned by the initiator of the order.
     *
     * <p>If the destination is owned by the initiator, it adds the specified number of armies to
     * the destination's army count. If the destination is not adjacent to any of the initiator's
     * countries it first checks is their any negotiation between the players if not it initiates an
     * attack on the destination.
     *
     * @throws IllegalArgumentException if the destination country is not adjacent to any of the
     *     initiator's countries and an attack is attempted.
     */
    @Override
    public void execute() {
        if (valid()) {

            Country l_destination = getCountryByName(getD_map(), d_destinationName);
            Country l_source = getCountryByName(getD_map(), d_sourceName);

            List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();

            if (l_countriesOfInitiator.contains(l_destination)) {
                l_source.setD_armyCount(l_source.getD_armyCount() - d_armyNumber);
                l_destination.setD_armyCount(l_destination.getD_armyCount() + d_armyNumber);
            } else {

                Player d_destinationOwner =
                        getCountryOwner(l_destination, getD_map().getD_players());
                if (d_destinationOwner != null
                        && (getD_initiator()
                                        .getD_negotiatedPlayers()
                                        .contains(d_destinationOwner.getD_name())
                                || d_destinationOwner
                                        .getD_negotiatedPlayers()
                                        .contains(getD_initiator().getD_name()))) {
                    GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                            "Both players, "
                                    + d_destinationOwner.getD_name()
                                    + " and "
                                    + getD_initiator().getD_name()
                                    + ", are under negotiation. So, cannot attack.");
                    return;
                }

                l_source.setD_armyCount(l_source.getD_armyCount() - d_armyNumber);
                attackTerritory(l_destination, d_armyNumber, d_destinationOwner, getD_initiator());
            }
        } else {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Cannot Advance armies to the territory.");
        }
    }

    /**
     * Checks the validity of the connection between the source and destination countries. The
     * connection is considered valid if the source country is associated with the initiator and
     * either directly connected to the destination country or indirectly through adjacency.
     *
     * @return {@code true} if the connection is valid, {@code false} otherwise.
     */
    @Override
    public boolean valid() {

        Country l_destination = getCountryByName(getD_map(), d_destinationName);
        Country l_source = getCountryByName(getD_map(), d_sourceName);

        if (l_destination == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_destinationName
                            + " doesn't exist in the map now. So, cannot advance to this country.");
            return false;
        }

        if (l_source == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_sourceName
                            + " doesn't exist in the map now. So, cannot advance from this country.");
            return false;
        }

        List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();

        if (!l_countriesOfInitiator.contains(l_source)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_sourceName
                            + " doesn't belong to "
                            + getD_initiator().getD_name()
                            + ". So cannot perform advance.");
            return false;
        }

        if (l_countriesOfInitiator.contains(l_destination)) {
            return true;
        }

        return isAdjacent(l_countriesOfInitiator, l_destination);
    }

    /**
     * Simulates an attack on a p_target territory by simulating battles between attacking and
     * defending armies. This method uses a random number generator to simulate the outcome of
     * battles between the attacking and defending armies. Each attacking army has a 60% chance of
     * killing one defending army, and each defending army has a 70% chance of killing one attacking
     * army. The battle continues until either all attacking or defending armies are eliminated.
     *
     * @param p_target The p_target {@link Country} to attack. This country represents the defending
     *     territory.
     * @param p_armyNumber The number of armies to use in the attack. This represents the attacking
     *     force.
     * @param p_destinationOwner Player object represents owner of the destination country
     * @param p_initiator Player object who initiated the order
     * @see Country#getD_armyCount()
     * @see Player#getD_countries()
     */
    private void attackTerritory(
            Country p_target, int p_armyNumber, Player p_destinationOwner, Player p_initiator) {
        Random l_random = new Random();
        int l_attackingArmyCount = p_armyNumber;
        int l_defendingArmyCount = p_target.getD_armyCount();

        while (l_attackingArmyCount > 0 && l_defendingArmyCount > 0) {
            // Each attacking army has 60% chance of killing one defending army
            for (int i = 0; i < l_attackingArmyCount; i++) {
                if (l_random.nextDouble() <= 0.6) {
                    l_defendingArmyCount--;
                }
            }

            // Each defending army has 70% chance of killing one attacking army
            for (int i = 0; i < l_defendingArmyCount; i++) {
                if (l_random.nextDouble() <= 0.7) {
                    l_attackingArmyCount--;
                }
            }
        }
        // Update territory armies based on the outcome of the attack
        if (l_defendingArmyCount <= 0) {
            // Attacker wins
            p_target.setD_armyCount(l_attackingArmyCount);
            p_initiator.getD_countries().add(p_target);
            if (p_destinationOwner != null) {
                p_destinationOwner.getD_countries().remove(p_target);
            }
        } else {
            // Defender wins
            p_target.setD_armyCount(l_defendingArmyCount);
        }
    }
}
