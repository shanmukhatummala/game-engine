 package game.map;
 import game.pojo.Continent;
 import game.pojo.Country;
 import game.pojo.Player;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;

 import static game.map.MapHelper.playerOwnsContinent;
 import static game.map.MapLoader.loadMap;
 import static game.map.MapShower.showMap;
 import static org.junit.jupiter.api.Assertions.*;

 import java.io.ByteArrayOutputStream;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;

 class MapShowerTest {

     private String path;
     private Map map;

     @BeforeEach
     void setUp() {
         path = "src/test/resources/test_load_map.map";
         map = new Map();
     }

     /**
      * Tests the showmap method when the countries are not owned by any player.
      */
     @Test
     void showMapTestNoOwner() {
         // Creating the test data
         loadMap(path, map);
         // Redirecting the standard output for testing process
         ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
         System.setOut(new PrintStream(outputStreamCaptor));
         // Calling the showMap method for testing
         showMap(map);
         // Restore standard output
         System.setOut(System.out);

         // converting the captured output into lines so that we can compare easily with the expected output
         String[] lines = outputStreamCaptor.toString().split("\\r?\\n");

         // Testing the output
         assertEquals("---------------------------Continents---------------------------", lines[0]);
         assertEquals("Norddeutschland", lines[1]);
         assertEquals("    - Continent is not owned by any player yet", lines[2]);
         assertEquals("Westdeutschland", lines[3]);
         assertEquals("    - Continent is not owned by any player yet", lines[4]);
         assertEquals("---------------------------Countries---------------------------", lines[5]);
         assertEquals("Country   : Ostfriesland", lines[6]);
         assertEquals("Continent : Norddeutschland", lines[7]);
         assertEquals("Country is not owned by any player yet", lines[8]);
         assertEquals("Number of armies : 0", lines[9]);
         assertEquals("Neighbors:", lines[10]);
         assertEquals("    - " + "Schleswig", lines[11]);
         assertEquals("    - " + "Holstein", lines[12]);
         assertEquals("----------------------------------------------------------------", lines[13]);
         assertEquals("Country   : Schleswig", lines[14]);
         assertEquals("Continent : Westdeutschland", lines[15]);
         assertEquals("Country is not owned by any player yet", lines[16]);
         assertEquals("Number of armies : 0", lines[17]);
         assertEquals("Neighbors:", lines[18]);
         assertEquals("    - " + "Ostfriesland", lines[19]);
         assertEquals("    - " + "Holstein", lines[20]);
         assertEquals("    - " + "Mecklenburger-Bucht", lines[21]);
         assertEquals("----------------------------------------------------------------", lines[22]);
         assertEquals("Country   : Holstein", lines[23]);
         assertEquals("Continent : Norddeutschland", lines[24]);
         assertEquals("Country is not owned by any player yet", lines[25]);
         assertEquals("Number of armies : 0", lines[26]);
         assertEquals("Neighbors:", lines[27]);
         assertEquals("    - " + "Ostfriesland", lines[28]);
         assertEquals("    - " + "Schleswig", lines[29]);
         assertEquals("    - " + "Hamburg", lines[30]);
         assertEquals("----------------------------------------------------------------", lines[31]);
         assertEquals("Country   : Hamburg", lines[32]);
         assertEquals("Continent : Westdeutschland", lines[33]);
         assertEquals("Country is not owned by any player yet", lines[34]);
         assertEquals("Number of armies : 0", lines[35]);
         assertEquals("Neighbors:", lines[36]);
         assertEquals("    - " + "Holstein", lines[37]);
         assertEquals("----------------------------------------------------------------", lines[38]);
         assertEquals("Country   : Mecklenburger-Bucht", lines[39]);
         assertEquals("Continent : Westdeutschland", lines[40]);
         assertEquals("Country is not owned by any player yet", lines[41]);
         assertEquals("Number of armies : 0", lines[42]);
         assertEquals("Neighbors:", lines[43]);
         assertEquals("    - " + "Schleswig", lines[44]);
         assertEquals("    - " + "Holstein", lines[45]);

     }


     /**
      * Tests the showmap method when countries and/or continents are owned by a player.
      */
     @Test
     void showMapTestOwner() {

         // Creating continents
         Continent norddeutschland = new Continent(1, "Norddeutschland",List.of(1, 2), 5);
         Continent westdeutschland = new Continent(2, "Westdeutschland",List.of(3, 4), 5);

         // Creating countries
         Country ostfriesland = new Country(1, "Ostfriesland", norddeutschland, new ArrayList<>(Arrays.asList(2,3)), 0);
         Country holstein = new Country(2, "Holstein", norddeutschland, new ArrayList<>(Arrays.asList(1,3)), 0);
         Country schleswig = new Country(3, "Schleswig", westdeutschland, new ArrayList<>(Arrays.asList(1,2,4)), 0);
         Country hamburg = new Country(4, "Hamburg", westdeutschland, new ArrayList<>(Arrays.asList(3)),0);

         // Creating players
         Player player1 = new Player("Player1", List.of(ostfriesland, holstein, schleswig));
         Player player2 = new Player("Player2", List.of(hamburg));

         // Loading the map with above continents, countries, players
         map.getD_countries().addAll(Arrays.asList(ostfriesland, holstein, schleswig, hamburg));
         map.getD_continents().addAll(Arrays.asList(norddeutschland,westdeutschland ));
         map.getD_players().addAll(Arrays.asList(player1, player2));

         // Redirecting the standard output for testing process
         ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
         System.setOut(new PrintStream(outputStreamCaptor));

         // Calling the showMap method for testing
         showMap(map);

         // Restore standard output
         System.setOut(System.out);

         // converting the captured output into lines so that we can compare easily with the expected output
         String[] lines = outputStreamCaptor.toString().split("\\r?\\n");

         // Testing the output
         assertEquals("---------------------------Continents---------------------------", lines[0]);
         assertEquals("Norddeutschland", lines[1]);
         assertEquals("    - Continent is owned by: Player1", lines[2]);
         assertEquals("Westdeutschland", lines[3]);
         assertEquals("    - Continent is not owned by any player yet", lines[4]);
         assertEquals("---------------------------Countries---------------------------", lines[5]);
         assertEquals("Country   : Ostfriesland", lines[6]);
         assertEquals("Continent : Norddeutschland", lines[7]);
         assertEquals("Country is owned by: Player1", lines[8]);
         assertEquals("Number of armies : 0", lines[9]);
         assertEquals("Neighbors:", lines[10]);
         assertEquals("    - Holstein", lines[11]);
         assertEquals("    - Schleswig", lines[12]);
         assertEquals("----------------------------------------------------------------", lines[13]);
         assertEquals("Country   : Holstein", lines[14]);
         assertEquals("Continent : Norddeutschland", lines[15]);
         assertEquals("Country is owned by: Player1", lines[16]);
         assertEquals("Number of armies : 0", lines[17]);
         assertEquals("Neighbors:", lines[18]);
         assertEquals("    - Ostfriesland", lines[19]);
         assertEquals("    - Schleswig", lines[20]);
         assertEquals("----------------------------------------------------------------", lines[21]);
         assertEquals("Country   : Schleswig", lines[22]);
         assertEquals("Continent : Westdeutschland", lines[23]);
         assertEquals("Country is owned by: Player1", lines[24]);
         assertEquals("Number of armies : 0", lines[25]);
         assertEquals("Neighbors:", lines[26]);
         assertEquals("    - Ostfriesland", lines[27]);
         assertEquals("    - Holstein", lines[28]);
         assertEquals("    - Hamburg", lines[29]);
         assertEquals("----------------------------------------------------------------", lines[30]);
         assertEquals("Country   : Hamburg", lines[31]);
         assertEquals("Continent : Westdeutschland", lines[32]);
         assertEquals("Country is owned by: Player2", lines[33]);
         assertEquals("Number of armies : 0", lines[34]);
         assertEquals("Neighbors:", lines[35]);
         assertEquals("    - Schleswig", lines[36]);
         assertEquals("----------------------------------------------------------------", lines[37]);
     }
 }
