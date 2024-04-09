package game.strategy;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represents a cheater computer player strategy. The cheater's strategy is to conquer all the
 * immediate neighboring enemy countries, and then doubles the number of armies on its own countries
 * that have enemy neighbors.
 */
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

        // Get all the countries owned by the cheater
        List<Country> countries = p_player.getD_countries();
        List<Country> neighborsToBeOccupied = new ArrayList<>();

        // Capturing all the immediate neighboring enemy countries
        for (int i=0; i< countries.size(); i++) {
            Country country = countries.get(i);
            Set<Integer> neighbors = country.getD_neighborIdList();
            Iterator it = neighbors.iterator();
            while (it.hasNext()) {
                int neighbor = (int) it.next();
                Country neighborCountry = getCountryById(p_map, neighbor);
                Player neighborOwner = getCountryOwner(neighborCountry, p_map.getD_players());
                if (neighborOwner != null && !neighborOwner.equals(p_player)) {
                    neighborsToBeOccupied.add(neighborCountry);
                    System.out.println(
                            "Cheater player "
                                    + p_player.getD_name()
                                    + " conquering their neighbor country : "
                                    + neighborCountry.getD_name());
                }
            }
        }

        //Occupying all the immediate neighboring enemy countries
        for(Country neighborCountry : neighborsToBeOccupied ) {
            Player neighborOwner = getCountryOwner(neighborCountry, p_map.getD_players());
            if(neighborOwner != null) {
                neighborOwner.getD_countries().remove(neighborCountry);
            }
            p_player.getD_countries().add(neighborCountry);
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

        return new Command("commit");
    }
}
