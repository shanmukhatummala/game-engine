package game.order;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

/** This class contains test cases for the Advance class. */
public class AdvanceOrderTest {

    /**
     * This test case confirms that executing the advance order should add armies to the destination
     * subtract from the initiator's country when the destination is adjacent.
     */
    @Test
    public void testExecuteAdvanceOrderDestinationOwnerIsAdjacentToInitiator() {
        Continent l_continent = new Continent();
        Country source =
                new Country(1, "Country1", l_continent, new ArrayList<>(singletonList(2)), 10);
        Country destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        Player initiator = new Player("Player1", List.of(source, destination));
        Advance advanceOrder = new Advance(destination, source, initiator, initiator, 5);
        advanceOrder.execute();
        assertEquals(15, destination.getD_armyCount());
        assertEquals(5, source.getD_armyCount());
    }

    /**
     * This test case confirms that carrying out the advance order shouldn't alter the army in the
     * target country if it isn't close to the initiator's country.
     */
    @Test
    public void testExecuteAdvanceOrderDestinationOwnerIsNotAdjacentToInitiator() {
        Continent l_continent = new Continent();
        Country source = new Country(1, "Country1", l_continent);
        Country destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        Player initiator = new Player("Player1", List.of(source));
        Player destinationPlayer = new Player("player2", List.of(destination));
        int initialDestinationArmyCount = destination.getD_armyCount();
        Advance advanceOrder = new Advance(destination, source, destinationPlayer, initiator, 5);
        advanceOrder.execute();
        assertEquals(initialDestinationArmyCount, destination.getD_armyCount());
    }

    /** This test case verifies that an advance order with a null destination should be invalid. */
    @Test
    public void testValidMethodWhenDestinationIsNotValid() {
        Continent l_continent = new Continent();
        Player d_player1 = new Player("player1");
        Country source = new Country(1, "Country1", l_continent);
        Country destination = null;
        Player destinationPlayer = null;
        Advance advanceOrder = new Advance(destination, source, destinationPlayer, d_player1, 5);
        assertFalse(advanceOrder.valid());
    }

    @Test
    public void testExecuteAdvanceOrderWhenDestinationOwnerAndInitiatorAreNegotiatedPlayers() {
        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent, singletonList(2), 10);
        Country l_destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        Player l_initiator = new Player("Player1", List.of(l_source));
        Player l_destinationOwner = new Player("Player2", List.of(l_destination));
        l_initiator.getD_negotiatedPlayers().add("Player2");
        Advance l_advanceOrder =
                new Advance(l_destination, l_source, l_destinationOwner, l_initiator, 5);
        l_advanceOrder.execute();
        assertEquals(10, l_destination.getD_armyCount());
        assertEquals(10, l_source.getD_armyCount());
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
