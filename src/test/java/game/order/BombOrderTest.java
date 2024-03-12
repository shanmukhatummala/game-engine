package game.order;

import static game.pojo.Player.Card.BOMB;

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
public class BombOrderTest {

    /** Tests if constructor and getters are implemented properly */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<BombOrder> l_classUnderTest = BombOrder.class;
        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER)
                .areWellImplemented();
    }

    /**
     * Tests that the target is bombed when the target is adjacent to the initiator's countries and
     * belongs to an opponent and the user has a BOMB card
     */
    @Test
    public void happyPathShouldExecuteBombOrder() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        l_country1.addNeighbor(l_country2.getD_id());
        Player l_player = new Player("Player", List.of(l_country1));
        l_player.addCard(BOMB);
        BombOrder l_bombOrder = new BombOrder(l_country2, l_player);

        l_bombOrder.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(5));
    }

    /** Tests that the target is not bombed when the user doesn't have a BOMB card */
    @Test
    public void shouldNotBombWhenUserDoesNotHaveBombCard() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        l_country1.addNeighbor(l_country2.getD_id());
        Player l_player = new Player("Player", List.of(l_country1));
        BombOrder l_bombOrder = new BombOrder(l_country2, l_player);

        l_bombOrder.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }

    /**
     * Tests that the target is not bombed when the target is not adjacent to the initiator's
     * countries
     */
    @Test
    public void shouldNotBombWhenTargetIsNotAdjacent() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new ArrayList<>(), 10);
        Player l_player = new Player("Player", List.of(l_country1));
        l_player.addCard(BOMB);
        BombOrder l_bombOrder = new BombOrder(l_country2, l_player);

        l_bombOrder.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }

    /** Tests that the target is not bombed when the target belongs to the initiator */
    @Test
    public void shouldNotBombWhenTargetBelongsToSamePlayer() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new ArrayList<>(), 10);
        Player l_player = new Player("Player", List.of(l_country1));
        l_player.addCard(BOMB);
        BombOrder l_bombOrder = new BombOrder(l_country1, l_player);

        l_bombOrder.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(10));
    }
}
