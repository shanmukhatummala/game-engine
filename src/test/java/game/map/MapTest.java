package game.map;

import game.GameEngine;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * MapTest is a test class for the Map class
 */
class MapTest {

    private Map d_map;

    /**
     * <p>Creates a new map before each test</p>
     */
    @BeforeEach
    void setUp() {
        d_map = new Map();
    }

    /**
     * <p>Tests addContinent method to add a continent</p>
     */
    @Test
    void shouldAddContinent() {
        int l_id = 1;
        Continent l_continent = new Continent(l_id, "Continent1", 3);

        d_map.addContinent(l_continent);

        assertThat(d_map.getD_continents().get(0), equalTo(l_continent));
    }

    /**
     * <p>Tests addContinent method to throw exception when adding new continent with existing id</p>
     */
    @Test
    void shouldThrowExceptionWhenAddingNewContinentWithExistingId() {
        int l_id = 1;
        Continent l_continent1 = new Continent(l_id, "Continent1", 3);
        Continent l_continent2 = new Continent(l_id, "Continent2", 5);
        d_map.addContinent(l_continent1);
        assertThrows(IllegalArgumentException.class, () -> {
            d_map.addContinent(l_continent2);
        }, "Continent with same id already exists");
    }

    /**
     * <p>Tests addCountry method to add a country</p>
     */
    @Test
    void shouldAddCountry() {
        int l_id = 1;
        Country l_country = new Country(l_id, "Country1", new Continent());

        d_map.addCountry(l_country);

        assertThat(d_map.getD_countries().get(0), equalTo(l_country));
    }

    /**
     * <p>Tests addCountry method to throw exception when adding new country with existing id</p>
     */
    @Test
    void shouldThrowExceptionWhenAddingNewCountryWithExistingId() {
        int l_id = 1;
        Country l_country1 = new Country(l_id, "Country1", new Continent());
        Country l_country2 = new Country(l_id, "Country2", new Continent());

        d_map.addCountry(l_country1);

        assertThrows(IllegalArgumentException.class, () -> {
            d_map.addCountry(l_country2);
        }, "Country with same id already exists");
    }

    /**
     * <p>Tests addPlayer method to add a player</p>
     */
    @Test
    void shouldAddPlayer() {
        String l_playerName = "Player";

        d_map.addPlayer(l_playerName);

        assertThat(d_map.getD_players().get(0), equalTo(new Player(l_playerName)));
    }

    /**
     * <p>Tests addPlayer method to throw exception when adding new player with existing name</p>
     */
    @Test
    void shouldThrowExceptionWhenAddingNewPlayerWithExistingName() {
        String l_playerName1 = "Player";
        String l_playerName2 = "Player";

        d_map.addPlayer(l_playerName1);

        assertThrows(IllegalArgumentException.class, () -> {
            d_map.addPlayer(l_playerName2);
        }, "Player with same name already exists");
    }

    /**
     * <p>Tests removePlayer method to remove a player</p>
     */
    @Test
    void shouldRemovePlayer() {
        String l_playerName = "Player";
        d_map.addPlayer(l_playerName);

        d_map.removePlayer(l_playerName);

        assertThat(d_map.getD_players().size(), equalTo(0));
    }

    /**
     * <p>Tests removePlayer method to throw exception when removing a player that does not exist</p>
     */
    @Test
    void shouldThrowExceptionWhenRemovingPlayerThatDoesNotExist() {
        String l_playerName = "Player";

        assertThrows(IllegalArgumentException.class, () -> {
            d_map.removePlayer(l_playerName);
        }, "No player exists with this name");
    }

    /**
     * <p>Test assignCountries when no countries available</p>
     */
    @Test
    void testNoCountriesAvailable() {
        List<Player> l_players = Arrays.asList(new Player("a"), new Player("b"));
        List<Country> l_countries = new ArrayList<>();

        d_map.assignCountries(l_players, l_countries);

        assertTrue(l_players.stream().allMatch(player -> player.getD_countries().isEmpty()));
    }

