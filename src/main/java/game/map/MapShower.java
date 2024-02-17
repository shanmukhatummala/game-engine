package game.map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

/**
 * MapShower displays all the information about the map in text format
 * @author Siva
 */

public class MapShower {

    /**
     * showMap method is used display the map as text
     * It shows all the continents, countries, players and armies assigned to each country and connectivity between countries
     */

    public static void showMap(Map map) {
        System.out.println("---------------------------Continents---------------------------");
        List<Continent> continents = map.getD_continents();
        for (Continent continent : continents) {
            System.out.println(continent.getD_name());
        }
        System.out.println("---------------------------Countries---------------------------");
        List<Country> countries = map.getD_countries();
        List<Player> players = map.getD_players();
        for (Country country : countries) {
            System.out.println(country.getD_name());
            boolean isOwned = false;
            for (Player player : players) {
                if (player.getD_countries().contains(country)) { // Check if the player owns the country
                    System.out.println("Country is owned by: " + player.getD_name());
                    isOwned = true;
                    break;
                }
            }
            if (!isOwned) {
                System.out.println("Country is not owned by any player yet");
            }

            System.out.println("Neighbors:");
            for (Integer neighborId : country.getD_neighborIdList()) {
                Country neighbor = getCountryById(map, neighborId);
                assert neighbor != null;
                System.out.println("- " + neighbor.getD_name());
            }
        }
    }
    private static Country getCountryById(Map map, int id) {
        List<Country> countries = map.getD_countries();
        for (Country country : countries) {
            if (country.getD_id() == id) {
                return country;
            }
        }
        return null;
    }
}
