package game;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class GameEngineTest {

    @Test
    void testIsValidForSimpleAddGamePlayerCommand() {

        String gamePlayerCommand = "gameplayer -add player1";

        boolean isValid = GameEngine.isValidGamePlayerCommand(gamePlayerCommand.split(" "));

        assertThat(isValid, is(true));
    }

    @Test
    void testIsValidForSimpleRemoveGamePlayerCommand() {

        String gamePlayerCommand = "gameplayer -remove player1";

        boolean isValid = GameEngine.isValidGamePlayerCommand(gamePlayerCommand.split(" "));

        assertThat(isValid, is(true));
    }

    @Test
    void testIsValidForGamePlayerCommand() {

        String gamePlayerCommand = "gameplayer -add player1 -add player2 -remove player3 -add player4 -remove player5";

        boolean isValid = GameEngine.isValidGamePlayerCommand(gamePlayerCommand.split(" "));

        assertThat(isValid, is(true));
    }

    @Test
    void testIsValidForIncorrectGamePlayerCommand() {

        String gamePlayerCommand = "gameplayer -add -remove player1";

        boolean isValid = GameEngine.isValidGamePlayerCommand(gamePlayerCommand.split(" "));

        assertThat(isValid, is(false));
    }

    @Test
    void testIsValidForIncorrectGamePlayerCommandEndingWithOption() {

        String gamePlayerCommand = "gameplayer -add player1 -remove";

        boolean isValid = GameEngine.isValidGamePlayerCommand(gamePlayerCommand.split(" "));

        assertThat(isValid, is(false));
    }
}
