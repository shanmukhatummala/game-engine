package game.commands;

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
        inputCommand = "gameplayer -add playerName -add playername2";
        parser = new CommandParser(new StartUpCommandValidator());
        inputCommand2 = "showmap";
    }

    @Test
    public void parseTest() {
        List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add("-add");
        expectedArgs.add("playerName");
        expectedArgs.add("-add");
        expectedArgs.add("playername2");
        String expectedCommandType = "gameplayer";
        Command command = parser.parse(inputCommand);
        Assertions.assertEquals(expectedCommandType, command.getCommandType());
        Assertions.assertArrayEquals(expectedArgs.toArray(), command.getArgs().toArray());
    }
}
