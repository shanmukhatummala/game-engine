package game.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

<<<<<<< HEAD
=======
import game.map.Map;
>>>>>>> bf7baabe39ca0265aefc6e94600d6a6cdd68bcbe
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.HashSet;
>>>>>>> bf7baabe39ca0265aefc6e94600d6a6cdd68bcbe
import java.util.List;

/**
 * This class contains test cases for the Airlift class.
 *
 * @author Naveen
 */
public class AirliftTest {
    /**
     * Test case to verify the behavior of an Airlift order when the destination territory belongs
     * to the same player as the initiator.
     */
    @Test
    public void TestAirliftWhenDestinationBelongsToSamePlayer() {

        Continent l_continent = new Continent();
        Country l_country = new Country(1, "Country1", l_continent, new HashSet<>(), 10);
        Player l_initiator = new Player("Player", List.of(l_country));
        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.addCountry(l_country);
        l_map.getD_players().add(l_initiator);
        Airlift l_airlift =
                new Airlift(l_initiator, l_country.getD_name(), l_country.getD_name(), 5, l_map);
        l_airlift.execute();
        assertThat(l_country.getD_armyCount(), equalTo(10));
    }

    /**
     * Test case to verify the behavior of an Airlift order when both the destination owner and the
     * initiator are under negotiation.
     */
    @Test
    public void TestAirliftWhenDestinationOwnerAndInitiatorAreUnderNegotiation() {

        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent);
        Country l_destination = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        l_source.addNeighbor(2);
        Player l_initiator = new Player("Player1", List.of(l_source));
        Player l_destinationOwner = new Player("Player2", List.of(l_destination));
        l_initiator.getD_negotiatedPlayers().add("Player2");
<<<<<<< HEAD
        Airlift l_airlift =
                new Airlift(l_initiator, l_destinationOwner, l_destination, l_source, 5);
=======
        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.getD_countries().add(l_source);
        l_map.getD_countries().add(l_destination);
        l_map.getD_players().add(l_initiator);
        l_map.getD_players().add(l_destinationOwner);
        Airlift l_airlift =
                new Airlift(l_initiator, l_destination.getD_name(), l_source.getD_name(), 5, l_map);
>>>>>>> bf7baabe39ca0265aefc6e94600d6a6cdd68bcbe
        l_airlift.execute();

        assertThat(l_destination.getD_armyCount(), equalTo(10));
    }

    /** Test case to verify the outcome of an attack on a territory when the attacker wins. */
    @Test
    void testAttackTerritory_AttackerWins() {
        // Setup: Ensure the attacker has more armies than the defender
        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent, new HashSet<>(), 100);
        Country l_destination = new Country(2, "Country2", l_continent, new HashSet<>(), 5);
        Player l_initiator = new Player("Player1", new ArrayList<>(List.of(l_source)));
        Player l_destinationOwner = new Player("Player2", new ArrayList<>(List.of(l_destination)));
<<<<<<< HEAD
        Airlift l_airlift =
                new Airlift(l_initiator, l_destinationOwner, l_destination, l_source, 70);
=======
        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.getD_countries().add(l_source);
        l_map.getD_countries().add(l_destination);
        l_map.getD_players().add(l_initiator);
        l_map.getD_players().add(l_destinationOwner);
        Airlift l_airlift =
                new Airlift(
                        l_initiator, l_destination.getD_name(), l_source.getD_name(), 70, l_map);
>>>>>>> bf7baabe39ca0265aefc6e94600d6a6cdd68bcbe
        l_airlift.execute();
        assertTrue(l_initiator.getD_countries().contains(l_destination));
    }
}
