package game.pojo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import java.util.*;

/** PlayerTest is a test class for the Player POJO */
class PlayerTest {
    private Player d_player;
    Scanner d_scannerPlayer1;

    /** Setting up the Countries list and the players */
    @BeforeEach
    void setUp() {
        Continent l_continent = new Continent(1, "continent1", 5);
        List<Country> l_playerCountries = createCountries(l_continent);
        d_player = new Player("player1", l_playerCountries);
    }

    /**
     * This method create the countries
     *
     * @param p_continent Continent
     * @return List of countries
     */
    private List<Country> createCountries(Continent p_continent) {
        List<Country> l_countries = new ArrayList<>();
        l_countries.add(new Country(1, "country1", p_continent, new ArrayList<>(), 0));
        l_countries.add(new Country(2, "country2", p_continent, new ArrayList<>(), 0));
        l_countries.add(new Country(3, "country3", p_continent, new ArrayList<>(), 0));
        return l_countries;
    }

    /** Tests the constructors, getters, equals and hashcode methods */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Player> l_classUnderTest = Player.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER, Method.EQUALS, Method.HASH_CODE)
                .areWellImplemented();
    }

    //    /**
    //     * This method test the issue order method by providing user deploy command and assert if
    // the
    //     * player reinforcements has been affected correctly
    //     */
    //    @Test
    //    void issue_order() {
    //        String l_userCommandPlayer1 = "deploy country1 4\n";
    //        ByteArrayInputStream l_inputStream =
    //                new ByteArrayInputStream(l_userCommandPlayer1.getBytes());
    //        d_scannerPlayer1 = new Scanner(l_inputStream);
    //        Player.Scanner = d_scannerPlayer1;
    //        d_player.issue_order();
    //        Assertions.assertEquals(
    //                1,
    //                d_player.getD_reinforcements(),
    //                "Reinforcements should be reduced by the army number deployed.");
    //    }

    //    /**
    //     * This method test the issue order method by providing user two deploy command one is
    // wrong
    //     * anthe other is correct and assert if the player reinforcements has been affected
    // correctly
    //     * after the right command
    //     */
    //    @Test
    //    void issue_orderNotValidOnce() {
    //        String l_userCommandPlayer1 = "test country3 3\ndeploy country3 4\n";
    //        ByteArrayInputStream l_inputStream =
    //                new ByteArrayInputStream(l_userCommandPlayer1.getBytes());
    //        d_scannerPlayer1 = new Scanner(l_inputStream);
    //        Player.Scanner = d_scannerPlayer1;
    //        d_player.issue_order();
    //        Assertions.assertEquals(
    //                1,
    //                d_player.getD_reinforcements(),
    //                "Reinforcements should be reduced by the army number deployed.");
    //    }
    //
    //    /**
    //     * This method test the next_order method in the player class it assert if the next order
    // will
    //     * return an object after the issue order method
    //     */
    //    @Test
    //    void next_order() {
    //        String l_userCommandPlayer1 = "deploy country2 1\n";
    //        ByteArrayInputStream l_inputStream =
    //                new ByteArrayInputStream(l_userCommandPlayer1.getBytes());
    //        d_scannerPlayer1 = new Scanner(l_inputStream);
    //        Player.Scanner = d_scannerPlayer1;
    //        d_player.issue_order();
    //        Order l_player1Order = d_player.next_order();
    //        Assertions.assertNotNull(l_player1Order, "An order should exist");
    //    }
}
