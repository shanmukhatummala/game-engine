package game.pojo;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/** Test suite for pojo package */
@Suite
@SelectClasses({ContinentTest.class, CountryTest.class, PlayerTest.class})
public class PojoTestSuite {}
