package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.map.MapSaver;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapSaverTest {
    Map map;
    @BeforeEach
    void setUp() {
        List<Country> countries = new ArrayList<>();
        List<Continent> continents = new ArrayList<>();
        Continent continent1 = new Continent(1, "continent1", 5);
        Continent continent2 = new Continent(2, "continent2", 4);
        continents.add(continent1);
        continents.add(continent2);
        Country country1 = new Country(1, "country1",continent1);
        Country country2 = new Country(2, "country2",continent1);
        Country country3 = new Country(3, "country3",continent2);
        Country country4 = new Country(4, "country4",continent2);
        countries.add(country1);
        countries.add(country2);
        countries.add(country3);
        countries.add(country4);
        map = new Map(continents, countries, new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveMapTest() throws IOException{
        MapSaver.saveMap("src/main/resources/testMapSaver.map", map);


    }

}