    /**
     * <p>Test assignCountries when number of players divide number of countries</p>
     */
    @Test
    void testEqualDistributionWhenNumOfPlayersDividesNumOfCountries() {
        List<Player> l_players = Arrays.asList(
                new Player("a"),
                new Player("b"));
        List<Country> l_countries = Arrays.asList(
                new Country(1, "c1", new Continent()),
                new Country(2, "c2", new Continent()),
                new Country(3, "c3", new Continent()),
                new Country(4, "c4", new Continent()));

        d_map.assignCountries(l_players, l_countries);

        assertEquals(2, l_players.get(0).getD_countries().size());
        assertEquals(2, l_players.get(1).getD_countries().size());
    }

    /**
     * <p>Test assignCountries when number of players does not divide number of countries</p>
     */
    @Test
    void testDistributionWhenNumOfPlayersDoesNotDivideNumOfCountries() {
        List<Player> l_players = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            l_players.add(new Player("Player" + i));
            d_map.getD_players().add(l_players.get(i));
        }

        List<Country> l_countries = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            l_countries.add(new Country(i, "Country" + i, new Continent()));
            d_map.getD_countries().add(l_countries.get(i));
        }

        d_map.assignCountries(l_players, l_countries);

        List<Country> l_countriesAssignedToPlayers = new ArrayList<>();
        int l_countOfCountriesAssignedToPlayers = 0;

        for (int i = 0; i < 3; i ++) {
            List<Country> l_countriesAssignedToThisPlayer = l_players.get(i).getD_countries();
            assertThat(l_countriesAssignedToThisPlayer.size(), anyOf(equalTo(3), equalTo(4)));
            l_countriesAssignedToPlayers.addAll(l_countriesAssignedToThisPlayer);
            l_countOfCountriesAssignedToPlayers += l_countriesAssignedToThisPlayer.size();
        }

        assertThat(l_countOfCountriesAssignedToPlayers, equalTo(l_countries.size()));
        assertThat(l_countriesAssignedToPlayers, is(Matchers.containsInAnyOrder(l_countries.toArray())));
    }

    /**
     * <p>Test assignReinforcements when 5 reinforcements  assigned to each player</p>
     */
    @Test
    void testAssignReinforcements() {

        Player l_player1 = new Player("Player1");
        Player l_player2 = new Player("Player2");
        d_map.addPlayer(l_player1.getD_name());
        d_map.addPlayer(l_player2.getD_name());

        GameEngine.assignReinforcements(d_map);

        // check reinforcements for each player
        assertEquals(5, l_player1.getD_reinforcements()); // Initial reinforcements for two players are 5
        assertEquals(5, l_player2.getD_reinforcements());
    }

    /**
     * <p>Test assignReinforcements when bonus reinforcements assigned to each player</p>
     */
    @Test
    public void testAssignReinforcementsWithContinentBonus() {


        Player l_player1 = new Player("Player1");
        Player l_player2 = new Player("Player2");

        // Add players to the map
        d_map.getD_players().add(l_player1);
        d_map.getD_players().add(l_player2);

        Continent l_continent1 = new Continent(1, "Continent 1", Arrays.asList(1, 3), 3);
        Continent l_continent2 = new Continent(2, "Continent 2", Arrays.asList(2, 4), 5);

        List<Continent>l_continents = Arrays.asList(l_continent1, l_continent2);

        Country l_country1 = new Country(1, "country1", l_continent1);
        Country l_country2 = new Country(2, "country2", l_continent2);
        Country l_country3 = new Country(3, "country3", l_continent1);
        Country l_country4 = new Country(4, "country4", l_continent2);

        List<Country> l_countries = Arrays.asList(l_country1, l_country2, l_country3, l_country4);
        // Add continents to the map
        d_map.getD_continents().addAll(l_continents);
        // Add countries to the map
        d_map.getD_countries().addAll(l_countries);


        l_player1.getD_countries().addAll(Arrays.asList(l_country1, l_country2, l_country3));
        l_player2.getD_countries().add(l_country4);

        // Assign reinforcements
        GameEngine.assignReinforcements(d_map);

        // Check player 1 received correct number of reinforcements
        assertEquals(8, l_player1.getD_reinforcements()); // Base: 5 + Bonus: 3 = 8

        // Check player 2 received correct number of reinforcements
        assertEquals(5, l_player2.getD_reinforcements()); // Base: 5
    }


}