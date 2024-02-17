package game.map;

import static game.map.MapLoader.loadMap;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import game.pojo.Continent;
import game.pojo.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapValidatorTest {
	
	private String d_path = "src/test/resources/test_load_map.map";
	private Map d_map;
    Continent d_continent1;
    Continent d_continent2;

    Country d_country1;
    Country d_country2;
    Country d_country3;
    Country d_country4;
    Country d_country5;
    MapValidator d_mapVal = new MapValidator();
	
    @BeforeEach
    void setUp() {
    	
    	// Initialize a simple map, where the borders can be adjusted for each test
    	d_map = new Map();
        d_continent1 = new Continent(1, "Norddeutschland", 3);
        d_continent2 = new Continent(2, "Westdeutschland", 4);

        d_country1 = new Country(1, "Ostfriesland", d_continent1);
        d_country2 = new Country(2, "Schleswig", d_continent2);
        d_country3 = new Country(3, "Holstein", d_continent1);
        d_country4 = new Country(4, "Hamburg", d_continent2);
        d_country5 = new Country(5, "Mecklenburger-Bucht", d_continent2);
        
        d_continent1.addCountryId(1);
        d_continent1.addCountryId(3);
        d_continent2.addCountryId(2);
        d_continent2.addCountryId(4);
        d_continent2.addCountryId(5);
    }

    @AfterEach
    void tearDown() {
    }
    
    /**
     * Tests the DFS algorithm on a connected map.
     */
    @Test
    void testDfsConnectedMap() {
    	loadMap(d_path, d_map);
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	for (int i=0; i<d_map.getD_countries().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	assertEquals(l_expectedResult, d_mapVal.dfs(d_map.getD_countries().get(0), d_map));
    }
    
    /**
     * Tests the DFS on a disconnected map
     */
    @Test
    void testDfsNotConnected() {        
        // There is no connection to l_country4
        d_country1.addNeighbors(Arrays.asList(2, 3));
        d_country2.addNeighbors(Arrays.asList(1, 3, 5));
        d_country3.addNeighbors(Arrays.asList(1, 2));
        d_country4.addNeighbors(List.of(3));
        d_country5.addNeighbors(Arrays.asList(2, 3));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	for (int i=0; i<d_map.getD_countries().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	l_expectedResult.set(3, Boolean.FALSE);
    	
    	assertEquals(l_expectedResult, d_mapVal.dfs(d_map.getD_countries().get(0), d_map));
    }
    
    /**
     * Tests the DFS in the case that the map is connected, but just in one direction.
     * Every country can be reached from the starting country.
     */
    @Test
    void testDfsConnectedOneWay() {
    	
        d_country1.addNeighbors(Arrays.asList(2, 3));
        d_country2.addNeighbors(Arrays.asList(5));
        d_country3.addNeighbors(Arrays.asList(4));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	for (int i=0; i<d_map.getD_countries().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	
    	assertEquals(l_expectedResult, d_mapVal.dfs(d_map.getD_countries().get(0), d_map));
    }
    
    /**
     * Tests the method mapIsConnected on a connected map
     */
    @Test
    void testMapIsConnectedConnectedMap() {
    	loadMap(d_path, d_map);
    	assertTrue(d_mapVal.mapIsConnected(d_map));
    }
    
    /**
     * Tests the method mapIsConnected on a disconnected map
     */
    @Test
    void testMapIsConnectedNotConnected() {        
        // There is no connection to l_country4
        d_country1.addNeighbors(Arrays.asList(2, 3));
        d_country2.addNeighbors(Arrays.asList(1, 3, 5));
        d_country3.addNeighbors(Arrays.asList(1, 2));
        d_country4.addNeighbors(List.of(3));
        d_country5.addNeighbors(Arrays.asList(2, 3));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	assertFalse(d_mapVal.mapIsConnected(d_map));
    }
    
    /**
     * Tests the method mapIsConnected on a map that is connected only in one direction.
     * As such, the tested method should return false.
     */
    @Test
    void testMapIsConnectedConnectedOneWay() {
    	
        d_country1.addNeighbors(Arrays.asList(2, 3));
        d_country2.addNeighbors(Arrays.asList(5));
        d_country3.addNeighbors(Arrays.asList(4));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	assertFalse(d_mapVal.mapIsConnected(d_map));
    }
    
    /**
     * Tests the DFS on a continent that is NOT a connected subgraph.
     */
    @Test
    void testdfsConnectedContinent() {
    	loadMap(d_path, d_map);
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	Continent l_continentToTest = d_map.getD_continents().get(1);
    	for (int i=0; i<l_continentToTest.getD_countryIdList().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	l_expectedResult.set(1, Boolean.FALSE);
    	assertEquals(l_expectedResult, d_mapVal.dfs(l_continentToTest.getD_countryIdList().get(0), l_continentToTest, d_map));
    }
    
    /**
     * Tests the method mapAndContinentsConnected on a connected map where the continents are also connected subgraphs.
     * The map used is a real Domination map.
     */
    @Test
    void testmapAndContinentsConnectedRealMap() {
    	loadMap("src/test/resources/canada.map", d_map);
    	assertTrue(d_mapVal.mapAndContinentsConnected(d_map));
    }
    
    /**
     * Tests the method mapIsValid on a correct map.
     */
    @Test
    void testisMapValidGoodMap() {
    	loadMap("src/test/resources/canada.map", d_map);
    	assertTrue(d_mapVal.isMapValid(d_map));
    }
    
    /**
     * Tests the method mapIsValid on a map where one continent is not a connected subgraph.
     */
    @Test
    void testisMapValidDisconnectedContinent() {
    	loadMap(d_path, d_map);
    	assertFalse(d_mapVal.isMapValid(d_map));
    }
    
    /**
     * Tests the method mapIsValid on a disconnected map.
     */
    @Test
    void testisMapValidDisconnectedMap() {
        d_country1.addNeighbors(Arrays.asList(2, 3));
        d_country2.addNeighbors(Arrays.asList(3, 5));
        d_country3.addNeighbors(Arrays.asList(2, 4));
        d_country4.addNeighbors(List.of(3));
        d_country5.addNeighbors(Arrays.asList(2, 3));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	assertFalse(d_mapVal.isMapValid(d_map));
    }
}
