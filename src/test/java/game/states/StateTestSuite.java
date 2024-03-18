package game.states;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AssignResourcesPhaseTest.class, EditMapPhaseTest.class, PlaySetupPhaseTest.class})
public class StateTestSuite {}
