package game.states;

import static game.pojo.Player.Card.AIRLIFT;
import static game.pojo.Player.Card.BLOCKADE;
import static game.pojo.Player.Card.BOMB;
import static game.pojo.Player.Card.DIPLOMACY;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/** Test class for AssignResourcesPhase class */
public class AssignResourcesPhaseTest {

    private AssignResourcesPhase d_assignResourcesPhase;
    private Set<Player> d_players;
    private Player d_player1;
    private Player d_player2;

    @BeforeEach
    public void setUp() {
        d_assignResourcesPhase = new AssignResourcesPhase();
        d_players = new HashSet<>();
        d_player1 = new Player("d_player1");
        d_player2 = new Player("d_player2");
        d_players.add(d_player1);
        d_players.add(d_player1);
        d_players.add(d_player2);
    }

    @Test
    public void shouldAssignCardToPlayers() {

        d_assignResourcesPhase.assignRandomCard(d_players);

        assertThat(d_players.size(), equalTo(2));
        assertThat(d_player1.getD_cards().size(), equalTo(1));
        assertThat(
                d_player1.getD_cards().get(0),
                anyOf(is(BOMB), is(BLOCKADE), is(AIRLIFT), is(DIPLOMACY)));
        assertThat(d_player2.getD_cards().size(), equalTo(1));
        assertThat(
                d_player2.getD_cards().get(0),
                anyOf(is(BOMB), is(BLOCKADE), is(AIRLIFT), is(DIPLOMACY)));
    }
}
