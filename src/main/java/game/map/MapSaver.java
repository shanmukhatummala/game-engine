package game.map;

import static game.map.MapHelper.getContinentWithId;
import static game.map.MapHelper.getCountryWithId;

import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class MapSaver {

    public static void saveMap(String path, Map map) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String continentsStarter = "[continents]";
            String countryStarter = "[countries]";
            String borderStarter = "[borders]";

            // write the continents to the file
            writer.write(continentsStarter);
            for (int i = 0; i < map.continents.size(); i++) {
                writer.write(map.continents.get(i).getName());
                writer.write(map.continents.get(i).getBonus());
            }
            // write the countries to the file
            writer.write(countryStarter);

            // write the border to the file
            writer.write(borderStarter);

            //close the writer
            writer.close();


            System.out.println("Saved the map file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
