package game;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

/** GameEngineTest is a test class for the GameEngine class */
public class GameEngineTest {

    /** Test isValidGamePlayerCommand method for a valid -add command */
    @Test
    void testIsValidForSimpleAddGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -add player1";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(true));
    }

    /** Test isValidGamePlayerCommand method for a valid -remove command */
    @Test
    void testIsValidForSimpleRemoveGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -remove player1";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(true));
    }

    /** Test isValidGamePlayerCommand method for multiple -add and -remove options */
    @Test
    void testIsValidForGamePlayerCommand() {

        String l_gamePlayerCommand =
                "gameplayer -add player1 -add player2 -remove player3 -add player4 -remove player5";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(true));
    }

    /** Test isValidGamePlayerCommand method for an invalid command */
    @Test
    void testIsValidForIncorrectGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -add -remove player1";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(false));
    }

    /** Test isValidGamePlayerCommand method for an invalid command ending with -add or -remove */
    @Test
    void testIsValidForIncorrectGamePlayerCommandEndingWithOption() {

        String l_gamePlayerCommand = "gameplayer -add player1 -remove";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(false));
    }
}
