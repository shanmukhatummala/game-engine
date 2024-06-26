package game.mapfile.reader;

import static game.map.MapHelper.getContinentById;
import static game.map.MapHelper.getCountryById;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedReader;

/**
 * MapFileReader is used for loading the warzone style map
 *
 * @author Shanmukha
 */
public class MapFileReader {

    /**
     * This method loads the map into map object by reading a file
     *
     * @param p_path path of the file
     * @param p_map reference to the map
     */
    public void readFile(String p_path, Map p_map) {
        try (BufferedReader l_reader = new BufferedReader(new java.io.FileReader(p_path))) {
            String l_fileName = p_path.split("/")[3];
            p_map.clearMap();

            p_map.setD_mapName(l_fileName);
            String l_line;
            boolean l_readingContinents = false;
            boolean l_readingCountries = false;
            boolean l_readingBorders = false;

            int l_continentID = 1;

            while ((l_line = l_reader.readLine()) != null) {
                if ("[continents]".equals(l_line)) {
                    l_readingContinents = true;
                    continue;
                }

                if ("[countries]".equals(l_line)) {
                    l_readingCountries = true;
                    continue;
                }

                if ("[borders]".equals(l_line)) {
                    l_readingBorders = true;
                    continue;
                }

                if (l_line.isEmpty()) {
                    l_readingContinents = false;
                    l_readingCountries = false;
                    l_readingBorders = false;
                    continue;
                }

                if (l_readingContinents) {
                    String[] l_continentAttributes = l_line.split(" ");
                    Continent l_continent =
                            new Continent(
                                    l_continentID,
                                    l_continentAttributes[0],
                                    Integer.parseInt(l_continentAttributes[1]));
                    l_continentID++;
                    p_map.addContinent(l_continent);
                }

                if (l_readingCountries) {
                    String[] l_countryAttributes = l_line.split(" ");
                    int l_countryId = Integer.parseInt(l_countryAttributes[0]);
                    String l_countryName = l_countryAttributes[1];
                    Continent l_continent =
                            getContinentById(p_map, Integer.parseInt(l_countryAttributes[2]));
                    if (l_continent == null) {
                        throw new IllegalArgumentException(
                                "Continent with id, " + l_countryAttributes[2] + ", doesn't exist");
                    }

                    Country l_country = new Country(l_countryId, l_countryName, l_continent);
                    p_map.addCountry(l_country);
                }

                if (l_readingBorders) {
                    String[] l_borderAttributes = l_line.split(" ");
                    Country l_currentCountry =
                            getCountryById(p_map, Integer.parseInt(l_borderAttributes[0]));
                    if (l_currentCountry == null) {
                        throw new IllegalArgumentException(
                                "Continent with id, " + l_borderAttributes[0] + ", doesn't exist");
                    }

                    for (int l_idx = 1; l_idx < l_borderAttributes.length; l_idx++) {
                        int l_neighborId = Integer.parseInt(l_borderAttributes[l_idx]);
                        l_currentCountry.addNeighbor(l_neighborId, p_map);
                    }
                }
            }
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Loaded the map into Java objects");
        } catch (Exception l_e) {
            p_map.clearMap();
            System.out.println(
                    "Loading map failed with error: "
                            + l_e.getMessage()
                            + ". So stopped loading, check if your map is in correct format.");
        }
    }
}
