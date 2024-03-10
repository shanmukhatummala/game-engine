package game.map;

import static game.map.MapHelper.*;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

/**
 * MapShower displays all the information about the map in text format
 *
 * @author Siva
 */
public class MapShower {

    /**
     * showMap method is used display the map as text It shows all the continents, countries,
     * ownership and armies assigned to each country and connectivity between countries
     *
     * @param p_map reference to the map
     */
    public static void showMap(Map p_map) {
        List<Continent> l_continents = p_map.getD_continents();
        List<Country> l_countries = p_map.getD_countries();
        List<Player> l_players = p_map.getD_players();
        System.out.println("---------------------------Continents---------------------------");
        for (Continent l_continent : l_continents) {
            System.out.println(l_continent.getD_name());
            // Finding if any player owns an entire continent i.e., owner of all countries in a
            // continent
            Player l_continentOwner = getContinentOwner(p_map, l_continent, l_players);
            if (l_continentOwner != null) {
                System.out.println("    - Continent is owned by: " + l_continentOwner.getD_name());
            } else {
                System.out.println("    - Continent is not owned by any player yet");
            }
        }
        System.out.println("---------------------------Countries---------------------------");
        for (Country l_country : l_countries) {
            System.out.println("Country   : " + l_country.getD_name());
            System.out.println("Continent : " + l_country.getD_continent().getD_name());
            Player l_owner = getCountryOwner(l_country, l_players);
            if (l_owner != null) {
                System.out.println("Country is owned by: " + l_owner.getD_name());
            } else {
                System.out.println("Country is not owned by any player yet");
            }
            System.out.println("Number of armies : " + l_country.getD_armyCount());
            System.out.println("Neighbors:");
            for (Integer l_neighborId : l_country.getD_neighborIdList()) {
                Country l_neighbor = getCountryWithId(p_map, l_neighborId);
                assert l_neighbor != null;
                System.out.println("    - " + l_neighbor.getD_name());
            }
            System.out.println("----------------------------------------------------------------");
        }
    }
}
