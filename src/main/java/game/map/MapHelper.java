package game.map;

import game.pojo.Continent;
import game.pojo.Country;

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
}
