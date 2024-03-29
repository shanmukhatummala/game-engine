package game;

import game.commands.CommandsTestSuite;
import game.map.MapTestSuite;
import game.order.OrderTestSuite;
import game.pojo.PojoTestSuite;
import game.reader.ReaderTestSuite;
import game.states.StateTestSuite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/** Test suite covering all tests */
@Suite
@SelectClasses({
    CommandsTestSuite.class,
    MapTestSuite.class,
    OrderTestSuite.class,
    PojoTestSuite.class,
    ReaderTestSuite.class,
    StateTestSuite.class
})
public class FullTestSuite {}
