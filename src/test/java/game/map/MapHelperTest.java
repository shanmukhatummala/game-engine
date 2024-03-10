package game.map;

import static game.map.MapHelper.getContinentWithId;
import static game.map.MapHelper.getCountryWithId;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import game.pojo.Continent;
import game.pojo.Country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * MapHelperTest is a test class for the MapHelper class
 *
 * @author Shanmukha
 */
class MapHelperTest {

    private Map d_map;

    /** Creates a new map before each test */
    @BeforeEach
    void setUp() {
        d_map = new Map();
    }

    /** Tests getContinentWithId method when id exists */
    @Test
    void shouldGetContinentWithId() {
        int l_id = 1;
        Continent l_continent = new Continent(l_id, "Continent1", 3);
        d_map.addContinent(l_continent);

        Continent l_continentReturned = getContinentWithId(d_map, l_id);

        assertThat(l_continentReturned, equalTo(l_continent));
    }

    /** Tests getContinentWithId method when id does not exist */
    @Test
    void shouldReturnNullWhenNoContinentExistsWithId() {
        int l_id = 1;

        Continent l_continentReturned = getContinentWithId(d_map, l_id);

        assertNull(l_continentReturned);
    }

    /** Tests getCountryWithId method when id exists */
    @Test
    void shouldGetCountryWithId() {
        int l_id = 1;
        Country l_country = new Country(l_id, "Country1", new Continent());
        d_map.addCountry(l_country);

        Country l_countryReturned = getCountryWithId(d_map, l_id);

        assertThat(l_countryReturned, equalTo(l_country));
    }

    /** Tests getCountryWithId method when id does not exist */
    @Test
    void shouldReturnNullWhenNoCountryExistsWithId() {
        int l_id = 1;

        Country l_countryReturned = getCountryWithId(d_map, l_id);

        assertNull(l_countryReturned);
    }
}
