package game.order;

import static game.pojo.Player.Card.BLOCKADE;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** Test for Blockade class */
public class BlockadeTest {

    /** Tests if constructor and getters are implemented properly */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Blockade> l_classUnderTest = Blockade.class;
        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER)
                .areWellImplemented();
    }

    /** Tests that the target is not blockaded when the target is not owned by the initiator */
    @Test
    public void shouldNotBlockadeWhenPlayerIsNotOwner() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new HashSet<>(), 10);
        Player l_player = new Player("Player");
        l_player.addCard(Player.Card.BLOCKADE);
        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.getD_countries().add(l_country1);
        l_map.getD_players().add(l_player);
        Blockade l_blockadeOrder = new Blockade(l_country1.getD_name(), l_player, l_map);

        l_blockadeOrder.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(10));
    }

    /** Tests that the target is not blockaded when the user doesn't have a BLOCKADE card */
    @Test
    public void shouldNotBlockadeWhenPlayerHasNoBlockadeCard() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        l_country1.addNeighbor(l_country2.getD_id());
        Player l_player = new Player("Player", List.of(l_country1));
        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.getD_countries().add(l_country1);
        l_map.getD_countries().add(l_country2);
        l_map.getD_players().add(l_player);
        Blockade l_blockade = new Blockade(l_country2.getD_name(), l_player, l_map);

        l_blockade.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }

    /**
     * Tests that the target is Blockaded when the target is owned by the initiator and the user has
     * a BLOCKADE card
     */
    @Test
    public void ShouldExecuteBlockadeOrderWhenPlayerIsOwnerAndHasBlockadeCard() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new HashSet<>(), 10);
        Player l_player = new Player("Player", new ArrayList<>(List.of(l_country1)));
        l_player.addCard(BLOCKADE);
        Map l_map = new Map();
        l_map.getD_continents().add(l_continent);
        l_map.getD_countries().add(l_country1);
        l_map.getD_players().add(l_player);
        Blockade l_blockade = new Blockade(l_country1.getD_name(), l_player, l_map);

        l_blockade.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(30));
    }
}
