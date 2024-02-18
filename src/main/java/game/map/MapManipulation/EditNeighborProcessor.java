package game.map.MapManipulation;

import game.map.Map;
import game.pojo.Continent;
import game.util.ValidationHelper;
import org.apache.commons.lang3.StringUtils;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

public class EditNeighborProcessor {
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

  private static void processAddCommand(
      String p_country_id, String p_neighbor_country_id, Map map) {
    if (ValidationHelper.isInteger(p_country_id)
        && ValidationHelper.isInteger(p_neighbor_country_id)) {
      map.addNeighborToCountry(
          Integer.parseInt(p_country_id), Integer.parseInt(p_neighbor_country_id));
    }
  }

  private static void processRemoveCommand(
      String p_country_id, String p_neighbor_country_id, Map map) {
    if (ValidationHelper.isInteger(p_country_id)
        && ValidationHelper.isInteger(p_neighbor_country_id)) {
      map.removeNeighborFromCountry(
          Integer.parseInt(p_country_id), Integer.parseInt(p_neighbor_country_id));
    }
  }
}
