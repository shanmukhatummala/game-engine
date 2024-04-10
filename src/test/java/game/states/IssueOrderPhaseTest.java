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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Test class for IssueOrderPhase */
public class IssueOrderPhaseTest {

    /** The data member represent the base path of the resources folder in the test */
    private String d_path;

    /** Object of the game engine in order to use the phase data member */
    private GameEngine d_playSetUpPhase;

    /** This data member represent the map that we will use in the tests */
    private Map d_map;

    /** Sets up the required objects for the execution of tests */
    @BeforeEach
    public void setUp() {
        d_playSetUpPhase = new GameEngine();
        d_playSetUpPhase.setD_gamePhase(new IssueOrderPhase());
        d_map = new Map();
        d_path = "src/test/resources/";
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
        Player l_player1 = new Player("player1", new ArrayList<>());
        Player l_player2 = new Player("player2", new ArrayList<>());
        l_country1.addNeighbors(Arrays.asList(2, 3, 4));
        l_country2.addNeighbors(List.of(1));
        l_country3.addNeighbors(List.of(2));
        l_country4.addNeighbors(List.of(2));
        l_expectedPlayers.add(l_player1);
        l_expectedPlayers.add(l_player2);
        l_expectedContinents.add(l_continent1);
        l_expectedCountries.add(l_country1);
        l_expectedCountries.add(l_country2);
        l_expectedCountries.add(l_country3);
        l_expectedCountries.add(l_country4);
        Map l_map =
                new Map(l_expectedContinents, l_expectedCountries, l_expectedPlayers, "new.map");
        l_map.assignCountries(l_expectedPlayers, l_expectedCountries);
        l_map.getD_countries().get(0).setD_armyCount(3);
        return l_map;
    }

    /** Tests the save game feature */
    @Test
    public void handleSaveGameTest() {
        Map l_expectedMap = createObjectsToAssert();
        List<String> l_commandArgs = new ArrayList<>();
        Integer l_expectedCurrentPlayerIndex = 1;
        l_commandArgs.add("savegametest.bin");
        Command l_command = new Command("savegame", l_commandArgs);
        d_playSetUpPhase
                .getD_gamePhase()
                .handleSaveGame(
                        l_expectedMap,
                        l_expectedMap.getD_players(),
                        l_expectedCurrentPlayerIndex,
                        d_path + l_command.getD_args().get(0));
        List<Player> l_playersLeftToIssueOrder = null;
        Integer l_currentPlayerIndex = null;
        try (ObjectInputStream in =
                new ObjectInputStream(new FileInputStream(d_path + l_command.getD_args().get(0)))) {
            d_map = (Map) in.readObject();
            l_playersLeftToIssueOrder = (List<Player>) in.readObject();
            l_currentPlayerIndex = (Integer) in.readObject();
        } catch (IOException | ClassNotFoundException l_e) {
            System.out.println(l_e.getMessage());
        }

        List<Continent> l_continents = d_map.getD_continents();
        List<Country> l_countries = d_map.getD_countries();
        List<Player> l_players = d_map.getD_players();
        Assertions.assertEquals(l_expectedMap.getD_countries(), l_countries);
        Assertions.assertEquals(l_expectedMap.getD_continents(), l_continents);
        Assertions.assertEquals(l_expectedMap.getD_players(), l_players);
        Assertions.assertEquals(l_expectedMap.getD_mapName(), d_map.getD_mapName());
        Assertions.assertEquals(l_expectedMap.getD_players(), l_playersLeftToIssueOrder);
        Assertions.assertEquals(l_expectedCurrentPlayerIndex, l_currentPlayerIndex);
    }

    /**
     * This method run after every test and is for cleaning the resources used in the tests like
     * deleting the file.
     */
    @AfterEach
    public void tearDown() {
        String l_pathToSavedOutcome = d_path + "savegametest.bin";
        Path l_pathToFile = Paths.get(l_pathToSavedOutcome);
        try {
            Files.delete(l_pathToFile);
            System.out.println("File deleted successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
