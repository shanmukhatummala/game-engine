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
            writer.newLine();
            for (int i = 0; i < map.continents.size(); i++) {
                writer.write(map.continents.get(i).getName()+" ");
                writer.write(map.continents.get(i).getBonus()+"  \n");
            }
            writer.newLine();
            // write the countries to the file
            writer.write(countryStarter);
            writer.newLine();
            for (int i = 0; i < map.countries.size(); i++) {
                writer.write(map.countries.get(i).getId()+" ");
                writer.write(map.countries.get(i).getName()+" ");
                writer.write(map.countries.get(i).getContinent().getId()+" \n");
            }
            writer.newLine();
            // write the border to the file
            writer.write(borderStarter);
            writer.newLine();

            writer.newLine();
            //close the writer
            writer.close();


            System.out.println("Saved the map file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
