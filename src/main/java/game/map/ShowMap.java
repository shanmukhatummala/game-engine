package game.map;
import game.pojo.Continent;
import game.pojo.Country;
import java.util.List;
public class ShowMap {
    public static void showMap(Map map) {
        System.out.println("---------------------------Continents---------------------------");
        List<Continent> continents = map.getContinents();
        for (Continent continent : continents) {
            System.out.println(continent.getName());
        }
        System.out.println("---------------------------Countries---------------------------");
        List<Country> countries = map.getCountries();
        for (Country country : countries) {
            System.out.println(country.getName());
            System.out.println("Borders:");
            List<Integer> borderIds = country.getBorderIds();
            if (borderIds != null && !borderIds.isEmpty()) {
                for (int borderId : borderIds) {
                    Country borderCountry = MapHelper.getCountryWithId(map, borderId);
                    if (borderCountry != null) {
                        System.out.println("- " + borderCountry.getName());
                    }
                    else {
                        System.out.println("- Country with ID " + borderId + " does not exist");
                    }
                }
            }
            else {
                System.out.println("- No borders (invalid data)");
            }
            System.out.println();
        }
    }
}
