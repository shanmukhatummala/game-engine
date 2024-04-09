package game.mapfile.reader;

import static game.map.MapHelper.getContinentByName;
import static game.map.MapHelper.getCountryByName;

import game.GameEngine;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * ConquestFileReader is used for loading the conquest style map
 *
 * @author Shanmukha
 */
public class ConquestFileReader {

    /**
     * This method reads the map and loads it into map object
     *
     * @param p_path path of the file
     * @param p_map reference to the map
     */
    public void readConquestFile(String p_path, Map p_map) {
        try (BufferedReader l_reader = new BufferedReader(new java.io.FileReader(p_path))) {
            String l_fileName = p_path.split("/")[3];
            p_map.clearMap();

            p_map.setD_mapName(l_fileName);
            String l_line;
            boolean l_readingContinents = false;
            boolean l_readingCountries = false;

            int l_continentID = 1;
            int l_countryId = 1;

            java.util.Map<Country, List<String>> l_countryNeighborNameListMap = new HashMap<>();

            while ((l_line = l_reader.readLine()) != null) {
                if ("[Continents]".equals(l_line)) {
                    l_readingContinents = true;
                    continue;
                }

                if ("[Territories]".equals(l_line)) {
                    l_readingContinents = false;
                    l_readingCountries = true;
                    continue;
                }

                if (l_line.isEmpty() || l_line.equals(" ")) {
                    continue;
                }

                if (l_readingContinents) {
                    String[] l_continentAttributes = l_line.split("=");
                    Continent l_continent =
                            new Continent(
                                    l_continentID,
                                    l_continentAttributes[0],
                                    Integer.parseInt(l_continentAttributes[1]));
                    l_continentID++;
                    p_map.addContinent(l_continent);
                }

                if (l_readingCountries) {
                    String[] l_countryAttributes = l_line.split(",");

                    String l_countryName = l_countryAttributes[0].trim();
                    Continent l_continent =
                            getContinentByName(p_map, l_countryAttributes[3].trim());
                    if (l_continent == null) {
                        throw new IllegalArgumentException(
                                "Continent with id, " + l_countryAttributes[2] + ", doesn't exist");
                    }

                    Country l_country = new Country(l_countryId, l_countryName, l_continent);
                    l_countryNeighborNameListMap.put(
                            l_country,
                            l_countryAttributes.length > 4
                                    ? Arrays.stream(l_countryAttributes)
                                            .toList()
                                            .subList(4, l_countryAttributes.length)
                                    : new ArrayList<>());
                    p_map.addCountry(l_country);

                    l_countryId++;
                }
            }

            l_countryNeighborNameListMap.forEach(
                    (l_country, l_neighborNameList) -> {
                        l_neighborNameList.forEach(
                                l_neighbor ->
                                        l_country.addNeighbor(
                                                Objects.requireNonNull(
                                                                getCountryByName(
                                                                        p_map, l_neighbor.trim()))
                                                        .getD_id()));
                    });
        } catch (IOException | IllegalArgumentException l_e) {
            p_map.clearMap();
            System.out.println(
                    "Loading map failed with error: " + l_e.getMessage() + ". So stopped loading.");
        }

        GameEngine.LOG_ENTRY_BUFFER.addLogEntry("Loaded the map into Java objects");
    }
}
