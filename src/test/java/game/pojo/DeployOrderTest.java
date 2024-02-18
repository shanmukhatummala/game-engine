package game.pojo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import java.io.ByteArrayInputStream;
import java.util.*;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;


public class DeployOrderTest {


    private List<Player> d_players;
    private Map<String,List<Country>> d_playersCountries;
    Scanner d_scannerPlayer1;
    Scanner d_scannerPlayer2;
    @BeforeEach
    void setUp() {
        List<Continent> l_continents = createContinents();
        d_playersCountries = createCountries(l_continents);
        d_players = createPLayers();
        String l_userCommandPlayer1 = "deploy country1 4\n";
        String l_userCommandPlayer2 = "deploy country3 3\n";
        ByteArrayInputStream l_inputStream = new ByteArrayInputStream(l_userCommandPlayer1.getBytes());
        d_scannerPlayer1 = new Scanner(l_inputStream);
        l_inputStream = new ByteArrayInputStream(l_userCommandPlayer2.getBytes());
        d_scannerPlayer2 = new Scanner(l_inputStream);
    }

    private List<Player> createPLayers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1",d_playersCountries.get("player1"),0));
        players.add(new Player("player2",d_playersCountries.get("player2"),0));
        return players;
    }
    private List<Continent> createContinents() {
        List<Continent> continents = new ArrayList<>();
        continents.add(new Continent(1, "continent1", 5));
        return continents;
    }

    private Map<String,List<Country>> createCountries(List<Continent> p_continents) {
        List<Country> l_countriesPlayer1 = new ArrayList<>();
        List<Country> l_countriesPlayer2 = new ArrayList<>();
        Map<String, List<Country>> l_playersCountries = new HashMap<>();
        Continent continent = p_continents.get(0);
        l_countriesPlayer1.add(new Country(1, "country1", continent, new ArrayList<>(), 0));
        l_countriesPlayer1.add(new Country(2, "country2", continent, new ArrayList<>(), 0));
        l_countriesPlayer2.add(new Country(3, "country3", continent, new ArrayList<>(), 0));
        l_playersCountries.put("player1", l_countriesPlayer1);
        l_playersCountries.put("player2", l_countriesPlayer2);
        return l_playersCountries;
    }
    void tearDown() {}


    @Test
    public void shouldPassAllPojoTests() {
        final Class<DeployOrder> l_classUnderTest = DeployOrder.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR,
                        Method.GETTER)
                .areWellImplemented();
    }


    @Test
    public void executeTest() {
        Player.Scanner = d_scannerPlayer1;
        Player l_player1 = d_players.get(0);
        l_player1.issue_order();
        Order l_player1Order = l_player1.next_order();
        l_player1Order.execute();
        int l_expectedArmyCount = 4;
        Country l_country = l_player1.getD_countries().get(0);
        Assertions.assertEquals(l_expectedArmyCount,l_country.getD_armyCount());
    }


}
