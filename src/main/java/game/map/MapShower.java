package game.map;
import game.pojo.Continent;
import game.pojo.Country;
import java.util.List;
public class MapShower {
    public static void mapShower(Map map) {
        System.out.println("---------------------------Continents---------------------------");
        List<Continent> continents = map.getContinents();
        for (Continent continent : continents) {
            System.out.println(continent.getName());
        }
        System.out.println("---------------------------Countries---------------------------");
        List<Country> countries = map.getCountries();
        for (Country country : countries) {
            System.out.println(country.getName());
            System.out.println("Neighbors:");

            for (Country neighbor : country.getNeighbors()) {
                System.out.println("- " + neighbor.getName());
            }
        }
    }
}
