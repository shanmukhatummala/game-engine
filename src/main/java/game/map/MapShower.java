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
        List<Continent> continents = map.getContinents();
        for (Continent continent : continents) {
            System.out.println(continent.getName());
        }
        System.out.println("---------------------------Countries---------------------------");
        List<Country> countries = map.getCountries();
        List<Player> players = map.getPlayers();
        for (Country country : countries) {
            System.out.println(country.getName());
            boolean isOwned = false;

            for (Player player : players) {
                if (player.getCountries().contains(country)) { // Check if the player owns the country
                    System.out.println("Country is owned by: " + player.getName());
                    isOwned = true;
                    break;
                }
            }
            if (!isOwned) {
                System.out.println("Country is not owned by any player yet");
            }

            System.out.println("Neighbors:");
            for (Integer neighborId : country.getNeighborIdList()) {
                Country neighbor = getCountryById(map, neighborId);
                assert neighbor != null;
                System.out.println("    - " + neighbor.getName());
            }
        }
    }
    private static Country getCountryById(Map map, int id) {
        List<Country> countries = map.getCountries();
        for (Country country : countries) {
            if (country.getId() == id) {
                return country;
            }
        }
        return null;
    }
}
