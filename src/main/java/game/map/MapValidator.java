package game.map;

import java.util.List;
import java.util.Stack;

import game.pojo.Country;

public class MapValidator {
	
	private List<Country> d_countryList;
	private Map d_mapToValidate;
	
	public MapValidator(Map p_mapToValidate) {
		d_mapToValidate = p_mapToValidate;
		d_countryList = p_mapToValidate.getCountries();
	}
	
	
	public List<Country> dfs(Country p_startingCountry) {
		
	}

	private List<Boolean> dfsStep(Country p_currentCountry, List<Boolean> p_countryHasBeenSeen) {
		int l_indexCurrentCountry = d_countryList.indexOf(p_currentCountry);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, true);
		for (Country l_neighbour: p_currentCountry.getNeighbours()) {
			int l_indexNeighbour = d_countryList.indexOf(l_neighbour);
			if (!p_countryHasBeenSeen.get(l_indexNeighbour)) {
				dfsStep(l_neighbour, p_countryHasBeenSeen);
			}
		}
		return p_countryHasBeenSeen;
	}
	
}
