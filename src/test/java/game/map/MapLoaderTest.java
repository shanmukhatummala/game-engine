package game.map;

import static game.map.MapLoader.loadMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MapLoaderTest {

    private String path;
    private Map map;

    @BeforeEach
    void setUp() {
        path = "src/test/resources/test_load_map.map";
        map = new Map();
    }

    @Test
    public void shouldLoadMapIntoJavaObjects() {

        loadMap(path, map);

        List<Continent> continents = map.getContinents();
        List<Country> countries = map.getCountries();
        List<Player> players = map.getPlayers();

        List<Continent> expectedContinents = new ArrayList<>();
        List<Country> expectedCountries = new ArrayList<>();

        createObjectsToAssert(expectedContinents, expectedCountries);

        //assert continents details
        assertThat(continents.size(), equalTo(2));
        assertThat(continents.get(0), equalTo(expectedContinents.get(0)));
        assertThat(continents.get(1), equalTo(expectedContinents.get(1)));

        //assert countries details
        assertThat(countries.size(), equalTo(5));
        assertThat(countries.get(0), equalTo(expectedCountries.get(0)));
        assertThat(countries.get(1), equalTo(expectedCountries.get(1)));
        assertThat(countries.get(2), equalTo(expectedCountries.get(2)));
        assertThat(countries.get(3), equalTo(expectedCountries.get(3)));
        assertThat(countries.get(4), equalTo(expectedCountries.get(4)));

        assertThat(players.size(), equalTo(0));
    }

    private void createObjectsToAssert(List<Continent> expectedContinents, List<Country> expectedCountries) {
        Continent continent1 = new Continent(1, "Norddeutschland", 3);
        Continent continent2 = new Continent(2, "Westdeutschland", 4);

        Country country1 = new Country(1, "Ostfriesland", continent1);
        Country country2 = new Country(2, "Schleswig", continent2);
        Country country3 = new Country(3, "Holstein", continent1);
        Country country4 = new Country(4, "Hamburg", continent2);
        Country country5 = new Country(5, "Mecklenburger-Bucht", continent2);

        country1.addNeighbours(Arrays.asList(2, 3));
        country2.addNeighbours(Arrays.asList(1, 3, 5));
        country3.addNeighbours(Arrays.asList(1, 2, 4));
        country4.addNeighbours(List.of(3));
        country5.addNeighbours(Arrays.asList(2, 3));

        expectedContinents.add(continent1);
        expectedContinents.add(continent2);

        expectedCountries.add(country1);
        expectedCountries.add(country2);
        expectedCountries.add(country3);
        expectedCountries.add(country4);
        expectedCountries.add(country5);
    }
}