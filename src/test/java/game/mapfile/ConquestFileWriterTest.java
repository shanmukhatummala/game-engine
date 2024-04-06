package game.mapfile;

import game.map.Map;
import game.mapfile.writer.ConquestFileWriter;
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
import java.util.Arrays;
import java.util.List;

/** Tests the ConquestFileWriter class */
public class ConquestFileWriterTest {

    Map d_map;
    final String d_pathToSaveMapOutcome = "src/test/resources/testMapSaver.map";
    private List<Continent> d_continents;
    private List<Country> d_countries;
    private ConquestFileWriter d_conquestFileWriter;

    /** Initializing context before each test */
    @BeforeEach
    void setUp() {
        d_continents = new ArrayList<>();
        d_countries = new ArrayList<>();
        createContinentsAndCountries();
        d_map = new Map(d_continents, d_countries, new ArrayList<>(), "testMapSaver.map");
        d_conquestFileWriter = new ConquestFileWriter();
    }

    /** This method creates the continents and countries objects for the map object */
    private void createContinentsAndCountries() {
        Continent l_continent1 = new Continent(1, "Cockpit", 5);
        Continent l_continent2 = new Continent(2, "Right Thruster", 6);
        Continent l_continent3 = new Continent(3, "Left Cargo", 10);

        Country l_country1 = new Country(1, "Cockpit01", l_continent1);
        Country l_country2 = new Country(2, "Cockpit02", l_continent1);
        Country l_country3 = new Country(3, "Territory04", l_continent3);
        Country l_country4 = new Country(4, "Territory05", l_continent3);
        Country l_country5 = new Country(5, "Territory06", l_continent3);
        Country l_country6 = new Country(6, "Territory07", l_continent3);

        l_country1.addNeighbors(Arrays.asList(2, 6));
        l_country2.addNeighbors(Arrays.asList(1, 5));
        l_country3.addNeighbors(List.of(4));
        l_country4.addNeighbors(Arrays.asList(6, 5, 3));
        l_country5.addNeighbors(Arrays.asList(2, 4));
        l_country6.addNeighbors(Arrays.asList(5, 1));

        d_continents.add(l_continent1);
        d_continents.add(l_continent2);
        d_continents.add(l_continent3);

        d_countries.add(l_country1);
        d_countries.add(l_country2);
        d_countries.add(l_country3);
        d_countries.add(l_country4);
        d_countries.add(l_country5);
        d_countries.add(l_country6);
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
     * This method test the writeConquestFile method in the ConquestFileWriter class by comparing
     * the content of the expected file to the file we created for test
     *
     * @throws IOException when writer fails to write
     */
    @Test
    void saveMap() throws IOException {
        String l_pathToExpectedOutcome = "src/test/resources/expected_outcome_conquest.map";
        d_conquestFileWriter.writeConquestFile(d_pathToSaveMapOutcome, d_map);
        List<String> l_expectedOutcome = Files.readAllLines(Paths.get(l_pathToExpectedOutcome));
        List<String> l_saveMapOutcome = Files.readAllLines(Paths.get(d_pathToSaveMapOutcome));
        Assertions.assertEquals(l_expectedOutcome, l_saveMapOutcome);
    }
}
