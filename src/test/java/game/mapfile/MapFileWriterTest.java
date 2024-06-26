package game.mapfile;

import game.map.Map;
import game.mapfile.writer.MapFileWriter;
import game.pojo.Continent;
import game.pojo.Country;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** Test class for MapFileWriter class */
public class MapFileWriterTest {

    Map d_map;
    final String d_pathToSaveMapOutcome = "src/test/resources/testMapSaver.map";
    private List<Country> d_countries;
    private MapFileWriter d_mapFileWriter;

    /** Setting up the map object to be tested */
    @BeforeEach
    void setUp() {
        List<Continent> l_continents = createContinents();
        d_countries = createCountries(l_continents);
        d_map = new Map(l_continents, d_countries, new ArrayList<>(), "testMapSaver.map");
        linkCountries();
        d_mapFileWriter = new MapFileWriter();
    }

    /**
     * This method create two continents for the map object
     *
     * @return List of continents
     */
    private List<Continent> createContinents() {
        List<Continent> l_continents = new ArrayList<>();
        l_continents.add(new Continent(1, "continent1", 5));
        l_continents.add(new Continent(2, "continent2", 4));
        return l_continents;
    }

    /**
     * This method create 8 Countries for the map object
     *
     * @param continents List of continents
     * @return List of countries
     */
    private List<Country> createCountries(List<Continent> continents) {
        List<Country> l_countries = new ArrayList<>();
        for (int l_i = 1; l_i <= 8; l_i++) {
            Continent l_continent = (l_i <= 4) ? continents.get(0) : continents.get(1);
            l_countries.add(new Country(l_i, "country" + l_i, l_continent, new HashSet<>(), 0));
        }
        return l_countries;
    }

    /** This method creates the borders (link the countries with each other) */
    private void linkCountries() {
        d_countries.get(0).getD_neighborIdList().addAll(List.of(1, 2, 4));
        d_countries.get(1).getD_neighborIdList().addAll(List.of(0, 2));
        d_countries.get(2).getD_neighborIdList().addAll(List.of(3, 4, 0));
        d_countries.get(3).getD_neighborIdList().addAll(List.of(1, 5));
        d_countries.get(4).getD_neighborIdList().addAll(List.of(0, 5));
        d_countries.get(5).getD_neighborIdList().addAll(List.of(0, 1));
        d_countries.get(6).getD_neighborIdList().addAll(List.of(7, 2));
        d_countries.get(7).getD_neighborIdList().addAll(List.of(6, 2, 5));
    }

    /**
     * This method is called after the test and it delete the test file that we have create to save
     * the map in after asserting that it has been saved right
     */
    @AfterEach
    void tearDown() {
        Path l_pathToFile = Paths.get(d_pathToSaveMapOutcome);
        try {
            Files.delete(l_pathToFile);
            System.out.println("File deleted successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This mehtod test the saveMap method in the MapSaver class by comparing the content of the
     * expected file to the file we created for test
     *
     * @throws IOException when writer fails to write
     */
    @Test
    void saveMap() throws IOException {
        String l_pathToExpectedOutcome = "src/test/resources/expected_outcome.map";
        d_mapFileWriter.writeMapFile(d_pathToSaveMapOutcome, d_map);
        List<String> l_expectedOutcome = Files.readAllLines(Paths.get(l_pathToExpectedOutcome));
        List<String> l_saveMapOutcome = Files.readAllLines(Paths.get(d_pathToSaveMapOutcome));
        Assertions.assertEquals(l_expectedOutcome, l_saveMapOutcome);
    }
}
