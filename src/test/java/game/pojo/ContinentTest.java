package game.pojo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

/**
 * ContinentTest is a test class for the Continent POJO
 *
 * @author Shanmukha
 */
class ContinentTest {

    /** Tests the constructors, getters, equals and hashcode methods */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Continent> l_classUnderTest = Continent.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER, Method.EQUALS, Method.HASH_CODE)
                .areWellImplemented();
    }
}
