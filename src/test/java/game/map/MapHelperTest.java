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

class MapHelperTest {

    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map();
    }

    @Test
    void shouldGetContinentWithId() {
        int id = 1;
        Continent continent = new Continent(id, "Continent1", 3);
        map.addContinent(continent);

        Continent continentReturned = getContinentWithId(map, id);

        assertThat(continentReturned, equalTo(continent));
    }

    @Test
    void shouldReturnNullWhenNoContinentExistsWithId() {
        int id = 1;

        Continent continentReturned = getContinentWithId(map, id);

        assertNull(continentReturned);
    }

    @Test
    void shouldGetCountryWithId() {
        int id = 1;
        Country country = new Country(id, "Country1", new Continent());
        map.addCountry(country);

        Country countryReturned = getCountryWithId(map, id);

        assertThat(countryReturned, equalTo(country));
    }

    @Test
    void shouldReturnNullWhenNoCountryExistsWithId() {
        int id = 1;

        Country countryReturned = getCountryWithId(map, id);

        assertNull(countryReturned);
    }
}