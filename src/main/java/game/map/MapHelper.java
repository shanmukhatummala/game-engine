package game.map;

import game.pojo.Continent;
import game.pojo.Country;

public class MapHelper {

    public static Continent getContinentWithId(Map map, int id) {

        return map.getContinents()
                .stream().filter(continent -> continent.getId() == id).toList().get(0);
    }
// Below method throws IndexOutOfBoundsException when no country is found with the given id. But the next method
// will return "null" which seems to be a safer option. Leaving it to Shanmukh for confirmation as the initial method
// was created by him.

//    public static Country getCountryWithId(Map map, int id) {
//
//        return map.getCountries()
//                .stream().filter(country -> country.getId() == id).toList().get(0);
//    }
    public static Country getCountryWithId(Map map, int id) {
        return map.getCountries()
                .stream()
                .filter(country -> country.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
