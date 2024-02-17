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
            for (int i = 0; i < map.getD_continents().size(); i++) {
                writer.write(map.getD_continents().get(i).getD_name()+" ");
                writer.write(map.getD_continents().get(i).getD_bonus()+"  \n");
            }
            writer.newLine();

            // write the countries to the file
            writer.write(countryStarter);
            writer.newLine();
            for (int i = 0; i < map.getD_countries().size(); i++) {
                writer.write(map.getD_countries().get(i).getD_id()+" ");
                writer.write(map.getD_countries().get(i).getD_name()+" ");
                writer.write(map.getD_countries().get(i).getD_continent().getD_id()+" \n");
            }
            writer.newLine();

            // write the border to the file
            writer.write(borderStarter);
            writer.newLine();
            for (int i = 0; i < map.getD_countries().size(); i++) {
                writer.write(map.getD_countries().get(i).getD_id()+" ");
                for (int j = 0; j < map.getD_countries().get(i).getD_neighborIdList().size(); j++) {
                    writer.write(map.getD_countries().get(i).getD_neighborIdList().get(j)+" ");
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
