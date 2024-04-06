package game.map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** MapTest is a test class for the Map class */
class MapTest {

    private Map d_map;

    /** Creates a new map before each test */
    @BeforeEach
    void setUp() {
        d_map = new Map();
    }

    /** Adds Continents, Countries, Neighbors To an emptied Map object */
    void setContinentsCountriesNeighborsToMap(Map p_map) {
        Continent l_continent_1 = Continent.builder().d_id(1).d_name("Asia").d_bonus(5).build();

        Country l_country_1 =
                Country.builder()
                        .d_id(1)
                        .d_name("India")
                        .d_continent(l_continent_1)
                        .d_armyCount(3)
                        .build();

        Country l_country_2 =
                Country.builder()
                        .d_id(2)
                        .d_name("China")
                        .d_continent(l_continent_1)
                        .d_armyCount(3)
                        .build();

        Continent l_continent_2 = Continent.builder().d_id(2).d_name("Europe").d_bonus(5).build();

        Country l_country_3 =
                Country.builder()
                        .d_id(3)
                        .d_name("Germany")
                        .d_continent(l_continent_2)
                        .d_armyCount(3)
                        .build();

        Country l_country_4 =
                Country.builder()
                        .d_id(4)
                        .d_name("France")
                        .d_continent(l_continent_2)
                        .d_armyCount(3)
                        .build();

        l_country_1.addNeighbors(Arrays.asList(2, 3));
        l_country_2.addNeighbors(Arrays.asList(1, 4));
        l_country_3.addNeighbors(Arrays.asList(1, 4));
        l_country_4.addNeighbors(Arrays.asList(2, 3));

        p_map.clearMap();

        p_map.getD_continents().addAll(Arrays.asList(l_continent_1, l_continent_2));
        p_map.getD_countries()
                .addAll(Arrays.asList(l_country_1, l_country_2, l_country_3, l_country_4));
    }

    /** Tests addContinent method to add a continent */
    @Test
    void shouldAddContinent() {
        int l_id = 1;
        Continent l_continent = new Continent(l_id, "Continent1", 3);

        d_map.addContinent(l_continent);

        assertThat(d_map.getD_continents().get(0), equalTo(l_continent));
    }

