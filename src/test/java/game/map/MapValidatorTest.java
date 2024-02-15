package game.map;

import static game.map.MapLoader.loadMap;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import game.map.MapValidator;
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
    Continent d_continent1 = new Continent(1, "Norddeutschland", 3);
    Continent d_continent2 = new Continent(2, "Westdeutschland", 4);

    Country d_country1;
    Country d_country2;
    Country d_country3;
    Country d_country4;
    Country d_country5;
	
    @BeforeEach
    void setUp() {
    	d_map = new Map();
        d_continent1 = new Continent(1, "Norddeutschland", 3);
        d_continent2 = new Continent(2, "Westdeutschland", 4);

        d_country1 = new Country(1, "Ostfriesland", d_continent1);
        d_country2 = new Country(2, "Schleswig", d_continent2);
        d_country3 = new Country(3, "Holstein", d_continent1);
        d_country4 = new Country(4, "Hamburg", d_continent2);
        d_country5 = new Country(5, "Mecklenburger-Bucht", d_continent2);
    }

    @AfterEach
    void tearDown() {
    }
    
    @Test
    void testDfsConnectedMap() {
    	loadMap(d_path, d_map);
    	MapValidator l_mapVal = new MapValidator(d_map);
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	for (int i=0; i<d_map.getCountries().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	assertEquals(l_expectedResult, l_mapVal.dfs(d_map.getCountries().get(0)));
    }
    
    @Test
    void testDfsNotConnected() {        
        // There is no connection to l_country4
        d_country1.addNeighbours(Arrays.asList(2, 3));
        d_country2.addNeighbours(Arrays.asList(1, 3, 5));
        d_country3.addNeighbours(Arrays.asList(1, 2));
        d_country4.addNeighbours(List.of(3));
        d_country5.addNeighbours(Arrays.asList(2, 3));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	MapValidator l_mapVal = new MapValidator(d_map);
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	for (int i=0; i<d_map.getCountries().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	l_expectedResult.set(3, Boolean.FALSE);
    	
    	assertEquals(l_expectedResult, l_mapVal.dfs(d_map.getCountries().get(0)));
    }
    
    @Test
    void testDfsConnectedOneWay() {
    	
        d_country1.addNeighbours(Arrays.asList(2, 3));
        d_country2.addNeighbours(Arrays.asList(5));
        d_country3.addNeighbours(Arrays.asList(4));
        
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);
        
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);
        d_map.addCountry(d_country4);
        d_map.addCountry(d_country5);
        
    	MapValidator l_mapVal = new MapValidator(d_map);
    	List<Boolean> l_expectedResult = new ArrayList<Boolean>();
    	for (int i=0; i<d_map.getCountries().size(); i++)
    		l_expectedResult.add(Boolean.TRUE);
    	
    	assertEquals(l_expectedResult, l_mapVal.dfs(d_map.getCountries().get(0)));
    }
}
