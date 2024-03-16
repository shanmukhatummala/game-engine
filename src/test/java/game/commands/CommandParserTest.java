package game.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandParserTest {

    String d_inputCommand;
    String d_inputCommand1;
    String d_inputCommand2;
    String d_inputCommand3;

    @BeforeEach
    void setUp() {
        d_inputCommand = "gameplayer -add playerName -add playername2 -remove playername1";
        d_inputCommand1 = "deploy country1 4";
        d_inputCommand2 = "editcontinent -add Europe 3";
        d_inputCommand3 = "commit";
    }

    @Test
    public void parseStartUpGameplayerTest() {

        List<String> l_expectedArgs1 = new ArrayList<>();
        l_expectedArgs1.add("-add");
        l_expectedArgs1.add("playerName");
        List<String> l_expectedArgs2 = new ArrayList<>();
        l_expectedArgs2.add("-add");
        l_expectedArgs2.add("playername2");
        List<String> l_expectedArgs3 = new ArrayList<>();
        l_expectedArgs3.add("-remove");
        l_expectedArgs3.add("playername1");
        String l_expectedCommandType = "gameplayer";
        List<Command> l_commandList = CommandParser.parse(d_inputCommand);

        Assertions.assertEquals(l_expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(l_expectedArgs1, l_commandList.get(0).getArgs());

        Assertions.assertEquals(l_expectedCommandType, l_commandList.get(1).getCommandType());
        Assertions.assertEquals(l_expectedArgs2, l_commandList.get(1).getArgs());

        Assertions.assertEquals(l_expectedCommandType, l_commandList.get(2).getCommandType());
        Assertions.assertEquals(l_expectedArgs3, l_commandList.get(2).getArgs());
    }

    @Test
    public void parseDeployTest() {

        List<String> l_expectedArgs = new ArrayList<>();
        l_expectedArgs.add("country1");
        l_expectedArgs.add("4");

        String l_expectedCommandType = "deploy";
        List<Command> l_commandList = CommandParser.parse(d_inputCommand1);
        Command l_command = l_commandList.get(0);

        Assertions.assertEquals(l_expectedCommandType, l_command.getCommandType());
        Assertions.assertEquals(l_expectedArgs, l_command.getArgs());
    }

    @Test
    public void parseEditcontinentTest() {

        List<String> l_expectedArgs = new ArrayList<>();
        l_expectedArgs.add("-add");
        l_expectedArgs.add("Europe");
        l_expectedArgs.add("3");

        String l_expectedCommandType = "editcontinent";
        List<Command> l_commandList = CommandParser.parse(d_inputCommand2);

        Assertions.assertEquals(l_expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(l_expectedArgs, l_commandList.get(0).getArgs());
    }

    @Test
    public void parseCommitCommandTest() {

        List<String> l_expectedArgs = new ArrayList<>();

        String l_expectedCommandType = "commit";
        List<Command> l_commandList = CommandParser.parse(d_inputCommand3);

        Assertions.assertEquals(l_expectedCommandType, l_commandList.get(0).getCommandType());
        Assertions.assertEquals(l_expectedArgs, l_commandList.get(0).getArgs());
    }
}
