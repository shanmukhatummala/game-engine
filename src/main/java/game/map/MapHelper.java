package game.map;

import game.pojo.Continent;
import game.pojo.Country;

public class MapHelper {

    public static Continent getContinentWithId(Map map, int id) {
        return map.getContinents()
                .stream()
                .filter(continent -> continent.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public static Country getCountryWithId(Map map, int id) {
        return map.getCountries()
                .stream()
                .filter(country -> country.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
