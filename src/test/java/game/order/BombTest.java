package game.order;

import static game.pojo.Player.Card.BOMB;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import java.util.HashSet;
import java.util.List;

/** Test for Bomb class */
public class BombTest {

    private Map d_map;

    /** Creates a brand new map before each test */
    @BeforeEach
    public void setUp() {
        d_map = new Map();
    }

    /** Tests if constructor and getters are implemented properly */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Bomb> l_classUnderTest = Bomb.class;
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
        Country l_country2 = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        l_country1.addNeighbor(l_country2.getD_id());
        Player l_player = new Player("Player", List.of(l_country1));
        l_player.addCard(BOMB);
        d_map.getD_continents().add(l_continent);
        d_map.getD_countries().add(l_country1);
        d_map.getD_countries().add(l_country2);
        d_map.getD_players().add(l_player);
        Bomb l_bomb = new Bomb(l_country2.getD_name(), l_player, d_map);

        l_bomb.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(5));
    }

    /** Tests that the target is not bombed when the user doesn't have a BOMB card */
    @Test
    public void shouldNotBombWhenUserDoesNotHaveBombCard() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        l_country1.addNeighbor(l_country2.getD_id());
        Player l_player = new Player("Player", List.of(l_country1));
        d_map.getD_continents().add(l_continent);
        d_map.getD_countries().add(l_country1);
        d_map.getD_countries().add(l_country2);
        d_map.getD_players().add(l_player);
        Bomb l_bomb = new Bomb(l_country2.getD_name(), l_player, d_map);

        l_bomb.execute();

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
        Country l_country2 = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        Player l_player = new Player("Player", List.of(l_country1));
        l_player.addCard(BOMB);
        d_map.getD_continents().add(l_continent);
        d_map.getD_countries().add(l_country1);
        d_map.getD_countries().add(l_country2);
        d_map.getD_players().add(l_player);
        Bomb l_bomb = new Bomb(l_country2.getD_name(), l_player, d_map);

        l_bomb.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }

    /** Tests that the target is not bombed when the target belongs to the initiator */
    @Test
    public void shouldNotBombWhenTargetBelongsToSamePlayer() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent, new HashSet<>(), 10);
        Player l_player = new Player("Player", List.of(l_country1));
        l_player.addCard(BOMB);
        d_map.getD_continents().add(l_continent);
        d_map.getD_countries().add(l_country1);
        d_map.getD_players().add(l_player);
        Bomb l_bomb = new Bomb(l_country1.getD_name(), l_player, d_map);

        l_bomb.execute();

        assertThat(l_country1.getD_armyCount(), equalTo(10));
    }

    /** Tests that the bomb order doesn't execute when there's a negotiation order */
    @Test
    public void shouldNotBombWhenTargetOwnerAndInitiatorAreUnderNegotiation() {

        Continent l_continent = new Continent();
        Country l_country1 = new Country(1, "Country1", l_continent);
        Country l_country2 = new Country(2, "Country2", l_continent, new HashSet<>(), 10);
        l_country1.addNeighbor(2);
        Player l_initiator = new Player("Player1", List.of(l_country1));
        Player l_targetOwner = new Player("Player2", List.of(l_country2));
        l_initiator.getD_negotiatedPlayers().add("Player2");
        l_initiator.addCard(BOMB);
        d_map.getD_continents().add(l_continent);
        d_map.getD_countries().add(l_country1);
        d_map.getD_countries().add(l_country2);
        d_map.getD_players().add(l_initiator);
        d_map.getD_players().add(l_targetOwner);
        Bomb l_bomb = new Bomb(l_country2.getD_name(), l_initiator, d_map);

        l_bomb.execute();

        assertThat(l_country2.getD_armyCount(), equalTo(10));
    }
}
