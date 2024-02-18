package game.map.MapManipulation;

import game.map.Map;

import static game.constants.MapManipulation.*;

/**
 * The MapManipulator class is responsible for processing commands related to map manipulation. It
 * delegates the processing of specific commands to corresponding processor classes.
 */
public class MapManipulator {

  /**
   * Processes the given command based on the arguments and the game map.
   *
   * @param args The command arguments.
   * @param map The game map on which the command will be executed.
   */
  public void processCommand(String[] args, Map map) {
    switch (args[0]) {
      case EDIT_CONTINENT:
        EditContinentProcessor.process(args, map);
        break;
      case EDIT_COUNTRY:
        EditCountryProcessor.process(args, map);
        break;
      case EDIT_NEIGHBOR:
        EditNeighborProcessor.process(args, map);
        break;
      default:
        System.out.println("Invalid command please try again");
    }
  }
}
