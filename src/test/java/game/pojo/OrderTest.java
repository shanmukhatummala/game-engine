package game.pojo;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class OrderTest {

    @Test
    public void shouldPassAllPojoTests() {
        final Class<Order> l_classUnderTest = Order.class;

        assertPojoMethodsFor(l_classUnderTest)
                .testing(Method.CONSTRUCTOR,
                        Method.GETTER)
                .areWellImplemented();
    }
}
