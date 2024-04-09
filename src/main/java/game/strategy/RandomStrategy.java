/**
 * The RandomStrategy class represents a strategy for a player in a game that involves random
 * actions such as deploying troops, attacking, and moving troops. This strategy is designed to
 * provide a simple, yet unpredictable approach to gameplay. The strategy follows a sequence of
 * actions: deploy, attack, move, and then repeats. This class is a singleton, ensuring that only
 * one instance of the RandomStrategy exists.
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

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomStrategy extends PlayerStrategy {

    /** The singleton instance of the RandomStrategy. */
    private static RandomStrategy randomStrategy;

    /**
     * Returns the singleton instance of the RandomStrategy. If the instance does not exist, it is
     * created.
     *
     * @return the singleton instance of the RandomStrategy
     */
    public static RandomStrategy getRandomStrategy() {
        if (randomStrategy == null) {
            randomStrategy = new RandomStrategy();
        }
        return randomStrategy;
    }

    /** Private constructor to enforce the singleton pattern. */
    private RandomStrategy() {}

    /** Indicates whether the player has deployed troops. */
    private boolean d_deployed = false;

    /** Indicates whether the player has attacked. */
    private boolean d_attacked = false;

    /** Indicates whether the player has moved troops. */
    private boolean d_moved = false;

    /**
     * Creates an order for the player based on random actions. The order sequence is deploy,
     * attack, move, and then repeats.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the order is being created
     * @return the command to be executed
     */
    @Override
    public Command createOrder(Map p_map, Player p_player) {
        if (!d_deployed) {
            d_deployed = true;
            return deployRandomly(p_player);
        } else if (!d_attacked) {
            d_attacked = true;
            return attackRandomly(p_map, p_player);
        } else if (!d_moved) {
            d_moved = true;
            return moveRandomly(p_map, p_player);
        } else {
            // Reset flags for the next round
            d_deployed = false;
            d_attacked = false;
            d_moved = false;
            return new Command("commit");
        }
    }

    /**
     * Creates a deploy command for a randomly selected country of the player.
     *
     * @param p_player the player for whom the command is being created
     * @return the deploy command
     */
    private Command deployRandomly(Player p_player) {
        List<Country> l_countries = p_player.getD_countries();

        if (!l_countries.isEmpty()) {
            Random l_random = new Random();
            int index = l_random.nextInt(l_countries.size());
            Country l_randomCountryID = l_countries.get(index);
            int l_reinforcements = p_player.getD_reinforcements();
            return CommandParser.parse(
                            "deploy " + l_randomCountryID.getD_name() + " " + l_reinforcements)
                    .get(0);
        }
        return new Command("commit"); // No valid country to deploy, try again
    }

    /**
     * Creates an attack command for a randomly selected country of the player against a neighboring
     * country.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the command is being created
     * @return the attack command
     */
    private Command attackRandomly(Map p_map, Player p_player) {
        List<Country> l_countries = p_player.getD_countries();
        if (!l_countries.isEmpty()) {
            Random l_random = new Random();
            int index = l_random.nextInt(l_countries.size());
            Country l_randomCountry = l_countries.get(index);
            int l_armiesToMove = l_randomCountry.getD_armyCount();
            List<Integer> l_neighborIds = (List<Integer>) l_randomCountry.getD_neighborIdList();
            if (!l_neighborIds.isEmpty()) {
                int l_neighborIndex = l_random.nextInt(l_neighborIds.size());
                int l_neighborId = l_neighborIds.get(l_neighborIndex);
                Country l_neighbor = getCountryById(p_map, l_neighborId);

                if (l_neighbor != null
                        && l_randomCountry.getD_armyCount() > l_neighbor.getD_armyCount()) {
                    return CommandParser.parse(
                                    "advance "
                                            + l_randomCountry.getD_name()
                                            + " "
                                            + l_neighbor.getD_name()
                                            + " "
                                            + l_armiesToMove)
                            .get(0);
                }
            }
        }
        // If no enemy neighbor is found, try to move to a random neighbor
        return moveRandomly(p_map, p_player);
    }

    /**
     * Creates a move command to move troops from a randomly selected country of the player to a
     * neighboring country.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the command is being created
     * @return the move command
     */
    private Command moveRandomly(Map p_map, Player p_player) {
        List<Country> l_countries = p_player.getD_countries();
        if (!l_countries.isEmpty()) {
            Random l_random = new Random();
            int index = l_random.nextInt(l_countries.size());
            Country l_randomCountry = l_countries.get(index);
            int l_armiesToMove = l_randomCountry.getD_armyCount();
            List<Integer> l_neighborIds = (List<Integer>) l_randomCountry.getD_neighborIdList();
            if (!l_neighborIds.isEmpty()) {
                int l_neighborIndex = l_random.nextInt(l_neighborIds.size());
                int l_neighborId = l_neighborIds.get(l_neighborIndex);
                Country l_neighbor = getCountryById(p_map, l_neighborId);

                if (l_neighbor != null
                        && getCountryOwner(l_neighbor, Collections.singletonList(p_player))
                                .equals(p_player)) {

                    return new Command(
                            "advance",
                            List.of(
                                    l_randomCountry.getD_name(),
                                    l_neighbor.getD_name(),
                                    Integer.toString(l_armiesToMove)));
                }
            }
        }
        return new Command("commit"); // No valid move, try again
    }
}
