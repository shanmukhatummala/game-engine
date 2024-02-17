package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class MapSaverTest {
    Map map;
    final String d_pathToSaveMapOutcome = "src/test/resources/testMapSaver.map";
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
        countries.get(0).getD_neighborIdList().addAll(List.of(1, 2, 4));
        countries.get(1).getD_neighborIdList().addAll(List.of(0, 2));
        countries.get(2).getD_neighborIdList().addAll(List.of(3, 4, 0));
        countries.get(3).getD_neighborIdList().addAll(List.of(1, 5));
        countries.get(4).getD_neighborIdList().addAll(List.of(0, 5));
        countries.get(5).getD_neighborIdList().addAll(List.of(0, 1));
        countries.get(6).getD_neighborIdList().addAll(List.of(7, 2));
        countries.get(7).getD_neighborIdList().addAll(List.of(6, 2, 5));
    }

    @AfterEach
    void tearDown() {
        Path l_pathToFile = Paths.get(d_pathToSaveMapOutcome);
        try{
            Files.delete(l_pathToFile);
            System.out.println("File deleted successfully");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void saveMap() throws IOException{
        String l_pathToExpectedOutcome = "src/test/resources/expectedOutcome.map";
        MapSaver.saveMap(d_pathToSaveMapOutcome, map);
        List<String> l_expectedOutcome = Files.readAllLines(Paths.get(l_pathToExpectedOutcome));
        List<String> l_saveMapOutcome = Files.readAllLines(Paths.get(d_pathToSaveMapOutcome));
        Assertions.assertEquals(l_expectedOutcome, l_saveMapOutcome);
    }
}
