package game.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
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
                new Airlift(l_country.getD_name(), l_country.getD_name(), l_initiator, 5, l_map);
        l_airlift.execute();
        assertThat(l_country.getD_armyCount(), equalTo(10));
    }
}
