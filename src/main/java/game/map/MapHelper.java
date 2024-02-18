package game.map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import java.util.List;

/**
 * MapHelper provides helper methods to get required details from the map
 */
public class MapHelper {

    /**
     * <p>This method gives the continent by taking the id of the continent as input</p>
     * @param p_map reference to the map
     * @param p_id id of the continent
     * @return continent reference
     */
    public static Continent getContinentWithId(Map p_map, int p_id) {

        return p_map.getD_continents()
                .stream()
                .filter(continent -> continent.getD_id() == p_id)
                .findFirst()
                .orElse(null);
    }

    /**
     * <p>This method gives the country by taking the id of the country as input</p>
     * @param p_map reference to the map
     * @param p_id id of the country
     * @return country reference
     */
    public static Country getCountryWithId(Map p_map, int p_id) {

        return p_map.getD_countries()
                .stream()
                .filter(country -> country.getD_id() == p_id)
                .findFirst()
                .orElse(null);
    }

    /**  <p>playerOwnsContinent - This is a boolean to check if a continent is owned by the given single player or not</p>
     * @param p_map reference to the map
     * @param p_continent Name of the continent
     * @param p_player A single player from the list of players added in the game
     * @return Returns True if the given continent is owned by the given player, else returns False.
     */
    public static boolean playerOwnsContinent(Map p_map,Player p_player, Continent p_continent) {
       List<Country> countriesOfThisPlayer = p_player.getD_countries();
        for (Integer l_countryId : p_continent.getD_countryIdList()) {
            if(!countriesOfThisPlayer.contains(getCountryWithId(p_map,l_countryId)))
            {
               return false;
            }
        }
        return true;
    }

    /**  <p>getContinentOwner - This generates the name of the owner of a continent </p>
     * @param p_continent Name of the continent
     * @param p_players List of players added in the game
     * @return Name of the player that owns the given continent or returns Null if there is no owner yet
     */
    public static Player getContinentOwner(Map p_map,Continent p_continent, List<Player> p_players) {
        for (Player l_player : p_players) {
            if (MapHelper.playerOwnsContinent(p_map,l_player, p_continent)) {
                return l_player;
            }
        }
        return null;
    }

    /**  <p>getCountryOwner - This generates the name of the owner of a country</p>
     * @param p_country Name of the Country
     * @param p_players List of players added in the game
     * @return Name of the player that owns the given country or returns Null if there is no owner yet
     */
    public static Player getCountryOwner(Country p_country, List<Player> p_players) {
        for (Player l_player : p_players) {
            if (l_player.getD_countries().contains(p_country)) {
                return l_player;
            }
        }
        return null;
    }

}
