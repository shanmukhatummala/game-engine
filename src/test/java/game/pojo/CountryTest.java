package game.pojo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Method;

/**
 * CountryTest is a test class for the Country POJO
 *
 * @author Shanmukha
 */
class CountryTest {

    /** Tests the constructors, getters, equals and hashcode methods */
    @Test
    public void shouldPassAllPojoTests() {
        final Class<Country> l_classUnderTest = Country.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR, Method.GETTER, Method.EQUALS)
                .areWellImplemented();
    }
}
