package game.strategy;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Set;

/**
 * Represents a cheater computer player strategy. The cheater's strategy is to conquer all the
 * immediate neighboring enemy countries, and then doubles the number of armies on its own countries
 * that have enemy neighbors.
 */
public class Cheater extends PlayerStrategy {

    /** Unique Cheater object. */
    private static Cheater d_CheaterStrategy;

    /**
     * Provides access to the strategy, using the Singleton design pattern
     *
     * @return the cheater strategy
     */
    public static Cheater getCheaterStrategy() {
        if (d_CheaterStrategy == null) {
            Cheater.d_CheaterStrategy = new Cheater();
        }
        return d_CheaterStrategy;
    }

    /**
     * Implements the cheater player's issue order method. This method simulates the cheater's
     * behavior without directly impacting the map.
     *
     * @param p_map The map where the game is played
     * @param p_player Cheater player
     * @return List of orders issued by the player (empty list in the case of a cheater)
     */
    @Override
    public Command createOrder(Map p_map, Player p_player) {

        // Get all the countries owned by the cheater
        List<Country> countries = p_player.getD_countries();

        // Conquering all the immediate neighboring enemy countries
        for (Country country : countries) {
            Set<Integer> neighbors = country.getD_neighborIdList();
            for (Integer neighbor : neighbors) {
                Country neighborCountry = getCountryById(p_map, neighbor);
                Player neighborOwner = getCountryOwner(neighborCountry, p_map.getD_players());
                if (neighborOwner != null && !neighborOwner.equals(p_player)) {
                    System.out.println(
                            "Cheater player "
                                    + p_player.getD_name()
                                    + "conquering their neighbor country : "
                                    + neighborCountry.getD_name());
                }
            }
        }

        // Doubling the number of armies on cheater's countries that have enemy neighbors
        for (Country country : countries) {
            Set<Integer> neighbors = country.getD_neighborIdList();
            for (Integer neighbor : neighbors) {
                Country neighborCountry = getCountryById(p_map, neighbor);
                Player neighborOwner = getCountryOwner(neighborCountry, p_map.getD_players());
                if (neighborOwner != null && !neighborOwner.equals(p_player)) {
                    country.setD_armyCount(country.getD_armyCount() * 2);
                    System.out.println(
                            "Doubling the armies of country : "
                                    + country.getD_name()
                                    + ", owned by Cheater player : "
                                    + p_player.getD_name());
                    break;
                }
            }
        }

        // Return an empty list since cheater's strategy does not issue explicit orders
        return null;
    }
}
