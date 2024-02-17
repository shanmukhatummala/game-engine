package game.map;

import game.pojo.Continent;
import game.pojo.Country;

public class MapHelper {

    public static Continent getContinentWithId(Map p_map, int p_id) {

        return p_map.getD_continents()
                .stream()
                .filter(continent -> continent.getD_id() == p_id)
                .findFirst()
                .orElse(null);
    }

    public static Country getCountryWithId(Map p_map, int p_id) {

        return p_map.getD_countries()
                .stream()
                .filter(country -> country.getD_id() == p_id)
                .findFirst()
                .orElse(null);
    }
}
