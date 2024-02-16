package game.map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class MapTest {

    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map();
    }

    @Test
    void shouldAddContinent() {
        int id = 1;
        Continent continent = new Continent(id, "Continent1", 3);

        map.addContinent(continent);

        assertThat(map.getContinents().get(0), equalTo(continent));
    }

    @Test
    void shouldThrowExceptionWhenAddingNewContinentWithExistingId() {
        int id = 1;
        Continent continent1 = new Continent(id, "Continent1", 3);
        Continent continent2 = new Continent(id, "Continent2", 5);

        map.addContinent(continent1);

        assertThrows(IllegalArgumentException.class, () -> {
            map.addContinent(continent2);
        }, "Continent with same id already exists");
    }

    @Test
    void shouldAddCountry() {
        int id = 1;
        Country country = new Country(id, "Country1", new Continent());

        map.addCountry(country);

        assertThat(map.getCountries().get(0), equalTo(country));
    }

    @Test
    void shouldThrowExceptionWhenAddingNewCountryWithExistingId() {
        int id = 1;
        Country country1 = new Country(id, "Country1", new Continent());
        Country country2 = new Country(id, "Country2", new Continent());

        map.addCountry(country1);

        assertThrows(IllegalArgumentException.class, () -> {
            map.addCountry(country2);
        }, "Country with same id already exists");
    }

    @Test
    void shouldAddPlayer() {
        String playerName = "Player";

        map.addPlayer(playerName);

        assertThat(map.getPlayers().get(0), equalTo(new Player(playerName)));
    }

    @Test
    void shouldThrowExceptionWhenAddingNewPlayerWithExistingName() {
        String playerName1 = "Player";
        String playerName2 = "Player";

        map.addPlayer(playerName1);

        assertThrows(IllegalArgumentException.class, () -> {
            map.addPlayer(playerName2);
        }, "Player with same name already exists");
    }

    @Test
    void shouldRemovePlayer() {
        String playerName = "Player";
        map.addPlayer(playerName);

        map.removePlayer(playerName);

        assertThat(map.getPlayers().size(), equalTo(0));
    }

    @Test
    void shouldThrowExceptionWhenRemovingPlayerThatDoesNotExist() {
        String playerName = "Player";

        assertThrows(IllegalArgumentException.class, () -> {
            map.removePlayer(playerName);
        }, "No player exists with this name");
    }
}