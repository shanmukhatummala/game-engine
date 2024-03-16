package game.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import game.map.Map;
import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

/** Test for Negotiate class */
public class NegotiateTest {

    private Player d_initiator;
    private Player d_targetPlayer;
    private Map d_map;

    /**
     * Sets up the test environment before each test method is executed. Initializes the test data
     * including the initiator, target player, map.
     */
    @BeforeEach
    void setUp() {
        d_initiator = new Player("initiator", null);
        d_targetPlayer = new Player("targetPlayer", null);
        d_map = new Map();
        d_map.getD_players().add(d_initiator);
    }

    /** Tests if constructor and getters are implemented properly */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Negotiate> l_classUnderTest = Negotiate.class;
        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER)
                .areWellImplemented();
    }

    /** Tests that negotiation cannot be executed without having a diplomacy card */
    @Test
    public void shouldNotExecuteNegotiationWithoutDiplomacyCard() {
        Negotiate negotiate = new Negotiate(d_initiator, d_targetPlayer, d_map);
        assertThat(negotiate.valid(), equalTo(false));
    }

    /** Tests that negotiation cannot be executed without having a valid target player */
    @Test
    public void shouldNotExecuteNegotiationWithoutValidTargetPlayer() {
        d_initiator.addCard(Player.Card.DIPLOMACY);
        Negotiate negotiate = new Negotiate(d_initiator, d_targetPlayer, d_map);
        assertThat(negotiate.valid(), equalTo(false));
    }

    /**
     * Tests that the Negotiation is successful when the target player is valid and the initiator
     * has a DIPLOMACY card
     */
    @Test
    public void shouldExecuteNegotiationWithValidDiplomacyCardAndTargetPlayer() {
        d_map.getD_players().add(d_targetPlayer);
        d_initiator.addCard(Player.Card.DIPLOMACY);
        Negotiate negotiate = new Negotiate(d_initiator, d_targetPlayer, d_map);
        assertThat(negotiate.valid(), equalTo(true));
    }
}
