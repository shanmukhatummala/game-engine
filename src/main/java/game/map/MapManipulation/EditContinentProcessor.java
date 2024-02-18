package game.map.MapManipulation;

import game.map.Map;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

import game.pojo.Continent;
import game.pojo.Country;
import game.util.ValidationHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class EditContinentProcessor {

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

  private static void processAddCommand(String p_continent_id, String p_continent_value, Map map) {
    if (ValidationHelper.isInteger(p_continent_id) && StringUtils.isNotEmpty(p_continent_value)) {
      map.addContinent(new Continent(Integer.parseInt(p_continent_id), p_continent_value));
    }
  }


  private static void processRemoveCommand(String p_continent_id, Map map) {
    if (ValidationHelper.isInteger(p_continent_id)) {
      map.removeContinent(Integer.parseInt(p_continent_id));
    }
  }
}
