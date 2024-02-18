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
        List<Continent> continents = map.getD_continents();
        List<Country> countries = map.getD_countries();
        List<Player> players = map.getD_players();

        System.out.println("---------------------------Continents---------------------------");
        for (Continent continent : continents) {
            System.out.println(continent.getD_name());

            Player continentOwner = getContinentOwner(continent, players);
            // Print the owner of the continent
            if (continentOwner != null) {
                System.out.println("    - Continent is owned by: " + continentOwner.getD_name());
            }
            else {
                System.out.println("    - Continent is not owned by any player yet");
            }
        }

        System.out.println("---------------------------Countries---------------------------");
        for (Country country : countries) {
            System.out.println("Country   : " + country.getD_name());
            System.out.println("Continent : " + country.getD_continent().getD_name());

            Player owner = getCountryOwner(country, players);
            if (owner != null) {
                System.out.println("Country is owned by: " + owner.getD_name());
            } else {
                System.out.println("Country is not owned by any player yet");
            }
            // Initially, no country will be given armies and the players need to deploy them during their turn.
            System.out.println("Number of armies : 0");

            System.out.println("Neighbors:");
            for (Integer neighborId : country.getD_neighborIdList()) {
                Country neighbor = getCountryById(map, neighborId);
                assert neighbor != null;
                System.out.println("    - " + neighbor.getD_name());
            }
            System.out.println("----------------------------------------------------------------");
        }
    }


    private static Player getContinentOwner(Continent continent, List<Player> players) {
        for (Player player : players) {
            if (playerOwnsContinent(player, continent)) {
                return player;
            }
        }
        return null;
    }

    private static boolean playerOwnsContinent(Player player, Continent continent) {
        for (Integer countryId : continent.getD_countryIdList()) {
            boolean found = false;
            for (Country country : player.getD_countries()) {
                if (country.getD_id() == countryId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }


    private static Player getCountryOwner(Country country, List<Player> players) {
        for (Player player : players) {
            if (player.getD_countries().contains(country)) {
                return player;
            }
        }
        return null;
    }


    /**  <p>getCountryById - This generates Country name from the country id</p>
     * @param map reference to the map
     * @param id id of the neighbor country
     * @return Name of the neighbor country
     */
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
