package game.map;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/** Test suite for map package */
@Suite
@SelectClasses({
    MapHelperTest.class,
    MapLoaderTest.class,
    MapSaverTest.class,
    MapShowerTest.class,
    MapTest.class,
    MapValidatorTest.class
})
public class MapTestSuite {}
