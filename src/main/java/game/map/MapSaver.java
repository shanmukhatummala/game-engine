package game.map;

import java.io.BufferedWriter;
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
            for (int i = 0; i < map.getContinents().size(); i++) {
                writer.write(map.getContinents().get(i).getName()+" ");
                writer.write(map.getContinents().get(i).getBonus()+"  \n");
            }
            writer.newLine();

            // write the countries to the file
            writer.write(countryStarter);
            writer.newLine();
            for (int i = 0; i < map.getCountries().size(); i++) {
                writer.write(map.getCountries().get(i).getId()+" ");
                writer.write(map.getCountries().get(i).getName()+" ");
                writer.write(map.getCountries().get(i).getContinent().getId()+" \n");
            }
            writer.newLine();

            // write the border to the file
            writer.write(borderStarter);
            writer.newLine();
            for (int i = 0; i < map.getCountries().size(); i++) {
                writer.write(map.getCountries().get(i).getId()+" ");
                for (int j = 0; j < map.getCountries().get(i).getNeighbors().size(); j++) {
                    writer.write(map.getCountries().get(i).getNeighbors().get(j)+" ");
                }
                writer.newLine();
            }

            //close the writer
            writer.close();
            System.out.println("Saved the map file");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
