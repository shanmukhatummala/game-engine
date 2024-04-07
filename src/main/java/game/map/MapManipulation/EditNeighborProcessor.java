package game.map.MapManipulation;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

import game.GameEngine;
import game.map.Map;

import java.util.Objects;

/**
 * The EditNeighborProcessor class is responsible for processing commands related to editing
 * neighbors on the game map. It provides methods to add and remove neighbors for a specific country
 * on the map.
 */
public class EditNeighborProcessor {

    /**
     * Processes the provided arguments to add or remove neighbors for a country on the game p_map.
     *
     * @param p_args The command arguments specifying the action (add or remove) and the country and
     *     its neighbor.
     * @param p_map The game p_map on which the command will be executed.
     */
    public static void process(String[] p_args, Map p_map) {
        for (int l_i = 0; l_i < p_args.length; ) {
            if (p_args[l_i].equals(ADD_PARAM)) {
                processAddCommand(p_args[l_i + 1], p_args[l_i + 2], p_map);
                l_i += 3; // Skip the next two p_args
            } else if (p_args[l_i].equals(REMOVE_PARAM)) {
                processRemoveCommand(p_args[l_i + 1], p_args[l_i + 2], p_map);
                l_i += 3; // Skip the next arg
            } else {
                // Handle unrecognized parameters if needed
                System.out.println("Unrecognized parameter: " + p_args[l_i]);
            }
        }
    }

    /**
     * Adds a neighbor country to the specified country on the game p_map.
     *
     * @param p_country_name The Name of the country to which the neighbor will be added.
     * @param p_neighbor_country_name The Name of the neighbor country to be added.
     * @param p_map The game p_map on which the command will be executed.
     */
    private static void processAddCommand(
            String p_country_name, String p_neighbor_country_name, Map p_map) {

        Integer p_country_id = p_map.getCountryIdForCountryName(p_country_name);
        Integer p_neighbor_country_id = p_map.getCountryIdForCountryName(p_neighbor_country_name);

        if (Objects.nonNull(p_country_id) && Objects.nonNull(p_neighbor_country_id)) {
            p_map.addNeighborToCountry(p_country_id, p_neighbor_country_id);
        } else {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Invalid Country/Countries");
        }
    }

    /**
     * Removes a neighbor country from the specified country on the game p_map.
     *
     * @param p_country_name The Name of the country from which the neighbor will be removed.
     * @param p_neighbor_country_name The Name of the neighbor country to be removed.
     * @param p_map The game p_map on which the command will be executed.
     */
    private static void processRemoveCommand(
            String p_country_name, String p_neighbor_country_name, Map p_map) {

        Integer p_country_id = p_map.getCountryIdForCountryName(p_country_name);
        Integer p_neighbor_country_id = p_map.getCountryIdForCountryName(p_neighbor_country_name);

        if (Objects.nonNull(p_country_id) && Objects.nonNull(p_neighbor_country_id)) {
            p_map.removeNeighborFromCountry(p_country_id, p_neighbor_country_id);
        } else {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Invalid Country/Countries");
        }
    }
}
