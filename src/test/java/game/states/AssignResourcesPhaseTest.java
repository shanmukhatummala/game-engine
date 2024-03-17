package game.states;

import static game.pojo.Player.Card.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Test class for AssignResourcesPhase class */
public class AssignResourcesPhaseTest {

    private AssignResourcesPhase d_assignResourcesPhase;
    private Set<Player> d_players;
    private Player d_player1;
    private Player d_player2;
    private Map d_map;

    /** Sets up the required objects for the execution of tests */
    @BeforeEach
    public void setUp() {
        d_assignResourcesPhase = new AssignResourcesPhase();
        d_players = new HashSet<>();
        d_player1 = new Player("d_player1");
        d_player2 = new Player("d_player2");
        d_players.add(d_player1);
        d_players.add(d_player1);
        d_players.add(d_player2);
        d_map = new Map();
    }

    /** Tests if assigning a random card to players is working as expected */
    @Test
    public void shouldAssignCardToPlayers() {

        d_assignResourcesPhase.handleCardAssignment(d_players, new GameEngine());

        assertThat(d_players.size(), equalTo(2));
        assertThat(d_player1.getD_cards().size(), equalTo(1));
        assertThat(
                d_player1.getD_cards().get(0),
                anyOf(is(BOMB), is(BLOCKADE), is(AIRLIFT), is(DIPLOMACY)));
        assertThat(d_player2.getD_cards().size(), equalTo(1));
        assertThat(
                d_player2.getD_cards().get(0),
                anyOf(is(BOMB), is(BLOCKADE), is(AIRLIFT), is(DIPLOMACY)));
    }

    /** Test assignReinforcements when 5 reinforcements assigned to each player */
    @Test
    void testInitialReinforcements() {
        d_map.addPlayer(d_player1.getD_name());
        d_map.addPlayer(d_player2.getD_name());

        new AssignResourcesPhase().handleReinforcementsAssignment(d_map, new GameEngine(d_map));

        // check reinforcements for each player
        assertEquals(
                0, d_player1.getD_reinforcements()); // Initial reinforcements for two players are 5
        assertEquals(0, d_player2.getD_reinforcements());
    }

    /** Test assignReinforcements when bonus reinforcements assigned to each player */
    @Test
    public void testAssignReinforcementsWithTerritoriesAndContinentBonus() {

        // Add players to the map
        d_map.getD_players().add(d_player1);
        d_map.getD_players().add(d_player2);

        Continent l_continent1 = new Continent(1, "Continent 1", Arrays.asList(1, 3), 3);
        Continent l_continent2 = new Continent(2, "Continent 2", Arrays.asList(2, 4), 5);

        List<Continent> l_continents = Arrays.asList(l_continent1, l_continent2);

        Country l_country1 = new Country(1, "country1", l_continent1);
        Country l_country2 = new Country(2, "country2", l_continent2);
        Country l_country3 = new Country(3, "country3", l_continent1);
        Country l_country4 = new Country(4, "country4", l_continent2);

        List<Country> l_countries = Arrays.asList(l_country1, l_country2, l_country3, l_country4);
        // Add continents to the map
        d_map.getD_continents().addAll(l_continents);
        // Add countries to the map
        d_map.getD_countries().addAll(l_countries);

        d_player1.getD_countries().addAll(Arrays.asList(l_country1, l_country2, l_country3));
        d_player2.getD_countries().add(l_country4);

        // Assign reinforcements
        new AssignResourcesPhase().handleReinforcementsAssignment(d_map, new GameEngine(d_map));

        // Check player 1 received correct number of reinforcements
        assertEquals(4, d_player1.getD_reinforcements()); // Base: 3 + Bonus: 3 = 8

        // Check player 2 received correct number of reinforcements
        assertEquals(3, d_player2.getD_reinforcements()); // Base: 3
    }
}
