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

    DeployOrder d_deployOrder;
    private List<Country> d_Countries;
    @BeforeEach
    void setUp() {
        Continent l_continent = new Continent(1, "continent1", 5);
        d_Countries = createCountries(l_continent);
        d_deployOrder = new DeployOrder(d_Countries.get(0),4 );

    }
    private List<Country> createCountries(Continent p_continent) {
        List<Country> l_countries = new ArrayList<>();
        l_countries.add(new Country(1, "country1", p_continent, new ArrayList<>(), 0));
        l_countries.add(new Country(2, "country2", p_continent, new ArrayList<>(), 0));
        return l_countries;
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
        d_deployOrder.execute();
        int l_expectedArmyCount = 4;
        Assertions.assertEquals(l_expectedArmyCount,d_Countries.get(0).getD_armyCount());

    }


}
