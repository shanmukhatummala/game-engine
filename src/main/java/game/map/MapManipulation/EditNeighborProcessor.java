package game.map.MapManipulation;

import game.map.Map;
import game.pojo.Continent;
import game.util.ValidationHelper;
import org.apache.commons.lang3.StringUtils;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

/**
 * The EditNeighborProcessor class is responsible for processing commands related to editing
 * neighbors on the game map. It provides methods to add and remove neighbors for a specific country
 * on the map.
 */
public class EditNeighborProcessor {

  /**
   * Processes the provided arguments to add or remove neighbors for a country on the game map.
   *
   * @param args The command arguments specifying the action (add or remove) and the country and its
   *     neighbor.
   * @param map The game map on which the command will be executed.
   */
  public static void process(String[] args, Map map) {
    for (int i = 1; i < args.length; ) {
      if (args[i].equals(ADD_PARAM)) {
        processAddCommand(args[i + 1], args[i + 2], map);
        i += 3; // Skip the next two args
      } else if (args[i].equals(REMOVE_PARAM)) {
        processRemoveCommand(args[i + 1], args[i + 2], map);
        i += 3; // Skip the next arg
      } else {
        // Handle unrecognized parameters if needed
        System.out.println("Unrecognized parameter: " + args[i]);
      }
    }
  }

  /**
   * Adds a neighbor country to the specified country on the game map.
   *
   * @param p_country_id The ID of the country to which the neighbor will be added.
   * @param p_neighbor_country_id The ID of the neighbor country to be added.
   * @param map The game map on which the command will be executed.
   */
  private static void processAddCommand(
      String p_country_id, String p_neighbor_country_id, Map map) {
    if (ValidationHelper.isInteger(p_country_id)
        && ValidationHelper.isInteger(p_neighbor_country_id)) {
      map.addNeighborToCountry(
          Integer.parseInt(p_country_id), Integer.parseInt(p_neighbor_country_id));
    }
  }

  /**
   * Removes a neighbor country from the specified country on the game map.
   *
   * @param p_country_id The ID of the country from which the neighbor will be removed.
   * @param p_neighbor_country_id The ID of the neighbor country to be removed.
   * @param map The game map on which the command will be executed.
   */
  private static void processRemoveCommand(
      String p_country_id, String p_neighbor_country_id, Map map) {
    if (ValidationHelper.isInteger(p_country_id)
        && ValidationHelper.isInteger(p_neighbor_country_id)) {
      map.removeNeighborFromCountry(
          Integer.parseInt(p_country_id), Integer.parseInt(p_neighbor_country_id));
    }
  }
}
