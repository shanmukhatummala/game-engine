 package game.map;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;

 import static game.map.MapLoader.loadMap;
 import static game.map.MapShower.showMap;
 import static org.junit.jupiter.api.Assertions.*;

 import java.io.ByteArrayOutputStream;
 import java.io.PrintStream;

 class MapShowerTest {

     private String path;
     private Map map;

     @BeforeEach
     void setUp() {
         path = "src/test/resources/test_load_map.map";
         map = new Map();
     }

     @Test
     void showMapTest() {
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
 }
