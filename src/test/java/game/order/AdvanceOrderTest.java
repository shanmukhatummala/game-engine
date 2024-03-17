package game.order;

import static org.junit.Assert.*;

import static java.util.Collections.singletonList;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class contains test cases for the Advance class.
 *
 * @author Naveen
 */
public class AdvanceOrderTest {

    /**
     * This test case confirms that executing the advance order should add armies to the destination
     * subtract from the initiator's country when the destination is adjacent.
     */
    @Test
    public void testExecuteAdvanceOrderDestinationOwnerIsAdjacentToInitiator() {
        Continent l_continent = new Continent();
        Country l_source =
                new Country(1, "Country1", l_continent, new HashSet<>(singletonList(2)), 10);
        Country l_destination = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        Player l_initiator = new Player("Player1", List.of(l_source, l_destination));
        Advance l_advanceOrder = new Advance(l_destination, l_source, l_initiator, l_initiator, 5);
        l_advanceOrder.execute();
        assertEquals(15, l_destination.getD_armyCount());
        assertEquals(5, l_source.getD_armyCount());
    }

    /**
     * This test case confirms that carrying out the advance order shouldn't alter the army in the
     * target country if it isn't close to the initiator's country.
     */
    @Test
    public void testExecuteAdvanceOrderDestinationOwnerIsNotAdjacentToInitiator() {
        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent);
        Country l_destination = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        Player l_initiator = new Player("Player1", List.of(l_source));
        Player l_destinationOwner = new Player("player2", List.of(l_destination));
        int l_initialDestinationArmyCount = l_destination.getD_armyCount();
        Advance l_advanceOrder =
                new Advance(l_destination, l_source, l_destinationOwner, l_initiator, 5);
        l_advanceOrder.execute();
        assertEquals(l_initialDestinationArmyCount, l_destination.getD_armyCount());
    }

    /** This test case verifies that an advance order with a null destination should be invalid. */
    @Test
    public void testValidMethodWhenDestinationIsNotValid() {
        Continent l_continent = new Continent();
        Player l_initiator = new Player("player1");
        Country source = new Country(1, "Country1", l_continent);
        Country destination = null;
        Player l_destinationOwner = null;
        Advance l_advanceOrder =
                new Advance(destination, source, l_destinationOwner, l_initiator, 5);
        assertFalse(l_advanceOrder.valid());
    }

    /**
     * Test case to verify the execution of an Advance order when the destination owner and
     * initiator are negotiated players.
     */
    @Test
    public void testExecuteAdvanceOrderWhenDestinationOwnerAndInitiatorAreNegotiatedPlayers() {
        Continent l_continent = new Continent();
        Country l_source =
                new Country(1, "Country1", l_continent, new HashSet<>(singletonList(2)), 10);
        Country l_destination =
                new Country(2, "Country2", l_continent, new HashSet<>(new ArrayList<>()), 10);
        Player l_initiator = new Player("Player1", List.of(l_source));
        Player l_destinationOwner = new Player("Player2", List.of(l_destination));
        l_initiator.getD_negotiatedPlayers().add("Player2");
        Advance l_advanceOrder =
                new Advance(l_destination, l_source, l_destinationOwner, l_initiator, 5);
        l_advanceOrder.execute();
        assertEquals(10, l_destination.getD_armyCount());
        assertEquals(10, l_source.getD_armyCount());
    }

    /** Test case to verify the outcome of an attack on a territory when the attacker wins. */
    @Test
    void testAttackTerritory_AttackerWins() {
        Continent l_continent = new Continent();
        Country l_source = new Country(1, "Country1", l_continent, new HashSet<>(), 100);
        Country l_destination = new Country(2, "Country2", l_continent, new HashSet<>(), 5);
        Player l_initiator = new Player("Player1", new ArrayList<>(List.of(l_source)));
        Player l_destinationOwner = new Player("Player2", new ArrayList<>(List.of(l_destination)));
        Airlift l_airlift =
                new Airlift(l_initiator, l_destinationOwner, l_destination, l_source, 70);
        l_airlift.execute();
        assertTrue(l_initiator.getD_countries().contains(l_destination));
    }
}
