package game.order;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class contains test cases for the Advance_order class.
 */
public class AdvanceOrderTest {

    /**
     * <p>
     *     This test case confirms that executing the advance order should add armies to the destination
     *     subtract from the initiator's country when the destination is adjacent.
     * </p>
     */
    @Test
    public void testExecuteAdvanceOrderDestinationOwnerIsAdjacentToInitiator() {
        Continent l_continent = new Continent();
        Country source = new Country(1, "Country1", l_continent,new ArrayList<>(Collections.singletonList(2)),10);
        Country destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        Player initiator = new Player("Player1", List.of(source,destination));
        Advance_order advanceOrder = new Advance_order(destination, source, initiator, initiator, 5);
        advanceOrder.execute();
        assertEquals(15, destination.getD_armyCount());
        assertEquals(5, source.getD_armyCount());
    }

    /**
     * <p>
     *     This test case confirms that carrying out the advance order
     *    shouldn't alter the army in the target country if it isn't close to the initiator's country.
     * </p>
     */

    @Test
    public void testExecuteAdvanceOrderDestinationOwnerIsNotAdjacentToInitiator() {
        Continent l_continent = new Continent();
        Country source = new Country(1, "Country1", l_continent);
        Country destination = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        Player initiator = new Player("Player1", List.of(source));
        Player destinationPlayer = new Player("player2",List.of(destination));
        int initialDestinationArmyCount=destination.getD_armyCount();
        Advance_order advanceOrder = new Advance_order(destination, source, destinationPlayer, initiator, 5);
        advanceOrder.execute();
        assertEquals(initialDestinationArmyCount, destination.getD_armyCount());
    }

    /**
     * <p>
     *     This test case verifies that an advance order with a null destination should be invalid.
     * </p>
     */

    @Test
    public void testValidMethodWhenDestinationIsNotValid() {
        Continent l_continent = new Continent();
        Player d_player1 = new Player("player1");
        Country source = new Country(1, "Country1", l_continent);
        Country destination = null;
        Player destinationPlayer = null;
        Advance_order advanceOrder = new Advance_order(destination, source, destinationPlayer, d_player1,5);
        assertFalse(advanceOrder.valid());
    }



}
