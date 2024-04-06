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

import java.io.*;
import java.util.*;

/** Class testing the PlaySetupPhase class */
public class PlaySetupPhaseTest {

    /** The data member represent the base path of the resources folder in the test */
    private String d_path;

    /** Object of the game engine in order to use the phase data member */
    private GameEngine d_playSetUpPhase;

    /** This data member represent the map that we will use in the tests */
    private Map d_map;

    /** This data member is used for the output comparison */
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /** Sets up the required objects for the execution of tests */
    @BeforeEach
    public void setUp() {
        d_playSetUpPhase = new GameEngine();
        d_playSetUpPhase.setD_gamePhase(new PlaySetupPhase());
        d_map = new Map();
        d_path = "src/test/resources/";
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * This method create a test map dummy with countries and contents for to test with
     *
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

        l_expectedContinents.add(l_continent1);

        l_expectedCountries.add(l_country1);
        l_expectedCountries.add(l_country2);
        l_expectedCountries.add(l_country3);
        l_expectedCountries.add(l_country4);
        return new Map(l_expectedContinents, l_expectedCountries, l_expectedPlayers, "new.map");
    }

    /**
     * This method create a test list player dummy to be the expected values to test with;
     *
     * @return Map object
     */
    private List<Player> createPlayersList() {

        List<Player> l_expectedPlayers = new ArrayList<>();
        l_expectedPlayers.add(new Player("player1", new ArrayList<>()));
        l_expectedPlayers.add(new Player("player2", new ArrayList<>()));
        return l_expectedPlayers;
    }

