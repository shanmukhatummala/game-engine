package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.map.MapManipulation.MapManipulator;
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

import static game.map.MapSaver.saveMap;
import static game.map.MapValidator.isMapValid;


public class EditMapPhaseTest {


    private String d_path;
    private GameEngine d_playSetUpPhase;
    private Map d_map;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /** Sets up the required objects for the execution of tests */
    @BeforeEach
    public void setUp() {
        d_playSetUpPhase = new GameEngine();
        d_playSetUpPhase.setD_gamePhase(new EditMapPhase());
        d_map = new Map();
        d_path = "src/test/resources/";
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    private Map createObjectsToAssert() {
        List<Continent> l_expectedContinents = new ArrayList<>();
        List<Country> l_expectedCountries = new ArrayList<>();
        List<Player> l_expectedPlayers = new ArrayList<>();

        Continent l_continent1 = new Continent(1, "Asia", 4);

        Country l_country1 = new Country(1, "Japan", l_continent1);
        Country l_country2 = new Country(2, "Russia", l_continent1);
        Country l_country3 = new Country(3, "Taiwan", l_continent1);
        Country l_country4 = new Country(4, "India", l_continent1);

        l_country1.addNeighbors(Arrays.asList(2,3,4));
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
        return new Map(l_expectedContinents,l_expectedCountries,l_expectedPlayers,"new.map");
    }



    @Test
    public void handleLoadMapTest(){
        String l_expectedOutput = "Invalid Command in state EditMapPhase you can't load a map here.";
        d_playSetUpPhase.getD_gamePhase().handleLoadMap(new Command("",new ArrayList<>()),d_map,d_playSetUpPhase,d_path);
        Assertions.assertEquals(l_expectedOutput,outputStreamCaptor.toString().trim());
    }

    @Test
    public void handleSaveMapTest(){
        List<String> l_args = new ArrayList<>();
        l_args.add("new.map");
        d_map = createObjectsToAssert();
        Command l_command = new Command("savemap",l_args);
        d_playSetUpPhase.getD_gamePhase().handleSaveMap(l_command,d_map,d_playSetUpPhase,d_path);
        String l_expectedPhase = "PlaySetupPhase";
        Assertions.assertEquals(l_expectedPhase, d_playSetUpPhase.getGamePhase().getClass().getSimpleName());
    }


    @Test
    public void handleEditCountriesTest() {
        List<Continent> l_continent = new ArrayList<>();
        l_continent.add(new Continent(1, "Asia", 4));
        d_map = new Map(l_continent,new ArrayList<>(),new ArrayList<>(),"test.map");
        String[] l_args = new String[]{"editcountry","-add","test","Asia"};
        List<Country> l_expectedCountries = new ArrayList<>();
        l_expectedCountries.add(new Country(1, "test", l_continent.get(0)));
        d_playSetUpPhase.getD_gamePhase().handleEditCountriesOrContinentOrNeighbor(l_args,d_map);
        Assertions.assertEquals(l_expectedCountries, d_map.getD_countries());
    }

    @Test
    public void handleEditContinentTest() {
        d_map = new Map(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),"test.map");
        String[] l_args = new String[]{"editcontinent","-add","test","3"};
        List<Continent> l_expectedContinent = new ArrayList<>();
        l_expectedContinent.add(new Continent(1, "test",3));
        d_playSetUpPhase.getD_gamePhase().handleEditCountriesOrContinentOrNeighbor(l_args,d_map);
        Assertions.assertEquals(l_expectedContinent, d_map.getD_continents());
    }



    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }


}
