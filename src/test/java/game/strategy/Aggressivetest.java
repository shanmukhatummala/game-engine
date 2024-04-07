package game.strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import org.junit.Before;
import org.junit.Test;
import strategy.Aggressive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class Aggressivetest {
    private Aggressive d_aggressiveStrategy;
    private Map d_mockedMap;
    private Player d_mockedPlayer;

    @Before
    public void setUp() {
        d_aggressiveStrategy = Aggressive.getAggressiveStrategy();
        d_mockedMap = new Map(); // Assuming a default map
        d_mockedPlayer = new Player("Player"); // Assuming a default player
    }


    @Test
    public void testSingletonPattern() {
        Aggressive l_aggressive1 = Aggressive.getAggressiveStrategy();
        Aggressive l_aggressive2 = Aggressive.getAggressiveStrategy();
        assertSame(l_aggressive1, l_aggressive2);
    }


    @Test
    public void testDeployCommandOnStrongest() {
        // Create necessary objects for the test
        Continent l_continent = new Continent();
        Country l_country = new Country(1, "Country1", l_continent, new HashSet<>(), 100);
        Player l_player = new Player("Player1", new ArrayList<>(List.of(l_country)));
        l_player.setD_reinforcements(10);
        Command l_command = d_aggressiveStrategy.deployCommandOnStrongest(l_player);
        // Check if the returned command is of type "deploy"
        assertEquals("deploy", Command.getD_commandType());
        // Check if the command arguments are as expected
        List<String> args = l_command.getD_args();
        assertEquals(2, args.size()); // Expecting two arguments
        assertEquals("Country1", args.get(0)); // Expecting country name
        assertEquals("10", args.get(1)); // Expecting reinforcements count
    }


    @Test
    public void testAttackCommandOnStrongest() {
        Continent l_continent = new Continent();
        Country l_strongestCountry = new Country(1, "Country1", l_continent, new HashSet<>(), 100); // Mocking a country with 3 armies
        Country l_neighborCountry = new Country(2,"NeighborCountry", l_continent,new HashSet<>(),50); // Mocking a neighboring country with 2 armies
        Player l_player = new Player("Player1", new ArrayList<>(List.of(l_neighborCountry)));
        l_strongestCountry.addNeighbor(l_neighborCountry.getD_id());
        d_mockedMap.getD_continents().add(l_continent);
        d_mockedMap.getD_countries().add(l_strongestCountry);
        d_mockedMap.getD_countries().add(l_neighborCountry);
        d_mockedMap.getD_players().add(d_mockedPlayer);
        d_mockedPlayer.getD_countries().add(l_strongestCountry);
        d_mockedMap.getD_players().add(l_player);
        Command l_attackCommand = d_aggressiveStrategy.attackCommandOnStrongest(d_mockedMap, d_mockedPlayer);

        assertEquals("advance Country1 NeighborCountry 100", l_attackCommand.toString());
    }

    @Test
    public void testMoveCommandToReinforce() {

            Continent l_continent = new Continent();
            Country l_strongestCountry = new Country(1, "Country1", l_continent, new HashSet<>(), 100); // Mocking a country with 3 armies
            Country l_neighborCountry = new Country(2,"NeighborCountry", l_continent,new HashSet<>(),50); // Mocking a neighboring country with 2 armies
            Player l_player = new Player("Player", new ArrayList<>(List.of(l_neighborCountry)));
            l_strongestCountry.addNeighbor(l_neighborCountry.getD_id());;
            d_mockedMap.getD_countries().add(l_strongestCountry);
            d_mockedMap.getD_countries().add(l_neighborCountry);
            d_mockedMap.getD_players().add(d_mockedPlayer);
            d_mockedPlayer.getD_countries().add(l_strongestCountry);
            d_mockedPlayer.getD_countries().add(l_neighborCountry);
            d_mockedMap.getD_players().add(l_player);
            Command l_moveCommand = d_aggressiveStrategy.moveCommandToReinforce(d_mockedMap, d_mockedPlayer);
            assertEquals("advance NeighborCountry Country1 50", l_moveCommand.toString());
        }

}


