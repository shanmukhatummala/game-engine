package game.mapfile;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/** Test suite for reader package */
@Suite
@SelectClasses({
    ConquestFileReaderTest.class,
    ConquestFileWriterTest.class,
    MapFileReaderTest.class,
    MapFileWriterTest.class
})
public class MapFileTestSuite {}
