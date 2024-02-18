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
     * It shows all the continents, countries, ownership and armies assigned to each country and connectivity between countries
     */

    public static void showMap(Map map) {
        List<Continent> continents = map.getD_continents(); //Should use d_continents?
        List<Country> countries = map.getD_countries();
        List<Player> players = map.getD_players();

        System.out.println("---------------------------Continents---------------------------");
        for (Continent l_continent : continents) {
            System.out.println(l_continent.getD_name());
            // Finding if any player owns an entire continent i.e., owner of all countries in a continent
            Player l_continentOwner = getContinentOwner(l_continent, players);
            if (l_continentOwner != null) {
                System.out.println("    - Continent is owned by: " + l_continentOwner.getD_name());
            }
            else {
                System.out.println("    - Continent is not owned by any player yet");
            }
        }
        System.out.println("---------------------------Countries---------------------------");
        for (Country l_country : countries) {
            System.out.println("Country   : " + l_country.getD_name());
            System.out.println("Continent : " + l_country.getD_continent().getD_name());

            Player l_owner = getCountryOwner(l_country, players);
            if (l_owner != null) {
                System.out.println("Country is owned by: " + l_owner.getD_name());
            } else {
                System.out.println("Country is not owned by any player yet");
            }
            // Initially, no country will be given armies and the players need to deploy them during their turn.
            System.out.println("Number of armies : 0");
            System.out.println("Neighbors:");
            for (Integer l_neighborId : l_country.getD_neighborIdList()) {
                Country l_neighbor = getCountryById(map, l_neighborId);
                assert l_neighbor != null;
                System.out.println("    - " + l_neighbor.getD_name());
            }
            System.out.println("----------------------------------------------------------------");
        }
    }


    /**  <p>getContinentOwner - This generates the name of the owner of a continent </p>
     * @param p_continent Name of the continent
     * @param p_players List of players added in the game
     * @return Name of the player that owns the given continent or returns Null if there is no owner yet
     */
    private static Player getContinentOwner(Continent p_continent, List<Player> p_players) {
        for (Player l_player : p_players) {
            if (playerOwnsContinent(l_player, p_continent)) {
                return l_player;
            }
        }
        return null;
    }

    /**  <p>playerOwnsContinent - This is a boolean to check if a continent is owned by the given single player or not</p>
     * @param p_continent Name of the continent
     * @param p_player A single player from the list of players added in the game
     * @return Returns True if the given continent is owned by the given player, else returns False.
     */
    private static boolean playerOwnsContinent(Player p_player, Continent p_continent) {
        for (Integer l_countryId : p_continent.getD_countryIdList()) {
            boolean l_found = false;
            for (Country l_country : p_player.getD_countries()) {
                if (l_country.getD_id() == l_countryId) {
                    l_found = true;
                    break;
                }
            }
            if (!l_found) {
                return false;
            }
        }
        return true;
    }


    /**  <p>getCountryOwner - This generates the name of the owner of a country</p>
     * @param p_country Name of the Country
     * @param p_players List of players added in the game
     * @return Name of the player that owns the given country or returns Null if there is no owner yet
     */
    private static Player getCountryOwner(Country p_country, List<Player> p_players) {
        for (Player l_player : p_players) {
            if (l_player.getD_countries().contains(p_country)) {
                return l_player;
            }
        }
        return null;
    }


    /**  <p>getCountryById - This generates Country name from the country id</p>
     * @param p_map reference to the map
     * @param p_id id of the neighbor country
     * @return Name of the neighbor country
     */
    private static Country getCountryById(Map p_map, int p_id) {
        List<Country> countries = p_map.getD_countries();
        for (Country l_country : countries) {
            if (l_country.getD_id() == p_id) {
                return l_country;
            }
        }
        return null;
    }
}
