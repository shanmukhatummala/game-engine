package game.commands;

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
        inputCommand = "gameplayer -add playerName -add playername2 -remove playername1";
        inputCommand1 = "deploy country1 4";
        parser = new CommandParser();
        inputCommand2 = "editcontinent -add Europe 3";
    }

    @Test
    public void parseStartUpGameplayerTest() {

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
        List<Command> l_commandList = parser.parse(inputCommand);

        Assertions.assertEquals(expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(expectedArgs1, l_commandList.get(0).getArgs());

        Assertions.assertEquals(expectedCommandType, l_commandList.get(1).getCommandType());
        Assertions.assertEquals(expectedArgs2, l_commandList.get(1).getArgs());

        Assertions.assertEquals(expectedCommandType, l_commandList.get(2).getCommandType());
        Assertions.assertEquals(expectedArgs3, l_commandList.get(2).getArgs());
    }

    @Test
    public void parseDeployTest() {

        List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add("country1");
        expectedArgs.add("4");

        String expectedCommandType = "deploy";
        List<Command> l_commandList = parser.parse(inputCommand1);
        Command l_command = l_commandList.get(0);

        Assertions.assertEquals(expectedCommandType, l_command.getCommandType());
        Assertions.assertEquals(expectedArgs, l_command.getArgs());
    }

    @Test
    public void parseEditcontinentTest() {

        List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add("-add");
        expectedArgs.add("Europe");
        expectedArgs.add("3");

        String expectedCommandType = "editcontinent";
        List<Command> l_commandList = parser.parse(inputCommand2);

        Assertions.assertEquals(expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(expectedArgs, l_commandList.get(0).getArgs());
    }
}
