package game.map;

import static game.map.MapLoader.loadMap;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import game.map.MapValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapValidatorTest {
	
	private String d_path = "src/test/resources/test_load_map.map";
	private Map d_map;
	
    @BeforeEach
    void setUp() {
    	d_map = new Map();
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
}
