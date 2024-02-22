package game.pojo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

/** OrderTest is a test class for the Order POJO */
public class OrderTest {

  /** Tests the constructors, getters */
  @Test
  public void shouldPassAllPojoTests() {
    final Class<Order> l_classUnderTest = Order.class;

    assertPojoMethodsFor(l_classUnderTest)
        .testing(Method.CONSTRUCTOR, Method.GETTER)
        .areWellImplemented();
  }
}
