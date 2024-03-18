package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Optional;

/** MapHelper provides helper methods to get required details from the map */
public class MapHelper {

    /**
     * This method gives the continent by taking the id of the continent as input
     *
     * @param p_map reference to the map
     * @param p_id id of the continent
     * @return continent reference
     */
    public static Continent getContinentById(Map p_map, int p_id) {

        return p_map.getD_continents().stream()
                .filter(l_continent -> l_continent.getD_id() == p_id)
                .findFirst()
                .orElse(null);
    }

    /**
     * This method gives the country by taking the id of the country as input
     *
     * @param p_map reference to the map
     * @param p_id id of the country
     * @return country reference
     */
    public static Country getCountryById(Map p_map, int p_id) {

        return p_map.getD_countries().stream()
                .filter(l_country -> l_country.getD_id() == p_id)
                .findFirst()
                .orElse(null);
    }

    /**
     * To get a Country object by name
     *
     * @param p_map map of the game engine
     * @param p_name the country name
     * @return Country object
     */
    public static Country getCountryByName(Map p_map, String p_name) {
        for (Country l_country : p_map.getD_countries()) {
            if (l_country.getD_name().equals(p_name)) {
                return l_country;
            }
        }
        return null;
    }

    /**
     * playerOwnsContinent - This is a boolean to check if a continent is owned by the given single
     * player or not
     *
     * @param p_map reference to the map
     * @param p_continent Name of the continent
     * @param p_player A single player from the list of players added in the game
     * @return Returns True if the given continent is owned by the given player, else returns False.
     */
    public static boolean playerOwnsContinent(Map p_map, Player p_player, Continent p_continent) {
        List<Country> l_countriesOfThisPlayer = p_player.getD_countries();
        for (Integer l_countryId : p_continent.getD_countryIdList()) {
            if (!l_countriesOfThisPlayer.contains(getCountryById(p_map, l_countryId))) {
                return false;
            }
        }
        return true;
    }

    /**
     * getContinentOwner - This generates the name of the owner of a continent
     *
     * @param p_map map of the game engine
     * @param p_continent Name of the continent
     * @param p_players List of players added in the game
     * @return Name of the player that owns the given continent or returns Null if there is no owner
     *     yet
     */
    public static Player getContinentOwner(
            Map p_map, Continent p_continent, List<Player> p_players) {
        for (Player l_player : p_players) {
            if (MapHelper.playerOwnsContinent(p_map, l_player, p_continent)) {
                return l_player;
            }
        }
        return null;
    }

    /**
     * getCountryOwner - This generates the name of the owner of a country
     *
     * @param p_country Name of the Country
     * @param p_players List of players added in the game
     * @return Name of the player that owns the given country or returns Null if there is no owner
     *     yet
     */
    public static Player getCountryOwner(Country p_country, List<Player> p_players) {
        for (Player l_player : p_players) {
            if (l_player.getD_countries().contains(p_country)) {
                return l_player;
            }
        }
        return null;
    }

    /**
     * Checks if the target country is adjacent to any one of the list of countries
     *
     * @param p_countries list of countries
     * @param p_targetCountry target country
     * @return true if adjacent, false if not
     */
    public static boolean isAdjacent(List<Country> p_countries, Country p_targetCountry) {

        Optional<Country> l_adjacentToCountry =
                p_countries.stream()
                        .filter(
                                country ->
                                        country.getD_neighborIdList()
                                                .contains(p_targetCountry.getD_id()))
                        .findAny();

        return l_adjacentToCountry.isPresent();
    }
}
