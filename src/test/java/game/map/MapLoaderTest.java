package game.map;

import static game.map.MapLoader.loadMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** MapLoaderTest is a test class for the MapLoader class */
class MapLoaderTest {

    private String d_path;
    private Map d_map;

    /** Creates a new map before each test */
    @BeforeEach
    void setUp() {
        d_path = "src/test/resources/germany_test.map";
        d_map = new Map();
    }

    /** Tests loadMap method to load a map */
    @Test
    public void shouldLoadMapIntoJavaObjects() {

        loadMap(d_path, d_map);

        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();
        List<Player> l_players = d_map.getD_players();

        List<Continent> l_expectedContinents = new ArrayList<>();
        List<Country> l_expectedCountries = new ArrayList<>();
        List<Player> l_expectedPlayers = new ArrayList<>();

        createObjectsToAssert(l_expectedContinents, l_expectedCountries);

        assertThat(l_countries, equalTo(l_expectedCountries));
        assertThat(l_continents, equalTo(l_expectedContinents));
        assertThat(l_players, equalTo(l_expectedPlayers));
    }

    /** Create expected objects for the test to assert */
    private void createObjectsToAssert(
            List<Continent> expectedContinents, List<Country> expectedCountries) {
        Continent l_continent1 = new Continent(1, "Norddeutschland", 3);
        Continent l_continent2 = new Continent(2, "Westdeutschland", 4);

        Country l_country1 = new Country(1, "Ostfriesland", l_continent1);
        Country l_country2 = new Country(2, "Schleswig", l_continent2);
        Country l_country3 = new Country(3, "Holstein", l_continent1);
        Country l_country4 = new Country(4, "Hamburg", l_continent2);
        Country l_country5 = new Country(5, "Mecklenburger-Bucht", l_continent2);

        l_country1.addNeighbors(Arrays.asList(2, 3));
        l_country2.addNeighbors(Arrays.asList(1, 3, 5));
        l_country3.addNeighbors(Arrays.asList(1, 2, 4));
        l_country4.addNeighbors(List.of(3));
        l_country5.addNeighbors(Arrays.asList(2, 3));

        l_continent1.addCountryId(1);
        l_continent1.addCountryId(3);
        l_continent2.addCountryId(2);
        l_continent2.addCountryId(4);
        l_continent2.addCountryId(5);

        expectedContinents.add(l_continent1);
        expectedContinents.add(l_continent2);

        expectedCountries.add(l_country1);
        expectedCountries.add(l_country2);
        expectedCountries.add(l_country3);
        expectedCountries.add(l_country4);
        expectedCountries.add(l_country5);
    }
}
