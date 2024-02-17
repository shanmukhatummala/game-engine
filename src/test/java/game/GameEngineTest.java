package game;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class GameEngineTest {

    @Test
    void testIsValidForSimpleAddGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -add player1";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(true));
    }

    @Test
    void testIsValidForSimpleRemoveGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -remove player1";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(true));
    }

    @Test
    void testIsValidForGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -add player1 -add player2 -remove player3 -add player4 -remove player5";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(true));
    }

    @Test
    void testIsValidForIncorrectGamePlayerCommand() {

        String l_gamePlayerCommand = "gameplayer -add -remove player1";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(false));
    }

    @Test
    void testIsValidForIncorrectGamePlayerCommandEndingWithOption() {

        String l_gamePlayerCommand = "gameplayer -add player1 -remove";

        boolean l_isValid = GameEngine.isValidGamePlayerCommand(l_gamePlayerCommand.split(" "));

        assertThat(l_isValid, is(false));
    }
}
