package game.map.MapManipulation;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.util.ValidationHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

public class EditCountryProcessor {
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

    private static void processRemoveCommand(String p_country_id, Map map) {
        if (ValidationHelper.isInteger(p_country_id)) {
            map.removeCountry(Integer.parseInt(p_country_id));
        }
    }
}
