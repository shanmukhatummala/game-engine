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
        List<Country> countries1Neighbours = new ArrayList<>();
        List<Country> countries2Neighbours = new ArrayList<>();
        List<Country> countries3Neighbours = new ArrayList<>();
        List<Country> countries4Neighbours = new ArrayList<>();
        List<Country> countries5Neighbours = new ArrayList<>();
        List<Country> countries6Neighbours = new ArrayList<>();
        List<Country> countries7Neighbours = new ArrayList<>();
        List<Country> countries8Neighbours = new ArrayList<>();
        List<Continent> continents = new ArrayList<>();
        Continent continent1 = new Continent(1, "continent1", 5);
        Continent continent2 = new Continent(2, "continent2", 4);
        continents.add(continent1);
        continents.add(continent2);
        Country country1 = new Country(1, "country1",continent1, countries1Neighbours, null, 0);
        Country country2 = new Country(2, "country2",continent1, countries2Neighbours, null, 0);
        Country country3 = new Country(3, "country3",continent1, countries3Neighbours, null, 0);
        Country country4 = new Country(4, "country4",continent1, countries4Neighbours, null, 0);
        Country country5 = new Country(5, "country2",continent2, countries5Neighbours, null, 0);
        Country country6 = new Country(6, "country3",continent2, countries6Neighbours, null, 0);
        Country country7 = new Country(7, "country4",continent2, countries7Neighbours, null, 0);
        Country country8 = new Country(8, "country4",continent2, countries8Neighbours, null, 0);
        countries8Neighbours.add(country2);
        countries8Neighbours.add(country4);
        countries8Neighbours.add(country7);
        countries6Neighbours.add(country1);
        countries6Neighbours.add(country2);
        countries6Neighbours.add(country4);
        countries5Neighbours.add(country4);
        countries.add(country1);
        countries.add(country2);
        countries.add(country3);
        countries5Neighbours.add(country1);
        countries5Neighbours.add(country2);
        countries5Neighbours.add(country3);
        countries.add(country4);
        countries.add(country5);
        countries.add(country6);
        countries.add(country7);
        countries.add(country8);
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
