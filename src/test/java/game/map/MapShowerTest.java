// package game.map;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static game.map.MapLoader.loadMap;
// import static game.map.MapShower.showMap;
// import static org.junit.jupiter.api.Assertions.*;

// import java.io.ByteArrayOutputStream;
// import java.io.PrintStream;

// class MapShowerTest {

//     private String path;
//     private Map map;

//     @BeforeEach
//     void setUp() {
//         path = "src/test/resources/test_load_map.map";
//         map = new Map();
//     }

//     @Test
//     void showMapTest() {
//         // Creating the test data
//         loadMap(path, map);
//         // Redirecting the standard output for testing process
//         ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//         System.setOut(new PrintStream(outputStreamCaptor));
//         // Calling the showMap method for testing
//         showMap(map);
//         // Restore standard output
//         System.setOut(System.out);

//         // converting the captured output into lines so that we can compare easily with the expected output
//         String[] lines = outputStreamCaptor.toString().split("\\r?\\n");

//         // Testing the output
//         assertEquals("---------------------------Continents---------------------------", lines[0]);
//         assertEquals("Norddeutschland", lines[1]);
//         assertEquals("Westdeutschland", lines[2]);
//         assertEquals("---------------------------Countries---------------------------", lines[3]);
//         assertEquals("Ostfriesland", lines[4]);
//         assertEquals("Neighbors:", lines[5]);
//         assertEquals("- " + "Schleswig", lines[6]);
//         assertEquals("- " + "Holstein", lines[7]);
//         assertEquals("Schleswig", lines[8]);
//         assertEquals("Neighbors:", lines[9]);
//         assertEquals("- " + "Ostfriesland", lines[10]);
//         assertEquals("- " + "Holstein", lines[11]);
//         assertEquals("- " + "Mecklenburger-Bucht", lines[12]);
//         assertEquals("Holstein", lines[13]);
//         assertEquals("Neighbors:", lines[14]);
//         assertEquals("- " + "Ostfriesland", lines[15]);
//         assertEquals("- " + "Schleswig", lines[16]);
//         assertEquals("- " + "Hamburg", lines[17]);
//         assertEquals("Hamburg", lines[18]);
//         assertEquals("Neighbors:", lines[19]);
//         assertEquals("- " + "Holstein", lines[20]);
//         assertEquals("Mecklenburger-Bucht", lines[21]);
//         assertEquals("Neighbors:", lines[22]);
//         assertEquals("- " + "Schleswig", lines[23]);
//         assertEquals("- " + "Holstein", lines[24]);
//     }
// }
