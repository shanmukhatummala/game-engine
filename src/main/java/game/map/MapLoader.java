package game.map;

import static game.map.MapHelper.getContinentWithId;
import static game.map.MapHelper.getCountryWithId;

import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {

    public static void loadMap(String path, Map map) {

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean readingContinents = false;
            boolean readingCountries = false;
            boolean readingBorders = false;

            int continentID = 1;

            while ((line = reader.readLine()) != null) {
                if ("[continents]".equals(line)) {
                    readingContinents = true;
                    continue;
                }

                if ("[countries]".equals(line)) {
                    readingCountries = true;
                    continue;
                }

                if ("[borders]".equals(line)) {
                    readingBorders = true;
                    continue;
                }

                if (line.isEmpty()) {
                    readingContinents = false;
                    readingCountries = false;
                    readingBorders = false;
                    continue;
                }

                if (readingContinents) {
                    String[] continentAttributes = line.split(" ");
                    Continent continent = new Continent(
                            continentID, continentAttributes[0], Integer.parseInt(continentAttributes[1]));
                    continentID ++;
                    map.addContinent(continent);
                }

                if (readingCountries) {
                    String[] countryAttributes = line.split(" ");
                    Country country = new Country(
                            Integer.parseInt(countryAttributes[0]),
                            countryAttributes[1],
                            getContinentWithId(map, Integer.parseInt(countryAttributes[2])));
                    map.addCountry(country);
                }

//                if (readingBorders) {
//                    String[] borderAttributes = line.split(" ");
//                    Country currentCountry = getCountryWithId(map, Integer.parseInt(borderAttributes[0]));
//                    for (int idx = 1; idx < borderAttributes.length; idx ++) {
//                        int neighbourId = Integer.parseInt(borderAttributes[idx]);
//                        Country neighbour = getCountryWithId(map, neighbourId);
//                        currentCountry.getNeighbours().add(neighbour);
//                    }
//                }
                // Siva - to print border countries in ShowMap. Shanmukh need to confirm if this change is causing any other issue.
                if (readingBorders) {
                    String[] borderAttributes = line.split(" ");
                    int countryId = Integer.parseInt(borderAttributes[0]);
                    List<Integer> borderIds = new ArrayList<>();
                    for (int i = 1; i < borderAttributes.length; i++) {
                        borderIds.add(Integer.parseInt(borderAttributes[i]));
                    }
                    Country country = MapHelper.getCountryWithId(map, countryId);
                    if (country != null) {
                        country.setBorderIds(borderIds);
                    }
                }
            }

            System.out.println("Loaded the map into Java objects");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