    /** Tests addContinent method to throw exception when adding new continent with existing id */
    @Test
    void shouldThrowExceptionWhenAddingNewContinentWithExistingId() {
        int l_id = 1;
        Continent l_continent1 = new Continent(l_id, "Continent1", 3);
        Continent l_continent2 = new Continent(l_id, "Continent2", 5);
        d_map.addContinent(l_continent1);
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    d_map.addContinent(l_continent2);
                },
                "Continent with same id already exists");
    }

    /** Tests addCountry method to add a country */
    @Test
    void shouldAddCountry() {
        int l_id = 1;
        Country l_country = new Country(l_id, "Country1", new Continent());

        d_map.addCountry(l_country);

        assertThat(d_map.getD_countries().get(0), equalTo(l_country));
    }

    /** Tests addCountry method to throw exception when adding new country with existing id */
    @Test
    void shouldThrowExceptionWhenAddingNewCountryWithExistingId() {
        int l_id = 1;
        Country l_country1 = new Country(l_id, "Country1", new Continent());
        Country l_country2 = new Country(l_id, "Country2", new Continent());

        d_map.addCountry(l_country1);

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    d_map.addCountry(l_country2);
                },
                "Country with same id already exists");
    }

    /** Tests addPlayer method to add a player */
    @Test
    void shouldAddPlayer() {
        String l_playerName = "Player";

        d_map.addPlayer(l_playerName);

        assertThat(d_map.getD_players().get(0), equalTo(new Player(l_playerName)));
    }

    /** Tests addPlayer method to throw exception when adding new player with existing name */
    @Test
    void shouldThrowExceptionWhenAddingNewPlayerWithExistingName() {
        String l_playerName1 = "Player";
        String l_playerName2 = "Player";

        d_map.addPlayer(l_playerName1);

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    d_map.addPlayer(l_playerName2);
                },
                "Player with same name already exists");
    }

    /** Tests removePlayer method to remove a player */
    @Test
    void shouldRemovePlayer() {
        String l_playerName = "Player";
        d_map.addPlayer(l_playerName);

        d_map.removePlayer(l_playerName);

        assertThat(d_map.getD_players().size(), equalTo(0));
    }

    /** Tests removePlayer method to throw exception when removing a player that does not exist */
    @Test
    void shouldThrowExceptionWhenRemovingPlayerThatDoesNotExist() {
        String l_playerName = "Player";

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    d_map.removePlayer(l_playerName);
                },
                "No player exists with this name");
    }

    /** Test assignCountries when no countries available */
    @Test
    void testNoCountriesAvailable() {
        List<Player> l_players = Arrays.asList(new Player("a"), new Player("b"));
        List<Country> l_countries = new ArrayList<>();

        d_map.assignCountries(l_players, l_countries);

        assertTrue(l_players.stream().allMatch(player -> player.getD_countries().isEmpty()));
    }

    /** Test assignCountries when number of players divide number of countries */
    @Test
    void testEqualDistributionWhenNumOfPlayersDividesNumOfCountries() {
        List<Player> l_players = Arrays.asList(new Player("a"), new Player("b"));
        List<Country> l_countries =
                Arrays.asList(
                        new Country(1, "c1", new Continent()),
                        new Country(2, "c2", new Continent()),
                        new Country(3, "c3", new Continent()),
                        new Country(4, "c4", new Continent()));

        d_map.assignCountries(l_players, l_countries);

        assertEquals(2, l_players.get(0).getD_countries().size());
        assertEquals(2, l_players.get(1).getD_countries().size());
    }

    /** Test assignCountries when number of players does not divide number of countries */
    @Test
    void testDistributionWhenNumOfPlayersDoesNotDivideNumOfCountries() {
        List<Player> l_players = new ArrayList<>();
        for (int l_i = 0; l_i < 3; l_i++) {
            l_players.add(new Player("Player" + l_i));
            d_map.getD_players().add(l_players.get(l_i));
        }

        List<Country> l_countries = new ArrayList<>();
        for (int l_i = 0; l_i < 11; l_i++) {
            l_countries.add(new Country(l_i, "Country" + l_i, new Continent()));
            d_map.getD_countries().add(l_countries.get(l_i));
        }

        d_map.assignCountries(l_players, l_countries);

        List<Country> l_countriesAssignedToPlayers = new ArrayList<>();
        int l_countOfCountriesAssignedToPlayers = 0;

        for (int l_i = 0; l_i < 3; l_i++) {
            List<Country> l_countriesAssignedToThisPlayer = l_players.get(l_i).getD_countries();
            assertThat(l_countriesAssignedToThisPlayer.size(), anyOf(equalTo(3), equalTo(4)));
            l_countriesAssignedToPlayers.addAll(l_countriesAssignedToThisPlayer);
            l_countOfCountriesAssignedToPlayers += l_countriesAssignedToThisPlayer.size();
        }

        assertThat(l_countOfCountriesAssignedToPlayers, equalTo(l_countries.size()));
        assertThat(
                l_countriesAssignedToPlayers,
                is(Matchers.containsInAnyOrder(l_countries.toArray())));
    }

    /** Tests addNeighborToCountry function of Map.java */
    @Test
    public void testAddNeighbor() {
        setContinentsCountriesNeighborsToMap(d_map);

        Assertions.assertFalse(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(4));

        d_map.addNeighborToCountry(1, 4);

        Assertions.assertTrue(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(4));
    }

    /** Tests removeNeighborFromCountry function of Map.java */
    @Test
    public void testRemoveNeighbor() {
        setContinentsCountriesNeighborsToMap(d_map);

        Assertions.assertTrue(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(2));

        d_map.removeNeighborFromCountry(1, 2);

        Assertions.assertFalse(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(2));
    }

    /**
     * Checks that a country is removed from its neighbors' d_neighborIdList when country is removed
     * from map
     */
    @Test
    public void checkNeighborIsRemovedWhenCountryIsRemoved() {
        setContinentsCountriesNeighborsToMap(d_map);

        Assertions.assertTrue(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(2));

        d_map.removeCountry(2);

        Assertions.assertFalse(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(2));
    }

    /**
     * Checks that a country is removed from its neighbors' d_neighborIdList when country's parent
     * continent is removed from map
     */
    @Test
    public void checkNeighborIsRemovedWhenContinentIsRemoved() {
        setContinentsCountriesNeighborsToMap(d_map);

        Assertions.assertTrue(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(3));

        d_map.removeContinent("Europe");

        Assertions.assertFalse(
                d_map.getCountryForCountryName("India").getD_neighborIdList().contains(3));
    }
}
