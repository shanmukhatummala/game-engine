package game.strategy;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Set;

public class Cheater extends PlayerStrategy {

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

        List<Country> countries = p_player.getD_countries();

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

        return null;
    }
}
