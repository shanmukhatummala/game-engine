package game.mapfile.writer;

import static game.map.MapHelper.getCountryById;
import static game.util.FileHelper.createCopyOfOriginalFileBeforeSaving;
import static game.util.FileHelper.deleteFileCopy;
import static game.util.FileHelper.restoreOriginalContent;

import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/** Writes the map object into a file in the conquest format */
public class ConquestFileWriter {

    /**
     * writes the map object into a file
     *
     * @param p_path path of the file
     * @param p_map map object
     */
    public void writeConquestFile(String p_path, Map p_map) {

        createCopyOfOriginalFileBeforeSaving(p_path);

        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_path))) {
            // write the continents to the file
            writeContinents(l_writer, p_map);
            // write the countries to the file
            writeCountries(l_writer, p_map);
            // close the writer
            l_writer.close();

            deleteFileCopy(p_path);
            System.out.println("Saved the map file");
        } catch (IOException l_e) {
            restoreOriginalContent(p_path);
            System.out.println(
                    "Something went wrong when saving the file. Your updates to the file are not saved");
        }
    }

    /**
     * this method is called in the saveMap method to write all the continents and the bonus armies
     * in a specific format
     *
     * @param p_writer file writer object
     * @param p_map map object
     * @throws IOException when writer fails to write
     */
    private void writeContinents(BufferedWriter p_writer, Map p_map) throws IOException {
        String l_continentsStarter = "[Continents]";
        p_writer.write(l_continentsStarter);
        p_writer.newLine();
        for (Continent l_continent : p_map.getD_continents()) {
            p_writer.write(l_continent.getD_name() + "=" + l_continent.getD_bonus());
            p_writer.newLine();
        }
        p_writer.newLine();
    }

    /**
     * this method is called in the writeConquestFile method to write all the countries and their
     * neighbors in a specific format
     *
     * @param p_writer file writer object
     * @param p_map map object
     * @throws IOException when writer fails to write
     */
    private void writeCountries(BufferedWriter p_writer, Map p_map) throws IOException {
        String l_countryStarter = "[Territories]";
        p_writer.write(l_countryStarter);
        p_writer.newLine();
        for (Country l_country : p_map.getD_countries()) {
            AtomicReference<String> l_countryRow =
                    new AtomicReference<>(
                            l_country.getD_name() + ",,," + l_country.getD_continent().getD_name());
            l_country
                    .getD_neighborIdList()
                    .forEach(
                            l_neighborId ->
                                    l_countryRow.set(
                                            l_countryRow
                                                    + ","
                                                    + getCountryById(p_map, l_neighborId)
                                                            .getD_name()));
            p_writer.write(l_countryRow.toString());
            p_writer.newLine();
        }
    }
}
