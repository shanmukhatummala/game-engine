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

public class AirliftTest {

    @Test
    public void TestAirliftWhenDestinationBelongsToSamePlayer() {

        Continent l_continent = new Continent();
        Country l_country = new Country(1, "Country1", l_continent, new ArrayList<>(), 10);
        Player l_initiator = new Player("Player", List.of(l_country));
        Airlift airlift = new Airlift(l_initiator, l_initiator, l_country, l_country, 5);
        airlift.execute();
        assertThat(l_country.getD_armyCount(), equalTo(10));
    }
    @Test
    public void TestAirliftWhenDestinationOwnerAndInitiatorAreUnderNegotiation() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        l_country1.addNeighbor(2);
        Player l_initiator = new Player("Player1", List.of(l_country1));
        Player l_targetOwner = new Player("Player2", List.of(l_country2));
        l_initiator.getD_negotiatedPlayers().add("Player2");
        Airlift airlift = new Airlift(l_initiator, l_targetOwner, l_country2, l_country1, 5);
        airlift.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }

    @Test
    void testAttackTerritory_AttackerWins() {
        // Setup: Ensure the attacker has more armies than the defender
        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent,new ArrayList<>(), 100);
        Country l_country2 = new Country(2, "Country2", l_continent, new ArrayList<>(), 5);
        Player l_initiator = new Player("Player1", new ArrayList<>(List.of(l_country1)));
        Player l_targetOwner = new Player("Player2",new ArrayList<>(List.of(l_country2)));
        Airlift airlift  = new Airlift(l_initiator, l_targetOwner, l_country2, l_country1, 70);
        airlift.execute();
        assertTrue(l_initiator.getD_countries().contains(l_country2));
    }

}
