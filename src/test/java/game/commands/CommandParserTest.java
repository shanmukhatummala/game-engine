package game.commands;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandParserTest {

    CommandParser parser;
    String inputCommand;
    String inputCommand1;
    String inputCommand2;

    @BeforeEach
    void setUp() {
        inputCommand = "gameplayer -add playerName -add playername2";
        inputCommand1 = "gameplayer -remove playerName";
        parser = new CommandParser();
        inputCommand2 = "deploy country1 4";
    }

    @Test
    public void parseStartUpGameplayerTest() {
        Map l_map = new Map();
        GameEngine l_engine = new GameEngine(l_map);
        List<String> expectedArgs1 = new ArrayList<>();
        expectedArgs1.add("-add");
        expectedArgs1.add("playerName");
        List<String> expectedArgs2 = new ArrayList<>();
        expectedArgs2.add("-add");
        expectedArgs2.add("playername2");
        List<String> expectedArgs3 = new ArrayList<>();
        expectedArgs3.add("-remove");
        expectedArgs3.add("playerName");
        String expectedCommandType = "gameplayer";
        List<Command> l_commandList = parser.parse(l_engine, inputCommand);

        l_map.addPlayer("playerName");
        List<Command> l_commandList2 = parser.parse(l_engine, inputCommand1);

        Assertions.assertEquals(expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(expectedArgs1, l_commandList.get(0).getArgs());

        Assertions.assertEquals(expectedCommandType, l_commandList.get(1).getCommandType());
        Assertions.assertEquals(expectedArgs2, l_commandList.get(1).getArgs());

        Assertions.assertEquals(expectedCommandType, l_commandList2.get(0).getCommandType());
        Assertions.assertEquals(expectedArgs3, l_commandList2.get(0).getArgs());
    }

    @Test
    public void parseDeployTest() {
        Continent l_continent = new Continent(1, "continent1", 5);
        List<Country> l_countries = new ArrayList<>();
        l_countries.add(new Country(1, "country1", l_continent, new ArrayList<>(), 0));
        l_countries.add(new Country(2, "country2", l_continent, new ArrayList<>(), 0));
        l_countries.add(new Country(3, "country3", l_continent, new ArrayList<>(), 0));

        Player l_player = new Player("player1", l_countries);

        List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add("country1");
        expectedArgs.add("4");

        l_player.setD_reinforcements(5);

        String expectedCommandType = "deploy";
        Command l_command = parser.parse(l_player, inputCommand2);

        Assertions.assertEquals(expectedCommandType, l_command.getCommandType());
        Assertions.assertEquals(expectedArgs, l_command.getArgs());
    }
}
