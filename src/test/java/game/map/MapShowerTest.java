package game.map;

import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;

import static org.junit.jupiter.api.Assertions.*;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class MapShowerTest {
    private String d_path;
    private Map d_map;

    @BeforeEach
    void setUp() {
        d_path = "src/test/resources/germany_test.map";
        d_map = new Map();
    }

    /** Tests the showmap method when the countries are not owned by any player. */
    @Test
    void showMapTestNoOwner() {
        // Creating the test data
        loadMap(d_path, d_map);

        // Redirecting the standard output for testing process and Calling the showMap method for
        // testing
        ByteArrayOutputStream l_output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_output));
        showMap(d_map);

        // Restoring the standard output and converting the captured output into l_lines so that we
        // can
        // compare easily with the expected output
        System.setOut(System.out);
        String[] l_lines = l_output.toString().split("\\r?\\n");

        // Testing the output
        assertEquals(
                "---------------------------Continents---------------------------", l_lines[0]);
        assertEquals("Norddeutschland (bonus: 3)", l_lines[1]);
        assertEquals("    - does not belong to any player", l_lines[2]);
        assertEquals("Westdeutschland (bonus: 4)", l_lines[3]);
        assertEquals("    - does not belong to any player", l_lines[4]);
        assertEquals("---------------------------Countries---------------------------", l_lines[5]);
        assertEquals("Country   : Ostfriesland", l_lines[6]);
        assertEquals("Continent : Norddeutschland", l_lines[7]);
        assertEquals("Country does not belong to any player", l_lines[8]);
        assertEquals("Number of armies : 0", l_lines[9]);
        assertEquals("Neighbors:", l_lines[10]);
        assertEquals("    - " + "Schleswig", l_lines[11]);
        assertEquals("    - " + "Holstein", l_lines[12]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[13]);
        assertEquals("Country   : Schleswig", l_lines[14]);
        assertEquals("Continent : Westdeutschland", l_lines[15]);
        assertEquals("Country does not belong to any player", l_lines[16]);
        assertEquals("Number of armies : 0", l_lines[17]);
        assertEquals("Neighbors:", l_lines[18]);
        assertEquals("    - " + "Ostfriesland", l_lines[19]);
        assertEquals("    - " + "Holstein", l_lines[20]);
        assertEquals("    - " + "Mecklenburger-Bucht", l_lines[21]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[22]);
        assertEquals("Country   : Holstein", l_lines[23]);
        assertEquals("Continent : Norddeutschland", l_lines[24]);
        assertEquals("Country does not belong to any player", l_lines[25]);
        assertEquals("Number of armies : 0", l_lines[26]);
        assertEquals("Neighbors:", l_lines[27]);
        assertEquals("    - " + "Ostfriesland", l_lines[28]);
        assertEquals("    - " + "Schleswig", l_lines[29]);
        assertEquals("    - " + "Hamburg", l_lines[30]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[31]);
        assertEquals("Country   : Hamburg", l_lines[32]);
        assertEquals("Continent : Westdeutschland", l_lines[33]);
        assertEquals("Country does not belong to any player", l_lines[34]);
        assertEquals("Number of armies : 0", l_lines[35]);
        assertEquals("Neighbors:", l_lines[36]);
        assertEquals("    - " + "Holstein", l_lines[37]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[38]);
        assertEquals("Country   : Mecklenburger-Bucht", l_lines[39]);
        assertEquals("Continent : Westdeutschland", l_lines[40]);
        assertEquals("Country does not belong to any player", l_lines[41]);
        assertEquals("Number of armies : 0", l_lines[42]);
        assertEquals("Neighbors:", l_lines[43]);
        assertEquals("    - " + "Schleswig", l_lines[44]);
        assertEquals("    - " + "Holstein", l_lines[45]);
    }

    /** Tests the showmap method when countries and/or continents are owned by a player. */
    @Test
    void showMapTestOwner() {

        // Creating continents, countries and players
        Continent l_norddeutschland = new Continent(1, "Norddeutschland", 5);
        Continent l_westdeutschland = new Continent(2, "Westdeutschland", 5);

        Country l_ostfriesland =
                new Country(
                        1,
                        "Ostfriesland",
                        l_norddeutschland,
                        new HashSet<>(Arrays.asList(2, 3)),
                        0);
        Country l_holstein =
                new Country(
                        2, "Holstein", l_norddeutschland, new HashSet<>(Arrays.asList(1, 3)), 0);
        Country l_schleswig =
                new Country(
                        3,
                        "Schleswig",
                        l_westdeutschland,
                        new HashSet<>(Arrays.asList(1, 2, 4)),
                        0);
        Country l_hamburg =
                new Country(4, "Hamburg", l_westdeutschland, new HashSet<>(List.of(3)), 0);

        Player l_player1 = new Player("Player1", List.of(l_ostfriesland, l_holstein, l_schleswig));
        Player l_player2 = new Player("Player2", List.of(l_hamburg));

        // Loading the map with above continents, countries, players
        d_map.getD_countries()
                .addAll(Arrays.asList(l_ostfriesland, l_holstein, l_schleswig, l_hamburg));
        d_map.getD_continents().addAll(Arrays.asList(l_norddeutschland, l_westdeutschland));
        d_map.getD_players().addAll(Arrays.asList(l_player1, l_player2));

        // Redirecting the standard output for testing process and Calling the showMap method for
        // testing
        ByteArrayOutputStream l_output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_output));
        showMap(d_map);

        // Restoring the standard output and converting the captured output into l_lines so that we
        // can
        // compare easily with the expected output
        System.setOut(System.out);
        String[] l_lines = l_output.toString().split("\\r?\\n");

        // Testing the output
        assertEquals(
                "---------------------------Continents---------------------------", l_lines[0]);
        assertEquals("Norddeutschland (bonus: 5)", l_lines[1]);
        assertEquals("    - belongs to: Player1", l_lines[2]);
        assertEquals("Westdeutschland (bonus: 5)", l_lines[3]);
        assertEquals("    - does not belong to any player", l_lines[4]);
        assertEquals("---------------------------Countries---------------------------", l_lines[5]);
        assertEquals("Country   : Ostfriesland", l_lines[6]);
        assertEquals("Continent : Norddeutschland", l_lines[7]);
        assertEquals("Country belongs to: Player1", l_lines[8]);
        assertEquals("Number of armies : 0", l_lines[9]);
        assertEquals("Neighbors:", l_lines[10]);
        assertEquals("    - Holstein", l_lines[11]);
        assertEquals("    - Schleswig", l_lines[12]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[13]);
        assertEquals("Country   : Holstein", l_lines[14]);
        assertEquals("Continent : Norddeutschland", l_lines[15]);
        assertEquals("Country belongs to: Player1", l_lines[16]);
        assertEquals("Number of armies : 0", l_lines[17]);
        assertEquals("Neighbors:", l_lines[18]);
        assertEquals("    - Ostfriesland", l_lines[19]);
        assertEquals("    - Schleswig", l_lines[20]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[21]);
        assertEquals("Country   : Schleswig", l_lines[22]);
        assertEquals("Continent : Westdeutschland", l_lines[23]);
        assertEquals("Country belongs to: Player1", l_lines[24]);
        assertEquals("Number of armies : 0", l_lines[25]);
        assertEquals("Neighbors:", l_lines[26]);
        assertEquals("    - Ostfriesland", l_lines[27]);
        assertEquals("    - Holstein", l_lines[28]);
        assertEquals("    - Hamburg", l_lines[29]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[30]);
        assertEquals("Country   : Hamburg", l_lines[31]);
        assertEquals("Continent : Westdeutschland", l_lines[32]);
        assertEquals("Country belongs to: Player2", l_lines[33]);
        assertEquals("Number of armies : 0", l_lines[34]);
        assertEquals("Neighbors:", l_lines[35]);
        assertEquals("    - Schleswig", l_lines[36]);
        assertEquals(
                "----------------------------------------------------------------", l_lines[37]);
    }
}
