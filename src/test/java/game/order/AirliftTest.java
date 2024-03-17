package game.order;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * This class contains test cases for the Airlift class.
 *
 * @author Naveen
 */
public class AirliftTest {
    /**
     * Test case to verify the behavior of an Airlift order when the destination
     * territory belongs to the same player as the initiator.
     */
    @Test
    public void TestAirliftWhenDestinationBelongsToSamePlayer() {

        Continent l_continent = new Continent();
        Country l_country = new Country(1, "Country1", l_continent, new ArrayList<>(), 10);
        Player l_initiator = new Player("Player", List.of(l_country));
        Airlift l_airlift = new Airlift(l_initiator, l_initiator, l_country, l_country, 5);
        l_airlift.execute();
        assertThat(l_country.getD_armyCount(), equalTo(10));
    }

    /**
     * Test case to verify the behavior of an Airlift order when both the destination owner and the initiator are under negotiation.
     */

    @Test
    public void TestAirliftWhenDestinationOwnerAndInitiatorAreUnderNegotiation() {

        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent);
        Country l_destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        l_source.addNeighbor(2);
        Player l_initiator = new Player("Player1", List.of(l_source));
        Player l_destinationOwner = new Player("Player2", List.of(l_destination));
        l_initiator.getD_negotiatedPlayers().add("Player2");
        Airlift l_airlift = new Airlift(l_initiator, l_destinationOwner, l_destination, l_source, 5);
        l_airlift.execute();

        assertThat(l_destination.getD_armyCount(), equalTo(10));
    }

    /**
     * Test case to verify the outcome of an attack on a territory when the attacker wins.
     */
    @Test
    void testAttackTerritory_AttackerWins() {
        // Setup: Ensure the attacker has more armies than the defender
        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent, new ArrayList<>(), 100);
        Country l_destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 5);
        Player l_initiator = new Player("Player1", new ArrayList<>(List.of(l_source)));
        Player l_destinationOwner = new Player("Player2", new ArrayList<>(List.of(l_destination)));
        Airlift l_airlift = new Airlift(l_initiator, l_destinationOwner, l_destination, l_source, 70);
        l_airlift.execute();
        assertTrue(l_initiator.getD_countries().contains(l_destination));
    }
}
