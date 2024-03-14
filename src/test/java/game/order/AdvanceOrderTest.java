package game.order;

import game.pojo.Country;
import game.pojo.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdvanceOrderTest {

    @Test
    public void testValidMethodWhenDestinationIsNotValid() {
        Player d_player1 = new Player("player1");
        Country destination = null;

        Advance_order advanceOrder = new Advance_order(destination, null, d_player1, 0);
        assertFalse(advanceOrder.valid());
    }



}
