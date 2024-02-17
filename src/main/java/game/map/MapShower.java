package game.map;
import game.pojo.Continent;
import game.pojo.Country;
import java.util.List;

public class MapShower {

    public static void showMap(Map map) {
        System.out.println("---------------------------Continents---------------------------");
        List<Continent> continents = map.getD_continents();
        for (Continent continent : continents) {
            System.out.println(continent.getD_name());
        }
        System.out.println("---------------------------Countries---------------------------");
        List<Country> countries = map.getD_countries();
        for (Country country : countries) {
            System.out.println(country.getD_name());
            System.out.println("Neighbors:");

            for (Integer neighborId : country.getD_neighborIdList()) {
                Country neighbor = getCountryById(map, neighborId);
                assert neighbor != null;
                System.out.println("- " + neighbor.getD_name());
            }
        }
    }
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
