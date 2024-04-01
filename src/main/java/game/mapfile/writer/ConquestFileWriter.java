package game.mapfile.writer;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConquestFileWriter {
    public void writeConquestFile(String p_path, Map p_map) {

        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_path))) {
            // write the continents to the file
            writeContinents(l_writer, p_map);
            // write the countries to the file
            writeCountries(l_writer, p_map);
            // write the border to the file
            writeBorders(l_writer, p_map);
            // close the writer
            l_writer.close();
            System.out.println("Saved the map file");
        } catch (IOException l_e) {
            throw new RuntimeException(l_e);
        }
    }

    /**
     * this method is called in the saveMap method to write all the continents and the bonus armies
     * in a specific format
     *
     * @param p_writer file writer object
     * @param p_map map object
     * @throws IOException
     */
    private void writeContinents(BufferedWriter p_writer, Map p_map) throws IOException {
        String l_continentsStarter = "[continents]";
        p_writer.write(l_continentsStarter);
        p_writer.newLine();
        for (Continent l_continent : p_map.getD_continents()) {
            p_writer.write(l_continent.getD_name() + " " + l_continent.getD_bonus());
            p_writer.newLine();
        }
        p_writer.newLine();
    }

    /**
     * this method is called in the saveMap method to write all the continents and the bonus armies
     * in a specific format
     *
     * @param p_writer file writer object
     * @param p_map map object
     * @throws IOException when writer fails to write then exception is thrown
     */
    private void writeCountries(BufferedWriter p_writer, Map p_map) throws IOException {
        String l_countryStarter = "[countries]";
        p_writer.write(l_countryStarter);
        p_writer.newLine();
        for (Country l_country : p_map.getD_countries()) {
            p_writer.write(
                    l_country.getD_id()
                            + " "
                            + l_country.getD_name()
                            + " "
                            + l_country.getD_continent().getD_id());
            p_writer.newLine();
        }
        p_writer.newLine();
    }

    /**
     * this method is called in the saveMap method to write all the border of all countries as in a
     * country and it's neighbors
     *
     * @param p_writer file writer object
     * @param p_map map object
     * @throws IOException when writer fails to write then exception is thrown
     */
    private void writeBorders(BufferedWriter p_writer, Map p_map) throws IOException {
        String l_borderStarter = "[borders]";
        p_writer.write(l_borderStarter);
        p_writer.newLine();
        for (Country l_country : p_map.getD_countries()) {
            p_writer.write(Integer.toString(l_country.getD_id()));
            for (Integer l_neighboursID : l_country.getD_neighborIdList()) {
                p_writer.write(" " + l_neighboursID);
            }
            p_writer.newLine();
        }
    }
}