    /**
     * Tests the handleLoadMap in the right phase by comparing that the right map is loaded into the
     * d_map object by comparing the countries and contents and players and name.
     */
    @Test
    public void handleLoadMapTest() {
        Map l_expectedMap = createObjectsToAssert();
        List<String> l_commandArgs = new ArrayList<>();
        l_commandArgs.add("new.map");
        Command l_command = new Command("loadmap", l_commandArgs);
        d_playSetUpPhase.getD_gamePhase().handleLoadMap(l_command, d_map, d_playSetUpPhase, d_path);
        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedMap.getD_countries(), l_countries);
        Assertions.assertEquals(l_expectedMap.getD_continents(), l_continents);
        Assertions.assertEquals(l_expectedMap.getD_players(), l_players);
        Assertions.assertEquals(d_map.getD_mapName(), l_expectedMap.getD_mapName());
    }

    /**
     * Tests the handleGamePlayer in the right phase by comparing that the right player are added
     * into the d_map object by comparing the expected players.
     */
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
        l_command.add(new Command("gameplayer", l_command1Args));
        l_command.add(new Command("gameplayer", l_command2Args));
        d_playSetUpPhase.getD_gamePhase().handleGamePlayer(l_command, d_map);
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedPlayers, l_players);
    }

    /**
     * Tests the handleGamePlayer in the right phase by comparing that the remaining player are
     * equal to the expected players.
     */
    @Test
    public void handleGamePlayerRemoveTest() {
        List<Player> l_expectedPlayers = new ArrayList<>();
        l_expectedPlayers.add(new Player("player1"));
        List<String> l_commandArgs = new ArrayList<>();
        l_commandArgs.add("-remove");
        l_commandArgs.add("player2");
        List<Command> l_command = new ArrayList<>();
        l_command.add(new Command("gameplayer", l_commandArgs));
        d_map = new Map(new ArrayList<>(), new ArrayList<>(), createPlayersList(), "testmap");
        d_playSetUpPhase.getD_gamePhase().handleGamePlayer(l_command, d_map);
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedPlayers, l_players);
    }

    /**
     * This method is for testing the handleEditMap in the right phase and compare the method is
     * changing the phase object to the correct kind of phase object.
     */
    @Test
    public void handleEditMapTest() {
        List<String> l_args = new ArrayList<>();
        l_args.add("new.map");
        Command l_command = new Command("editmap", l_args);
        d_playSetUpPhase.getD_gamePhase().handleEditMap(d_playSetUpPhase, l_command, d_map, d_path);
        String l_expectedPhase = "EditMapPhase";
        Assertions.assertEquals(
                l_expectedPhase, d_playSetUpPhase.getD_gamePhase().getClass().getSimpleName());
    }

    /**
     * This method is for testing the handleSaveMapCommand in the wrong phase method and compare the
     * printed output.
     */
    @Test
    public void handleSaveMapCommandTest() {
        String l_expectedOutput =
                "Invalid Command in state PlaySetupPhase you can't save a map here";
        d_playSetUpPhase
                .getD_gamePhase()
                .handleSaveMapCommand(
                        new Command("", new ArrayList<>()), d_map, d_playSetUpPhase, d_path);
        Assertions.assertEquals(l_expectedOutput, outputStreamCaptor.toString().trim());
    }

    /**
     * This method is for testing the handleSaveMapType in the wrong phase method and compare the
     * printed output.
     */
    @Test
    public void handleSaveMapTypeTest() {
        String l_expectedOutput =
                "Invalid Command in state PlaySetupPhase you can't save a map here";
        d_playSetUpPhase
                .getD_gamePhase()
                .handleSaveMapType(
                        new Command("", new ArrayList<>()), d_map, d_playSetUpPhase, d_path);
        Assertions.assertEquals(l_expectedOutput, outputStreamCaptor.toString().trim());
    }

    /**
     * This method is for testing the handleValidateMap in the wrong phase method and compare the
     * printed output
     */
    @Test
    public void handleValidateMapTest() {
        String l_expectedOutput =
                "Invalid Command in state PlaySetupPhase you can't Validate a map here";
        d_playSetUpPhase.getD_gamePhase().handleValidateMap(d_map);
        Assertions.assertEquals(l_expectedOutput, outputStreamCaptor.toString().trim());
    }

    /**
     * This method is for testing the handleEditCountriesOrContinentOrNeighbor in the wrong phase
     * method and compare the printed output
     */
    @Test
    public void handleEditCountriesOrContinentOrNeighborTest() {
        String l_expectedOutput =
                "Invalid Command in state PlaySetupPhase you can't edit map while not in the edit mode phase";
        d_playSetUpPhase
                .getD_gamePhase()
                .handleEditCountriesOrContinentOrNeighbor(new String[] {}, d_map);
        Assertions.assertEquals(l_expectedOutput, outputStreamCaptor.toString().trim());
    }

    /** */
    @Test
    public void handleLoadGameTest() throws Exception {
        Map l_expectedMap = createObjectsToAssert();
        l_expectedMap.addPlayer(new Player("player1", new ArrayList<>()));
        l_expectedMap.addPlayer(new Player("player2", new ArrayList<>()));
        l_expectedMap.assignCountries(l_expectedMap.getD_players(), l_expectedMap.getD_countries());
        l_expectedMap.getD_countries().get(0).setD_armyCount(3);
        String l_fileName = "loadgametest.bin";
        List<Player> l_expectedPlayersLeftToIssueOrder = l_expectedMap.getD_players();
        Integer l_expectedCurrentPlayerIndex = 1;
        try (ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(d_path + l_fileName))) {
            out.writeObject(l_expectedMap);
            out.writeObject(l_expectedPlayersLeftToIssueOrder);
            out.writeObject(l_expectedCurrentPlayerIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> l_commandArgs = new ArrayList<>();
        l_commandArgs.add(l_fileName);
        Command l_command = new Command("loadgame", l_commandArgs);

        List<Player> l_playersLeftToIssueOrder =
                d_playSetUpPhase
                        .getD_gamePhase()
                        .handleLoadGame(
                                d_playSetUpPhase, d_map, d_path + l_command.getD_args().get(0));
        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();
        List<Player> l_players = d_map.getD_players();

        Assertions.assertEquals(l_expectedMap.getD_countries(), l_countries);
        Assertions.assertEquals(l_expectedMap.getD_continents(), l_continents);
        Assertions.assertEquals(l_expectedMap.getD_players(), l_players);
        Assertions.assertEquals(l_expectedMap.getD_mapName(), d_map.getD_mapName());
        Assertions.assertEquals(l_expectedPlayersLeftToIssueOrder, l_playersLeftToIssueOrder);
        Assertions.assertEquals(
                l_expectedCurrentPlayerIndex, d_playSetUpPhase.getD_currentPlayerIndex());
    }

    /**
     * This method run after every test and is for cleaning the resources used in the tests like
     * returning the System.setOut to it's default value.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }
}
