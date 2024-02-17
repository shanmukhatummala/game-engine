package game.map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



class MapTest {

    private Map d_map;

    @BeforeEach
    void setUp() {
        d_map = new Map();
    }

    @Test
    void shouldAddContinent() {
        int l_id = 1;
        Continent l_continent = new Continent(l_id, "Continent1", 3);

        d_map.addContinent(l_continent);

        assertThat(d_map.getD_continents().get(0), equalTo(l_continent));
    }

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

    @Test
    void shouldAddCountry() {
        int l_id = 1;
        Country l_country = new Country(l_id, "Country1", new Continent());

        d_map.addCountry(l_country);

        assertThat(d_map.getD_countries().get(0), equalTo(l_country));
    }

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

    @Test
    void shouldAddPlayer() {
        String l_playerName = "Player";

        d_map.addPlayer(l_playerName);

        assertThat(d_map.getD_players().get(0), equalTo(new Player(l_playerName)));
    }

    @Test
    void shouldThrowExceptionWhenAddingNewPlayerWithExistingName() {
        String l_playerName1 = "Player";
        String l_playerName2 = "Player";

        d_map.addPlayer(l_playerName1);

        assertThrows(IllegalArgumentException.class, () -> {
            d_map.addPlayer(l_playerName2);
        }, "Player with same name already exists");
    }

    @Test
    void shouldRemovePlayer() {
        String l_playerName = "Player";
        d_map.addPlayer(l_playerName);

        d_map.removePlayer(l_playerName);

        assertThat(d_map.getD_players().size(), equalTo(0));
    }

    @Test
    void shouldThrowExceptionWhenRemovingPlayerThatDoesNotExist() {
        String l_playerName = "Player";

        assertThrows(IllegalArgumentException.class, () -> {
            d_map.removePlayer(l_playerName);
        }, "No player exists with this name");
    }

    @Test
    void testNoCountriesAvailable() {
        List<Player> players = Arrays.asList(new Player(), new Player());
        List<Country> countries = new ArrayList<>();
        d_map.assignCountries(players, countries);
        assertTrue(players.stream().allMatch(player -> player.getD_countries().isEmpty()));
    }
    @Test
    void testEqualDistributionWhenCountriesPerPlayerIsZero() {
        List<Player> players = Arrays.asList(new Player(), new Player());
        List<Country> countries = Arrays.asList(new Country(), new Country(), new Country(),new Country());
        d_map.assignCountries(players, countries);
        assertEquals(2, players.get(0).getD_countries().size());
        assertEquals(2, players.get(1).getD_countries().size());
    }
    //@Test
//    void testExtraCountryAssignedToPlayerWhenCountriesPerPlayerIsNotZero() {
//        List<Player> players = Arrays.asList(new Player(), new Player(), new Player());
//        List<Country> countries = Arrays.asList(new Country(), new Country(), new Country(), new Country());
//        d_map.assignCountries(players, countries);
//
//        // Calculate expected number of countries per player
//        int countriesPerPlayer = countries.size() / players.size();
//        int extraCountries = countries.size() % players.size();
//
//        // Assert the number of countries assigned to each player
//        for (int i = 0; i < players.size(); i++) {
//            int expectedCountries = countriesPerPlayer + (i < extraCountries ? 1 : 0);
//            assertEquals(expectedCountries, players.get(i).getD_countries().size());
//        }
//    }
}