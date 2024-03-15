package game.order;

import static game.pojo.Player.Card.BLOCKADE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import java.util.ArrayList;
import java.util.List;

/** Test for BlockadeOrder class */
public class BlockadeTest {

    /** Tests if constructor and getters are implemented properly */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Blockade> l_classUnderTest = Blockade.class;
        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER)
                .areWellImplemented();
    }

    @Test
    public void shouldNotBlockadeWhenPlayerIsNotOwner() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new ArrayList<>(), 10);
        Player l_player = new Player("Player", List.of(l_country1));
        Blockade l_blockadeOrder = new Blockade(l_country1, l_player);

        l_blockadeOrder.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(10));
    }

    @Test
    public void shouldNotBlockadeWhenPlayerHasNoBlockadeCard() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        l_country1.addNeighbor(l_country2.getD_id());
        Player l_player = new Player("Player", List.of(l_country1));
        Blockade l_blockade = new Blockade(l_country2, l_player);

        l_blockade.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }

    @Test
    public void happyPathShouldExecuteBlockadeOrder() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new ArrayList<>(), 10);
        Player l_player = new Player("Player", new ArrayList<>(List.of(l_country1)));
        l_player.addCard(BLOCKADE);
        Blockade l_blockade = new Blockade(l_country1, l_player);

        l_blockade.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(30));
    }
}
