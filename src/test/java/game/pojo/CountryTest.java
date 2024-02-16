package game.pojo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

class CountryTest {

    @Test
    public void shouldPassAllPojoTests() {
        final Class<Country> classUnderTest = Country.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.CONSTRUCTOR,
                        Method.GETTER,
                        Method.EQUALS,
                        Method.HASH_CODE)
                .areWellImplemented();
    }
}