/**
 * The Aggressive class represents an aggressive strategy for a player in a game. This strategy
 * involves deploying troops to the strongest country, attacking the strongest country on the map,
 * and then moving troops to centralize them. The strategy follows a sequence of actions: deploy,
 * attack, move, and then repeats. This class is a singleton, ensuring that only one instance of the
 * Aggressive strategy exists.
 *
 * @author Naveen Rayapudi
 */
package game.strategy;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the Aggressive Player Strategy
 */
public class Aggressive extends PlayerStrategy {

    /** The singleton instance of the Aggressive strategy. */
    private static Aggressive d_aggressiveStrategy;

    /**
     * Returns the singleton instance of the Aggressive strategy. If the instance does not exist, it
     * is created.
     *
     * @return the singleton instance of the Aggressive strategy
     */
    public static Aggressive getAggressiveStrategy() {
        if (d_aggressiveStrategy == null) {
            Aggressive.d_aggressiveStrategy = new Aggressive();
        }
        return d_aggressiveStrategy;
    }

    /** Private constructor to enforce the singleton pattern. */
    public Aggressive() {}

    /**
     * Creates an order for the player based on the current state of the game. The order sequence is
     * deploy, attack, move, and then repeats.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the order is being created
     * @return the command to be executed
     */
    @Override
    public Command createOrder(Map p_map, Player p_player) {

        boolean l_deployed = p_player.getD_deployed();
        boolean l_attacked = p_player.getD_attacked();
        boolean l_moved = p_player.getD_moved();

        if (!l_deployed) {
            p_player.setD_deployed(true);
            return deployCommandOnStrongest(p_player);
        } else if (!l_attacked) {
            p_player.setD_attacked(true);
            return attackCommandOnStrongest(p_map, p_player);
        } else if (!l_moved) {
            p_player.setD_moved(true);
            return moveCommandToReinforce(p_map, p_player);
        } else {
            // Reset flags for the next round
            p_player.setD_deployed(false);
            p_player.setD_attacked(false);
            p_player.setD_moved(false);
            return new Command("commit");
        }
    }

    /**
     * Creates a deploy command for the strongest country of the player.
     *
     * @param p_player the player for whom the command is being created
     * @return the deploy command
     */
    public Command deployCommandOnStrongest(Player p_player) {
        String l_countryName = findStrongestCountry(p_player.getD_countries()).getD_name();
        int l_reinforcementsToDeploy = p_player.getD_reinforcements();
        return CommandParser.parse("deploy " + l_countryName + " " + l_reinforcementsToDeploy)
                .get(0);
    }

    /**
     * Creates an attack command for the strongest country of the player against any neighboring
     * enemy country.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the command is being created
     * @return the attack command
     */
    public Command attackCommandOnStrongest(Map p_map, Player p_player) {
        Country l_strongestCountry = findStrongestCountry(p_player.getD_countries());
        List<Integer> l_neighborIds = new ArrayList<>(l_strongestCountry.getD_neighborIdList());

        // Shuffle the neighbor IDs to attack a random neighboring enemy country
        Collections.shuffle(l_neighborIds);

        // Look for an enemy neighbor to attack
        for (int l_neighborId : l_neighborIds) {
            Country l_neighborCountry = getCountryById(p_map, l_neighborId);

            if (l_neighborCountry != null
                    && !getCountryOwner(l_neighborCountry, p_map.getD_players()).equals(p_player)) {
                int l_armiesToMove = l_strongestCountry.getD_armyCount();
                return CommandParser.parse(
                                "advance "
                                        + l_strongestCountry.getD_name()
                                        + " "
                                        + l_neighborCountry.getD_name()
                                        + " "
                                        + l_armiesToMove)
                        .get(0);
            }
        }
        // If no enemy neighbor is found, try to move to a random neighbor
        return moveStrongestToRandom(p_map, p_player);
    }

    /**
     * Creates a move command to reinforce the strongest country by moving troops from neighboring
     * countries. If no suitable neighbor is found, commit for this action.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the command is being created
     * @return the move command
     */
    public Command moveCommandToReinforce(Map p_map, Player p_player) {
        Country l_strongestCountry = findStrongestCountry(p_player.getD_countries());
        List<Integer> l_neighborIds = new ArrayList<>(l_strongestCountry.getD_neighborIdList());

        // Find neighboring countries owned by the player
        List<Country> l_ownedNeighbors = new ArrayList<>();
        for (int l_neighborId : l_neighborIds) {
            Country l_neighborCountry = getCountryById(p_map, l_neighborId);
            if (l_neighborCountry != null
                    && getCountryOwner(l_neighborCountry, p_map.getD_players()).equals(p_player)) {
                l_ownedNeighbors.add(l_neighborCountry);
            }
        }

        if (!l_ownedNeighbors.isEmpty()) {
            // Find the neighbor with the fewest armies and move its armies to the strongest country
            Country l_weakestNeighbor =
                    Collections.min(
                            l_ownedNeighbors, Comparator.comparingInt(Country::getD_armyCount));
            int l_armiesToMove = l_weakestNeighbor.getD_armyCount();

            return new Command(
                    "advance",
                    List.of(
                            l_weakestNeighbor.getD_name(),
                            l_strongestCountry.getD_name(),
                            Integer.toString(l_armiesToMove)));
        }

        // If no suitable neighbor is found, commit for this action
        p_player.setD_deployed(false);
        p_player.setD_attacked(false);
        p_player.setD_moved(false);
        return new Command("commit");
    }

    /**
     * Creates a move command to move the armies from the strongest country to one of its (friendly)
     * neighbors. The method assumes that attackCommandOnStrongest has already been called and that
     * it didn't find any enemy neighbors.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the command is being created
     * @return the move command
     */
    private Command moveStrongestToRandom(Map p_map, Player p_player) {
        Country l_strongestCountry = findStrongestCountry(p_player.getD_countries());
        List<Integer> l_neighborIds = new ArrayList<>(l_strongestCountry.getD_neighborIdList());

        // Select a neighbor at random
        Random l_random = new Random();
        int l_index = l_random.nextInt(l_neighborIds.size());

        Country l_randomNeighbor = getCountryById(p_map, l_neighborIds.get(l_index));
        int l_armiesToMove = l_strongestCountry.getD_armyCount();

        // Move everything to that neighbor
        return new Command(
                "advance",
                List.of(
                        l_strongestCountry.getD_name(),
                        l_randomNeighbor.getD_name(),
                        Integer.toString(l_armiesToMove)));
    }

    /**
     * Finds the strongest country among a list of countries.
     *
     * @param p_countries the list of countries to search through
     * @return the strongest country
     */
    private Country findStrongestCountry(List<Country> p_countries) {
        return Collections.max(p_countries, Comparator.comparingInt(Country::getD_armyCount));
    }
}
