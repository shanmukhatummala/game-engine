package game.order;

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

/** Test for BombOrder class */
public class BlockadeOrderTest {

    /** Tests if constructor and getters are implemented properly */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<BlockadeOrder> l_classUnderTest = BlockadeOrder.class;
        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER)
                .areWellImplemented();
    }

    @Test
    public void shouldNotBlockadeWhenPlayerIsNotOwner() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new ArrayList<>(), 10);
        Player l_player = new Player("Player", List.of(l_country1));
        BlockadeOrder l_blockadeOrder = new BlockadeOrder(l_country1, l_player);

        l_blockadeOrder.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(30));
    }
}
