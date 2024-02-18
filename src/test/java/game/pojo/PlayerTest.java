package game.pojo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.*;
import org.junit.jupiter.api.Assertions;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * PlayerTest is a test class for the Player POJO
 */
class PlayerTest {
    private List<Player> d_players;
    private Map<String,List<Country>> d_playersCountries;

    @BeforeEach
    void setUp() {
        List<Continent> l_continents = createContinents();
        d_playersCountries = createCountries(l_continents);
        d_players = createPLayers();

    }

    private List<Player> createPLayers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1",d_playersCountries.get("player1")));
        players.add(new Player("player2",d_playersCountries.get("player2")));
        return players;
    }
    private List<Continent> createContinents() {
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, "continent1", 5));
        continents.add(new Continent(2, "continent2", 4));
        return continents;
    }

    private Map<String,List<Country>> createCountries(List<Continent> p_continents) {
        List<Country> l_countriesPlayer1 = new ArrayList<>();
        List<Country> l_countriesPlayer2 = new ArrayList<>();
        Map<String, List<Country>> l_playersCountries = new HashMap<>();
        for (int i = 1; i <= 8; i++) {
            Continent continent = (i <= 4) ? p_continents.get(0) : p_continents.get(1);
            if(i<5){
                l_countriesPlayer1.add(new Country(i, "country" + i, continent, new ArrayList<>(), 0));
            }else{
                l_countriesPlayer2.add(new Country(i, "country" + i, continent, new ArrayList<>(), 0));
            }
        }
        l_playersCountries.put("player1", l_countriesPlayer1);
        l_playersCountries.put("player2", l_countriesPlayer2);
        return l_playersCountries;
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * <p>Tests the constructors, getters, equals and hashcode methods</p>
     */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Player> l_classUnderTest = Player.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR,
                        Method.GETTER,
                        Method.EQUALS,
                        Method.HASH_CODE)
                .areWellImplemented();
    }

    @Test
    void issue_order() {
        Scanner d_scannerPlayer1;
        String l_userCommandPlayer1 = "deploy country1 4\n";
        ByteArrayInputStream l_inputStream = new ByteArrayInputStream(l_userCommandPlayer1.getBytes());
        d_scannerPlayer1 = new Scanner(l_inputStream);
        Player.Scanner = d_scannerPlayer1;
        d_players.get(0).issue_order();
        Assertions.assertEquals(1, d_players.get(0).getD_reinforcements(), "Reinforcements should be reduced by the army number deployed.");
        Order l_player1Order = d_players.get(0).next_order();
        Assertions.assertNotNull(l_player1Order,"An order should exist");
    }

    @Test
    void issue_orderNotValidOnce() {
        Scanner d_scannerPlayer2;
        String l_userCommandPlayer2 = "test country5 3\ndeploy country5 4\n";
        ByteArrayInputStream l_inputStream = new ByteArrayInputStream(l_userCommandPlayer2.getBytes());
        d_scannerPlayer2 = new Scanner(l_inputStream);
        Player.Scanner = d_scannerPlayer2;
        d_players.get(1).issue_order();
        Assertions.assertEquals(1, d_players.get(1).getD_reinforcements(), "Reinforcements should be reduced by the army number deployed.");
        Order l_player2Order = d_players.get(1).next_order();
        Assertions.assertNotNull(l_player2Order,"An order should exist");
    }

}