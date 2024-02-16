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
import java.util.HashMap;
import java.util.List;


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

    @Test
    void shouldAssignCountries() {

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            countries.add(new Country(i, "Country" + i, new Continent()));
        }

        HashMap<Player, List<Country>> playerCountryMap = new HashMap<>();
        int numCountriesPerPlayer = countries.size() / players.size();
        int currentIndex = 0;
        for (Player player : players) {
            List<Country> assignedCountries = new ArrayList<>();
            for (int i = 0; i < numCountriesPerPlayer; i++) {
                assignedCountries.add(countries.get(currentIndex));
                currentIndex++;
            }
            playerCountryMap.put(player, assignedCountries);
        }

        for (Player player : players) {
            assertTrue(playerCountryMap.containsKey(player));
            assertFalse(playerCountryMap.get(player).isEmpty());
        }


        int totalAssignedCountries = playerCountryMap.values().stream().mapToInt(List::size).sum();
        assertEquals(countries.size(), totalAssignedCountries);


        List<Country> allAssignedCountries = new ArrayList<>();
        for (List<Country> assignedCountries : playerCountryMap.values()) {
            allAssignedCountries.addAll(assignedCountries);
        }
        assertEquals(countries.size(), allAssignedCountries.stream().distinct().count());
    }


}