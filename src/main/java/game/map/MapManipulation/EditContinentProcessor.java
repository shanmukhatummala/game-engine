package game.map.MapManipulation;

import game.map.Map;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

import game.pojo.Continent;
import game.pojo.Country;
import game.util.ValidationHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The EditContinentProcessor class is responsible for processing commands related to editing
 * continents on the game map. It provides methods to add or remove continents from the map.
 */
public class EditContinentProcessor {

  /**
   * Processes the provided arguments to add or remove a continent from the game map.
   *
   * @param args The command arguments specifying the action (add or remove), continent ID, and
   *     continent name.
   * @param map The game map on which the command will be executed.
   */
  public static void process(String[] args, Map map) {
    for (int i = 1; i < args.length; ) {
      if (args[i].equals(ADD_PARAM)) {
        processAddCommand(args[i + 1], args[i + 2], map);
        i += 3; // Skip the next two args
      } else if (args[i].equals(REMOVE_PARAM)) {
        processRemoveCommand(args[i + 1], map);
        i += 2; // Skip the next arg
      } else {
        // Handle unrecognized parameters if needed
        System.out.println("Unrecognized parameter: " + args[i]);
      }
    }
  }

  /**
   * Adds a continent to the game map with the specified continent ID and name.
   *
   * @param p_continent_name The ID of the continent to be added.
   * @param p_continent_bonus The name of the continent to be added.
   * @param map The game map on which the command will be executed.
   */
  private static void processAddCommand(
      String p_continent_name, String p_continent_bonus, Map map) {
    if (StringUtils.isNotEmpty(p_continent_name) && ValidationHelper.isInteger(p_continent_bonus)) {
      try {
        map.addContinent(
            new Continent(
                map.getMaxContinentId() + 1,
                p_continent_name,
                Integer.parseInt(p_continent_bonus))); // check ID
        System.out.println("Continent Added Successfully!");
      } catch (Exception e) {
        System.out.println("Continent could not be added!");
      }
    }
  }

  /**
   * Removes a continent from the game map with the specified continent ID.
   *
   * @param p_continent_name The Name of the continent to be removed.
   * @param map The game map on which the command will be executed.
   */
  private static void processRemoveCommand(String p_continent_name, Map map) {
    if (StringUtils.isNotEmpty(p_continent_name)) {
      map.removeContinent(p_continent_name);
    }
  }
}
