package game.map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.jupiter.api.Assertions.*;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<Player> players = Arrays.asList(new Player("a"), new Player("b"));
        List<Country> countries = new ArrayList<>();

        d_map.assignCountries(players, countries);

        assertTrue(players.stream().allMatch(player -> player.getD_countries().isEmpty()));
    }

    /**
     * <p>Test assignCountries when number of players divide number of countries</p>
     */
    @Test
    void testEqualDistributionWhenNumOfPlayersDividesNumOfCountries() {
        List<Player> players = Arrays.asList(
                new Player("a"),
                new Player("b"));
        List<Country> countries = Arrays.asList(
                new Country(1, "c1", new Continent()),
                new Country(2, "c2", new Continent()),
                new Country(3, "c3", new Continent()),
                new Country(4, "c4", new Continent()));

        d_map.assignCountries(players, countries);

        assertEquals(2, players.get(0).getD_countries().size());
        assertEquals(2, players.get(1).getD_countries().size());
    }

    /**
     * <p>Test assignCountries when number of players does not divide number of countries</p>
     */
    @Test
    void testDistributionWhenNumOfPlayersDoesNotDivideNumOfCountries() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            players.add(new Player("Player" + i));
            d_map.getD_players().add(players.get(i));
        }

        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            countries.add(new Country(i, "Country" + i, new Continent()));
            d_map.getD_countries().add(countries.get(i));
        }

        d_map.assignCountries(players, countries);

        List<Country> countriesAssignedToPlayers = new ArrayList<>();
        int countOfCountriesAssignedToPlayers = 0;

        for (int i = 0; i < 3; i ++) {
            List<Country> countriesAssignedToThisPlayer = players.get(i).getD_countries();
            assertThat(countriesAssignedToThisPlayer.size(), anyOf(equalTo(3), equalTo(4)));
            countriesAssignedToPlayers.addAll(countriesAssignedToThisPlayer);
            countOfCountriesAssignedToPlayers += countriesAssignedToThisPlayer.size();
        }

        assertThat(countOfCountriesAssignedToPlayers, equalTo(countries.size()));
        assertThat(countriesAssignedToPlayers, is(Matchers.containsInAnyOrder(countries.toArray())));
    }
}