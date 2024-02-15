package game.map;

import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class MapSaver {
    public static void saveMap(String p_path, Map p_map) {

        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_path))) {
            // write the continents to the file
            writeContinents(l_writer, p_map);
            // write the countries to the file
            writeCountries(l_writer, p_map);
            // write the border to the file
            writeBorders(l_writer,p_map);
            //close the writer
            l_writer.close();
            System.out.println("Saved the map file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void writeContinents(BufferedWriter p_writer, Map p_map)throws IOException{
        String l_continentsStarter = "[continents]";
        p_writer.write(l_continentsStarter);
        p_writer.newLine();
        for (Continent l_continent: p_map.getContinents()) {
            p_writer.write(l_continent.getName()+" "+l_continent.getBonus());
            p_writer.newLine();
        }
        p_writer.newLine();
    }


    private static void writeCountries(BufferedWriter p_writer, Map p_map)throws IOException{
        String l_countryStarter = "[countries]";
        p_writer.write(l_countryStarter);
        p_writer.newLine();
        for(Country l_country: p_map.getCountries()){
            p_writer.write(l_country.getId()+" "+ l_country.getName()+" "+ l_country.getContinent().getId());
            p_writer.newLine();
        }
        p_writer.newLine();
    }

    private static void writeBorders(BufferedWriter p_writer, Map p_map)throws IOException{
        String l_borderStarter = "[borders]";
        p_writer.write(l_borderStarter);
        p_writer.newLine();
        for(Country l_country: p_map.getCountries()){
            p_writer.write(l_country.getId()+" ");
            for (Integer l_neighboursID: l_country.getNeighbours()) {
                p_writer.write(l_neighboursID+" ");
            }
            p_writer.newLine();
        }
    }




}
