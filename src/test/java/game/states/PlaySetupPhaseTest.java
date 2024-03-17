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



public class PlaySetupPhaseTest {


    private String d_path;
    private GameEngine d_playSetUpPhase;
    private Map d_map;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /** Sets up the required objects for the execution of tests */
    @BeforeEach
    public void setUp() {
        d_playSetUpPhase = new GameEngine();
        d_playSetUpPhase.setGamePhase(new PlaySetupPhase());
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

    private List<Player> createPlayersList() {

        List<Player> l_expectedPlayers = new ArrayList<>();
        l_expectedPlayers.add(new Player("player1",new ArrayList<>()));
        l_expectedPlayers.add(new Player("player2",new ArrayList<>()));
        return l_expectedPlayers;
    }

    /** Tests if assigning a random card to players is working as expected */
    @Test
    public void handleLoadMapTest() {
        Map l_expectedMap = createObjectsToAssert();
        List<String> l_commandArgs = new ArrayList<>();
        l_commandArgs.add("new.map");
        Command l_command = new Command("loadmap",l_commandArgs);
        d_playSetUpPhase.getGamePhase().handleLoadMap(l_command,d_map,d_playSetUpPhase, d_path);
        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedMap.getD_countries(), l_countries);
        Assertions.assertEquals(l_expectedMap.getD_continents(),l_continents);
        Assertions.assertEquals(l_expectedMap.getD_players(),l_players);
        Assertions.assertEquals(d_map.getD_mapName(), l_expectedMap.getD_mapName());
    }



    @Test
    public void handleGamePlayerTest() {
        List<Player> l_expectedPlayers = createPlayersList();
        List<String> l_command1Args = new ArrayList<>();
        l_command1Args.add("-add");
        l_command1Args.add("player1");
        List<String> l_command2Args = new ArrayList<>();
        l_command2Args.add("-add");
        l_command2Args.add("player2");
        List<Command> l_command = new ArrayList<>();
        l_command.add(new Command("gameplayer",l_command1Args));
        l_command.add(new Command("gameplayer", l_command2Args));
        d_playSetUpPhase.getGamePhase().handleGamePlayer(l_command,d_map);
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedPlayers,l_players);

    }
    @Test
    public void handleGamePlayerRemoveTest() {
        List<Player> l_expectedPlayers = new ArrayList<>();
        l_expectedPlayers.add(new Player("player1"));
        List<String> l_commandArgs = new ArrayList<>();
        l_commandArgs.add("-remove");
        l_commandArgs.add("player2");
        List<Command> l_command = new ArrayList<>();
        l_command.add(new Command("gameplayer",l_commandArgs));
        d_map = new Map(new ArrayList<>(),new ArrayList<>(), createPlayersList(),"testmap");
        d_playSetUpPhase.getGamePhase().handleGamePlayer(l_command,d_map);
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedPlayers,l_players);

    }


    @Test
    public void handleEditMapTest(){
        List<String> l_args = new ArrayList<>();
        l_args.add("new.map");
        Command l_command = new Command("editmap",l_args);
        d_playSetUpPhase.getGamePhase().handleEditMap(d_playSetUpPhase,l_command,d_map,d_path);
        String l_expectedPhase = "EditMapPhase";
        Assertions.assertEquals(l_expectedPhase, d_playSetUpPhase.getGamePhase().getClass().getSimpleName());
    }




    @Test
    public void handleSaveMapTest(){
        String l_expectedOutput = "Invalid Command in state PlaySetupPhase you can't save a map here";
        d_playSetUpPhase.getGamePhase().handleSaveMap(new Command("",new ArrayList<>()),d_map,d_playSetUpPhase,d_path);
        Assertions.assertEquals(l_expectedOutput,outputStreamCaptor.toString().trim());
    }

    @Test
    public void handleValidateMapTest(){
        String l_expectedOutput = "Invalid Command in state PlaySetupPhase you can't Validate a map here";
        d_playSetUpPhase.getGamePhase().handleValidateMap(d_map);
        Assertions.assertEquals(l_expectedOutput,outputStreamCaptor.toString().trim());
    }

    @Test
    public void handleEditCountriesOrContinentOrNeighborTest(){
        String l_expectedOutput = "Invalid Command in state PlaySetupPhase you can't edit map while not in the edit mode phase";
        d_playSetUpPhase.getGamePhase().handleEditCountriesOrContinentOrNeighbor(new String[]{},d_map);
        Assertions.assertEquals(l_expectedOutput,outputStreamCaptor.toString().trim());
    }


    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

}
