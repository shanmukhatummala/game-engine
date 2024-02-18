package game.map.MapManipulation;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.util.ValidationHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

/**
 * The EditCountryProcessor class is responsible for processing commands related to editing
 * countries on the game map. It provides methods to add or remove countries from the map.
 */
public class EditCountryProcessor {

  /**
   * Processes the provided arguments to add or remove a country from the game map.
   *
   * @param args The command arguments specifying the action (add or remove), country ID, and
   *     continent ID.
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
   * Adds a country to the game map with the specified country ID and continent ID.
   *
   * @param p_country_id The ID of the country to be added.
   * @param p_continent_id The ID of the continent to which the country will belong.
   * @param map The game map on which the command will be executed.
   */
  private static void processAddCommand(String p_country_id, String p_continent_id, Map map) {
    if (ValidationHelper.isInteger(p_country_id) && ValidationHelper.isInteger(p_continent_id)) {

      Continent l_linked_continent =
          map.getContinents().stream()
              .filter(Objects::nonNull)
              .filter(c -> c.getId() == Integer.parseInt(p_continent_id))
              .findFirst()
              .orElse(null);

      if (l_linked_continent == null) {
        System.out.println("Continent ID: " + p_continent_id + " does not exist!");
        return;
      }

      map.addCountryToContinent(Integer.parseInt(p_country_id), Integer.parseInt(p_continent_id));

      map.addCountry(
          new Country(Integer.parseInt(p_country_id), p_continent_id, l_linked_continent));
    }
  }

  /**
   * Removes a country from the game map with the specified country ID.
   *
   * @param p_country_id The ID of the country to be removed.
   * @param map The game map on which the command will be executed.
   */
  private static void processRemoveCommand(String p_country_id, Map map) {
    if (ValidationHelper.isInteger(p_country_id)) {
      map.removeCountry(Integer.parseInt(p_country_id));
    }
  }
}
