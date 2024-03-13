package game.order;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

import java.util.*;

/** This class test the DeployOrder class */
public class DeployOrderTest {

    DeployOrder d_deployOrder;
    private List<Country> d_Countries;

    /** Setting up the deployOrder object for testing */
    @BeforeEach
    void setUp() {
        Continent l_continent = new Continent(1, "continent1", 5);
        d_Countries = createCountries(l_continent);
        d_deployOrder = new DeployOrder(d_Countries.get(0), new Player(), 4);
    }

    /**
     * This method create the list of countries used in testing
     *
     * @param p_continent continent object
     * @return List of countries
     */
    private List<Country> createCountries(Continent p_continent) {
        List<Country> l_countries = new ArrayList<>();
        l_countries.add(new Country(1, "country1", p_continent, new ArrayList<>(), 0));
        l_countries.add(new Country(2, "country2", p_continent, new ArrayList<>(), 0));
        return l_countries;
    }

    /** Tests the constructors, getters */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<DeployOrder> l_classUnderTest = DeployOrder.class;
        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER)
                .areWellImplemented();
    }

    /**
     * This test tests the execute method by asserting the changes in the army count of the
     * countries after the call of the execute method
     */
    @Test
    public void executeTest() {
        d_deployOrder.execute();
        int l_expectedArmyCount = 4;
        Assertions.assertEquals(l_expectedArmyCount, d_Countries.get(0).getD_armyCount());
    }
}
