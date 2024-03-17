package game.map.MapManipulation;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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
     * @param p_country_name The Name of the country to be added.
     * @param p_continent_name The Name of the continent to which the country will belong.
     * @param map The game map on which the command will be executed.
     */
    private static void processAddCommand(String p_country_name, String p_continent_name, Map map) {
        if (StringUtils.isNotEmpty(p_country_name) && StringUtils.isNotEmpty(p_continent_name)) {

            Continent l_linked_continent =
                    map.getD_continents().stream()
                            .filter(Objects::nonNull)
                            .filter(c -> Objects.equals(c.getD_name(), p_continent_name))
                            .findFirst()
                            .orElse(null);

            if (l_linked_continent == null) {
                GameEngine.d_logEntryBuffer.addLogEntry("Continent Name: " + p_continent_name + " does not exist!");
                return;
            }

            try {
                Integer l_country_id = map.getMaxCountryId() + 1;

                map.addCountry(new Country(l_country_id, p_country_name, l_linked_continent));

                map.addCountryToContinent(l_linked_continent.getD_id(), l_country_id);

                GameEngine.d_logEntryBuffer.addLogEntry("Country Added Successfully!");
            } catch (Exception e) {
                GameEngine.d_logEntryBuffer.addLogEntry("Country could not be added!");
            }
        }
    }

    /**
     * Removes a country from the game map with the specified country ID.
     *
     * @param p_country_name The Name of the country to be removed.
     * @param map The game map on which the command will be executed.
     */
    private static void processRemoveCommand(String p_country_name, Map map) {
        map.removeCountry(p_country_name);
    }
}
