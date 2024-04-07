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
     * Processes the provided arguments to add or remove a country from the game p_map.
     *
     * @param p_args The command arguments specifying the action (add or remove), country ID, and
     *     continent ID.
     * @param p_map The game p_map on which the command will be executed.
     */
    public static void process(String[] p_args, Map p_map) {
        for (int l_i = 0; l_i < p_args.length; ) {
            if (p_args[l_i].equals(ADD_PARAM)) {
                processAddCommand(p_args[l_i + 1], p_args[l_i + 2], p_map);
                l_i += 3; // Skip the next two p_args
            } else if (p_args[l_i].equals(REMOVE_PARAM)) {
                processRemoveCommand(p_args[l_i + 1], p_map);
                l_i += 2; // Skip the next arg
            } else {
                // Handle unrecognized parameters if needed
                System.out.println("Unrecognized parameter: " + p_args[l_i]);
            }
        }
    }

    /**
     * Adds a country to the game p_map with the specified country ID and continent ID.
     *
     * @param p_country_name The Name of the country to be added.
     * @param p_continent_name The Name of the continent to which the country will belong.
     * @param p_map The game p_map on which the command will be executed.
     */
    private static void processAddCommand(
            String p_country_name, String p_continent_name, Map p_map) {
        if (StringUtils.isNotEmpty(p_country_name) && StringUtils.isNotEmpty(p_continent_name)) {

            Continent l_linked_continent =
                    p_map.getD_continents().stream()
                            .filter(Objects::nonNull)
                            .filter(c -> Objects.equals(c.getD_name(), p_continent_name))
                            .findFirst()
                            .orElse(null);

            if (l_linked_continent == null) {
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                        "Continent Name: " + p_continent_name + " does not exist!");
                return;
            }

            try {
                Integer l_country_id = p_map.getMaxCountryId() + 1;

                p_map.addCountry(new Country(l_country_id, p_country_name, l_linked_continent));

                GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Country Added Successfully!");
            } catch (Exception e) {
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Country could not be added!");
            }
        }
    }

    /**
     * Removes a country from the game p_map with the specified country ID.
     *
     * @param p_country_name The Name of the country to be removed.
     * @param p_map The game p_map on which the command will be executed.
     */
    private static void processRemoveCommand(String p_country_name, Map p_map) {
        p_map.removeCountry(p_country_name);
    }
}
