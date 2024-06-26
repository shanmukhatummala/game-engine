package game.mapfile;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import game.map.Map;
import game.mapfile.reader.MapFileReader;
import game.pojo.Continent;
import game.pojo.Country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** MapFileReaderTest is a test class for the MapFileReader class */
public class MapFileReaderTest {
    private String d_path;
    private Map d_map;
    private MapFileReader d_mapFileReader;

    /** Sets up the initial configuration before the test starts */
    @BeforeEach
    void setUp() {
        d_mapFileReader = new MapFileReader();
        d_path = "src/test/resources/germany_test.map";
        d_map = new Map();
    }

    /** Tests readFile method to load a map */
    @Test
    public void shouldLoadDominationMapIntoJavaObjects() {

        d_path = "src/test/resources/germany_test.map";
        d_mapFileReader.readFile(d_path, d_map);

        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();

        List<Continent> l_expectedContinents = new ArrayList<>();
        List<Country> l_expectedCountries = new ArrayList<>();

        createObjectsToAssert(l_expectedContinents, l_expectedCountries);

        assertThat(l_countries, equalTo(l_expectedCountries));
        assertThat(l_continents, equalTo(l_expectedContinents));
    }

    /** Test that we should not load a badly formatted domination map */
    @Test
    public void shouldNotLoadBadlyFormattedDominationMapIntoJavaObjects() {

        d_path = "src/test/resources/bad_format_domination_map.map";
        d_mapFileReader.readFile(d_path, d_map);

        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();

        assertThat(l_countries.size(), equalTo(0));
        assertThat(l_continents.size(), equalTo(0));
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

        expectedContinents.add(l_continent1);
        expectedContinents.add(l_continent2);

        expectedCountries.add(l_country1);
        expectedCountries.add(l_country2);
        expectedCountries.add(l_country3);
        expectedCountries.add(l_country4);
        expectedCountries.add(l_country5);
    }
}
