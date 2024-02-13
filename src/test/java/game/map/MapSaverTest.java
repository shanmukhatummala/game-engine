package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class MapSaverTest {
    Map map;
    private List<Country> countries;
    private List<Continent> continents;
    @BeforeEach
    void setUp() {
        continents = createContinents();
        countries = createCountries(continents);
        map = new Map(continents, countries, new ArrayList<>());
        linkCountries();
    }

    private List<Continent> createContinents() {
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, "continent1", 5));
        continents.add(new Continent(2, "continent2", 4));
        return continents;
    }

    private List<Country> createCountries(List<Continent> continents) {
        List<Country> countries = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            Continent continent = (i <= 4) ? continents.get(0) : continents.get(1);
            countries.add(new Country(i, "country" + i, continent, new ArrayList<>(), null, 0));
        }
        return countries;
    }

    private void linkCountries() {
        countries.get(0).getNeighbours().addAll(List.of(countries.get(1), countries.get(2), countries.get(4)));
        countries.get(1).getNeighbours().addAll(List.of(countries.get(0), countries.get(2)));
        countries.get(2).getNeighbours().addAll(List.of(countries.get(3), countries.get(4), countries.get(0)));
        countries.get(3).getNeighbours().addAll(List.of(countries.get(1), countries.get(5)));
        countries.get(4).getNeighbours().addAll(List.of(countries.get(0), countries.get(5)));
        countries.get(5).getNeighbours().addAll(List.of(countries.get(0), countries.get(1)));
        countries.get(6).getNeighbours().addAll(List.of(countries.get(7), countries.get(2)));
        countries.get(7).getNeighbours().addAll(List.of(countries.get(6), countries.get(2), countries.get(5)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveMap() throws IOException{
        MapSaver.saveMap("src/main/resources/testMapSaver.map", map);
    }
}