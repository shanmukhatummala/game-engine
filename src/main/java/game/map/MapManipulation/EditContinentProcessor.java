package game.map.MapManipulation;

import static game.constants.MapManipulation.ADD_PARAM;
import static game.constants.MapManipulation.REMOVE_PARAM;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.util.ValidationHelper;

import org.apache.commons.lang3.StringUtils;

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
        for (int l_i = 1; l_i < args.length; ) {
            if (args[l_i].equals(ADD_PARAM)) {
                processAddCommand(args[l_i + 1], args[l_i + 2], map);
                l_i += 3; // Skip the next two args
            } else if (args[l_i].equals(REMOVE_PARAM)) {
                processRemoveCommand(args[l_i + 1], map);
                l_i += 2; // Skip the next arg
            } else {
                // Handle unrecognized parameters if needed
                System.out.println("Unrecognized parameter: " + args[l_i]);
            }
        }
    }

    /**
     * Adds a continent to the game p_map with the specified continent ID and name.
     *
     * @param p_continent_name The Name of the continent to be added.
     * @param p_continent_bonus The Bonus of the continent to be added.
     * @param p_map The game p_map on which the command will be executed.
     */
    private static void processAddCommand(
            String p_continent_name, String p_continent_bonus, Map p_map) {
        if (StringUtils.isNotEmpty(p_continent_name)
                && ValidationHelper.isInteger(p_continent_bonus)) {
            try {
                p_map.addContinent(
                        new Continent(
                                p_map.getMaxContinentId() + 1,
                                p_continent_name,
                                Integer.parseInt(p_continent_bonus))); // check ID
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Continent Added Successfully!");
            } catch (Exception e) {
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Continent could not be added!");
            }
        }
    }

    /**
     * Removes a continent from the game p_map with the specified continent ID.
     *
     * @param p_continent_name The Name of the continent to be removed.
     * @param p_map The game p_map on which the command will be executed.
     */
    private static void processRemoveCommand(String p_continent_name, Map p_map) {
        if (StringUtils.isNotEmpty(p_continent_name)) {
            p_map.removeContinent(p_continent_name);
        }
    }
}
