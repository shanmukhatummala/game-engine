package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * This class is responsible for testing the EditMapPhase
 */
public class EditMapPhaseTest {


    /**
     * The data member represent the base path of the resources folder in the test
     */
    private String d_path;
    /**
     * Object of the game engine in order to use the phase data member
     */
    private GameEngine d_playSetUpPhase;
    /**
     * This data member represent the map that we will use in the tests
     */
    private Map d_map;

    /**
     * This data member is used for the output comparison
     */
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     *  Sets up the required objects for the execution of tests
     */
    @BeforeEach
    public void setUp() {
        d_playSetUpPhase = new GameEngine();
        d_playSetUpPhase.setD_gamePhase(new EditMapPhase());
        d_map = new Map();
        d_path = "src/test/resources/";
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * This method create a test map dummy with countries and contents for to test with
     * @return Map object
     */
    private Map createObjectsToAssert() {
        List<Continent> l_expectedContinents = new ArrayList<>();
        List<Country> l_expectedCountries = new ArrayList<>();
        List<Player> l_expectedPlayers = new ArrayList<>();

        Continent l_continent1 = new Continent(1, "Asia", 4);

        Country l_country1 = new Country(1, "Japan", l_continent1);
        Country l_country2 = new Country(2, "Russia", l_continent1);
        Country l_country3 = new Country(3, "Taiwan", l_continent1);
        Country l_country4 = new Country(4, "India", l_continent1);

        l_country1.addNeighbors(Arrays.asList(2, 3, 4));
        l_country2.addNeighbors(List.of(1));
        l_country3.addNeighbors(List.of(2));
        l_country4.addNeighbors(List.of(2));

        l_continent1.addCountryId(1);
        l_continent1.addCountryId(2);
        l_continent1.addCountryId(3);
        l_continent1.addCountryId(4);

        l_expectedContinents.add(l_continent1);

        l_expectedCountries.add(l_country1);
        l_expectedCountries.add(l_country2);
        l_expectedCountries.add(l_country3);
        l_expectedCountries.add(l_country4);
        return new Map(l_expectedContinents, l_expectedCountries, l_expectedPlayers, "new.map");
    }


    /**
     * This method is for testing the handleLoadMap in the wrong phase method and compare the printed output
     */
    @Test
    public void handleLoadMapTest() {
        String l_expectedOutput =
                "Invalid Command in state EditMapPhase you can't load a map here.";
        d_playSetUpPhase
                .getD_gamePhase()
                .handleLoadMap(new Command("", new ArrayList<>()), d_map, d_playSetUpPhase, d_path);
        Assertions.assertEquals(l_expectedOutput, outputStreamCaptor.toString().trim());
    }

    /**
     * This method is for testing the handleSaveMap in the right phase and compare the method is changing the phase object to the correct kind of phase object.
     */
    @Test
    public void handleSaveMapTest() {
        List<String> l_args = new ArrayList<>();
        l_args.add("new.map");
        d_map = createObjectsToAssert();
        Command l_command = new Command("savemap", l_args);
        d_playSetUpPhase.getD_gamePhase().handleSaveMap(l_command, d_map, d_playSetUpPhase, d_path);
        String l_expectedPhase = "PlaySetupPhase";
        Assertions.assertEquals(
                l_expectedPhase, d_playSetUpPhase.getD_gamePhase().getClass().getSimpleName());
    }

    /**
     * This method is Testing the handleEditCountriesOrContinentOrNeighbor method where we test for adding new country
     */
    @Test
    public void handleEditCountriesTest() {
        List<Continent> l_continent = new ArrayList<>();
        l_continent.add(new Continent(1, "Asia", 4));
        d_map = new Map(l_continent, new ArrayList<>(), new ArrayList<>(), "test.map");
        String[] l_args = new String[] {"editcountry", "-add", "test", "Asia"};
        List<Country> l_expectedCountries = new ArrayList<>();
        l_expectedCountries.add(new Country(1, "test", l_continent.get(0)));
        d_playSetUpPhase.getD_gamePhase().handleEditCountriesOrContinentOrNeighbor(l_args, d_map);
        Assertions.assertEquals(l_expectedCountries, d_map.getD_countries());
    }

    /**
     * This method is Testing the handleEditCountriesOrContinentOrNeighbor method where we test for adding new content
     */
    @Test
    public void handleEditContinentTest() {
        d_map = new Map(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "test.map");
        String[] l_args = new String[] {"editcontinent", "-add", "test", "3"};
        List<Continent> l_expectedContinent = new ArrayList<>();
        l_expectedContinent.add(new Continent(1, "test", 3));
        d_playSetUpPhase.getD_gamePhase().handleEditCountriesOrContinentOrNeighbor(l_args, d_map);
        Assertions.assertEquals(l_expectedContinent, d_map.getD_continents());
    }


    /**
     * This method run after every test and is for cleaning the resources used in the tests like returning the System.setOut to it's default value.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }
}
