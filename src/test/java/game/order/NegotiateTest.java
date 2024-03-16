package game.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import game.map.Map;
import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test for Negotiate class */
public class NegotiateTest {

    private Player d_initiator;
    private Player d_targetPlayer;
    private Map d_map;

    @BeforeEach
    void setUp() {
        d_initiator = new Player("initiator", null);
        d_targetPlayer = new Player("targetPlayer", null);
        d_map = new Map();
    }

    /** Tests that negotiation cannot be executed without having a diplomacy card */
    @Test
    public void shouldNotExecuteNegotiationWithoutDiplomacyCard() {
        Negotiate negotiate = new Negotiate(d_initiator, d_targetPlayer, new Map());
        assertThat(negotiate.valid(), equalTo(false));
    }

    @Test
    public  void shouldNotExecuteNegotiationWithoutValidTargetPlayer() {
        d_initiator.addCard(Player.Card.DIPLOMACY);
        Negotiate negotiate = new Negotiate(d_initiator, d_targetPlayer, new Map());
        assertThat(negotiate.valid(), equalTo(false));
    }


}
