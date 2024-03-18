package game.pojo;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ContinentTest.class, CountryTest.class, PlayerTest.class})
public class PojoTestSuite {}
