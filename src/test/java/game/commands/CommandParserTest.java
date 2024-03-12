package game.commands;

import game.GameEngine;
import game.map.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandParserTest {

    CommandParser parser;
    String inputCommand;
    String inputCommand2;

    @BeforeEach
    void setUp() {
        inputCommand = "gameplayer -add playerName -add playername2 -remove playername1";
        parser = new CommandParser();
        inputCommand2 = "showmap";
    }

    @Test
    public void parseTest() {
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
        expectedArgs3.add("playername1");
        String expectedCommandType = "gameplayer";
        List<Command> l_commandList = parser.parse(l_engine, inputCommand);

        Assertions.assertEquals(expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(expectedArgs1, l_commandList.get(0).getArgs());

        Assertions.assertEquals(expectedCommandType, l_commandList.get(1).getCommandType());
        Assertions.assertEquals(expectedArgs2, l_commandList.get(1).getArgs());

        Assertions.assertEquals(expectedCommandType, l_commandList.get(2).getCommandType());
        Assertions.assertEquals(expectedArgs3, l_commandList.get(2).getArgs());
    }
}
