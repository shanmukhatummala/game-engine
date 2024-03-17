package game.pojo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import static java.util.Arrays.asList;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.order.Bomb;
import game.order.Deploy;
import game.order.Order;
import game.util.IssueOrderHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import java.util.HashSet;

/** PlayerTest is a test class for the Player POJO */
class PlayerTest {

    private Map d_map;
    private Player d_player1;
    private Country d_country1;
    private Country d_country2;
    private Country d_country3;

    /** Setting up the Countries list and the players */
    @BeforeEach
    void setUp() {
        Continent l_continent = new Continent(1, "continent1", 5);
        d_country1 = new Country(1, "country1", l_continent, new HashSet<>(), 0);
        d_country2 = new Country(2, "country2", l_continent, new HashSet<>(), 0);
        d_country3 = new Country(3, "country3", l_continent, new HashSet<>(), 0);
        d_player1 = new Player("player1");
        d_map = new Map();
        d_map.getD_continents().add(l_continent);
        d_map.getD_countries().addAll(asList(d_country1, d_country2, d_country3));
        d_map.getD_players().add(d_player1);
    }

    /** Tests if the orders are added to the order list */
    @Test
    void shouldIssueOrders() {
        d_player1.getD_countries().add(d_country1);
        d_country1.addNeighbor(2);
        String l_userCommandPlayer1 = "deploy country1 4";
        String l_userCommandPlayer2 = "bomb country2";
        Command l_deployCommand = CommandParser.parse(l_userCommandPlayer1).get(0);
        Command l_bombCommand = CommandParser.parse(l_userCommandPlayer2).get(0);

        IssueOrderHelper.setCommand(l_deployCommand);
        IssueOrderHelper.setMap(d_map);
        d_player1.issue_order();
        IssueOrderHelper.setCommand(l_bombCommand);
        IssueOrderHelper.setMap(d_map);
        d_player1.issue_order();

        assertThat(d_player1.getD_orderList().size(), equalTo(2));

        Order l_firstOrder = d_player1.getD_orderList().poll();
        Order l_secondOrder = d_player1.getD_orderList().poll();

        assertThat(l_firstOrder, instanceOf(Deploy.class));
        assertThat(l_firstOrder.getD_initiator(), equalTo(d_player1));
        assertThat(((Deploy) l_firstOrder).getD_destination(), equalTo(d_country1));
        assertThat(((Deploy) l_firstOrder).getD_armyNumber(), equalTo(4));
        assertThat(l_secondOrder, instanceOf(Bomb.class));
        assertThat(l_secondOrder.getD_initiator(), equalTo(d_player1));
        assertThat(((Bomb) l_secondOrder).getD_target(), equalTo(d_country2));
    }

    /** Tests if the next_order returns the correct order */
    @Test
    void shouldReturnNextOrder() {
        d_player1.getD_countries().add(d_country1);
        d_country1.addNeighbor(2);
        String l_userCommandPlayer1 = "deploy country1 4";
        String l_userCommandPlayer2 = "bomb country2";
        Command l_deployCommand = CommandParser.parse(l_userCommandPlayer1).get(0);
        Command l_bombCommand = CommandParser.parse(l_userCommandPlayer2).get(0);

        IssueOrderHelper.setCommand(l_deployCommand);
        IssueOrderHelper.setMap(d_map);
        d_player1.issue_order();
        IssueOrderHelper.setCommand(l_bombCommand);
        IssueOrderHelper.setMap(d_map);
        d_player1.issue_order();

        Order l_firstOrder = d_player1.next_order();
        Order l_secondOrder = d_player1.next_order();
        Order l_thirdOrder = d_player1.next_order();

        assertThat(l_firstOrder, instanceOf(Deploy.class));
        assertThat(l_firstOrder.getD_initiator(), equalTo(d_player1));
        assertThat(((Deploy) l_firstOrder).getD_destination(), equalTo(d_country1));
        assertThat(((Deploy) l_firstOrder).getD_armyNumber(), equalTo(4));
        assertThat(l_secondOrder, instanceOf(Bomb.class));
        assertThat(l_secondOrder.getD_initiator(), equalTo(d_player1));
        assertThat(((Bomb) l_secondOrder).getD_target(), equalTo(d_country2));
        assertThat(l_thirdOrder, nullValue());
    }

    /** Tests the constructors, getters, equals and hashcode methods */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Player> l_classUnderTest = Player.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER, Method.EQUALS, Method.HASH_CODE)
                .areWellImplemented();
    }
}
