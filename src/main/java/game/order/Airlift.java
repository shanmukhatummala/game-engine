package game.order;

import static game.map.MapHelper.*;
import static game.pojo.Player.Card.AIRLIFT;

import game.GameEngine;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

/**
 * This class extends from order class and represents the Airlift
 *
 * @author Naveen
 */
public class Airlift extends Order {

    private final String d_destinationName;
    private final String d_sourceName;
    private final int d_armyNumber;

    /**
     * Constructor for Airlift
     *
     * @param p_sourceName Country representing the source territory
     * @param p_destinationName Country representing the p_destination territory
     * @param p_initiator Player object who initiated the order
     * @param p_armyNumber Integer representing the number of armies to move
     * @param p_map The map where the game is played
     */
    public Airlift(
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
     * the destination's army count.
     */
    @Override
    public void execute() {
        if (valid()) {
            Country l_destination = getCountryByName(getD_map(), d_destinationName);
            Country l_source = getCountryByName(getD_map(), d_sourceName);

            l_source.setD_armyCount(l_source.getD_armyCount() - d_armyNumber);
            l_destination.setD_armyCount(l_destination.getD_armyCount() + d_armyNumber);
            getD_initiator().getD_cards().remove(AIRLIFT);
        } else {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Cannot Airlift armies to the territory.");
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

        if (!getD_initiator().getD_cards().contains(AIRLIFT)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    getD_initiator().getD_name()
                            + " doesn't have an AIRLIFT card. So, cannot execute airlift order.");
            return false;
        }

        if (l_destination == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_destinationName
                            + " doesn't exist in the map now. So, cannot Airlift to this country.");
            return false;
        }

        if (l_source == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_sourceName
                            + " doesn't exist in the map now. So, cannot Airlift from this country.");
            return false;
        }

        List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();

        if (!(l_countriesOfInitiator.contains(l_source)
                && l_countriesOfInitiator.contains(l_destination))) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_sourceName
                            + " and "
                            + d_destinationName
                            + " doesn't belong to "
                            + getD_initiator().getD_name()
                            + ". So cannot perform Airlift.");
            return false;
        }

        if (l_source.getD_armyCount() < d_armyNumber) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_sourceName + " doesn't have enough armies (" + d_armyNumber + ")");
            return false;
        }

        return true;
    }
}
