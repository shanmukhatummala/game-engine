package game.commands;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandParserTest {

    CommandParser parser ;
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
        Command command = parser.parse(inputCommand);
        System.out.println(command.toString());
        command = parser.parse(inputCommand2);
        System.out.println(command.toString());
    }

}
