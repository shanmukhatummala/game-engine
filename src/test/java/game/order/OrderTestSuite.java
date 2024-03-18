package game.order;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/** Test suite for order package */
@Suite
@SelectClasses({
    AdvanceOrderTest.class,
    AirliftTest.class,
    BlockadeTest.class,
    BombTest.class,
    DeployTest.class,
    NegotiateTest.class,
    OrderTest.class
})
public class OrderTestSuite {}
