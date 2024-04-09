package game.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** The CheaterTest class is responsible for testing the behavior of the Cheater player strategy. */
public class CheaterTest {

    /**
     * This test method verifies the behavior of the Cheater player strategy. It creates a mock map
     * with countries and players and then simulates Cheater behavior, and checks if the behavior is
     * correctly executed.
     */
    @Test
    public void testCheaterBehavior() {

        // Creating a mock map for testing
        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new HashSet<>(), 10);
        Country l_country2 = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        Country l_country3 = new Country(3, "Country3", l_continent, new HashSet<>(), 10);
        Country l_country4 = new Country(4, "Country4", l_continent, new HashSet<>(), 10);
        Country l_country5 = new Country(5, "Country5", l_continent, new HashSet<>(), 10);
        Country l_country6 = new Country(6, "Country6", l_continent, new HashSet<>(), 10);

        Player l_player1 = new Player("Cheater", List.of(l_country1, l_country2, l_country3));
        Player l_player2 = new Player("Enemy", List.of(l_country4, l_country5, l_country6));

        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.getD_countries().add(l_country1);
        l_map.getD_countries().add(l_country2);
        l_map.getD_countries().add(l_country3);
        l_map.getD_countries().add(l_country4);
        l_map.getD_countries().add(l_country5);
        l_map.getD_countries().add(l_country6);

        l_map.getD_players().add(l_player1);
        l_map.getD_players().add(l_player2);

        l_country1.setD_neighborIdList(Set.of(2, 4));
        l_country2.setD_neighborIdList(Set.of(1, 3, 5));
        l_country3.setD_neighborIdList(Set.of(2));
        l_country4.setD_neighborIdList(Set.of(1, 6));
        l_country5.setD_neighborIdList(Set.of(2));
        l_country6.setD_neighborIdList(Set.of(4));

        // Simulating Cheater behavior
        Cheater cheater = new Cheater();
        cheater.createOrder(l_map, l_player1);

        assertEquals(20, l_country1.getD_armyCount());
        assertEquals(20, l_country2.getD_armyCount());
        assertEquals(10, l_country3.getD_armyCount());
        assertEquals(10, l_country4.getD_armyCount());
        assertEquals(10, l_country5.getD_armyCount());
        assertEquals(10, l_country6.getD_armyCount());
    }
}
