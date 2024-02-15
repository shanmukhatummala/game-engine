package game.map;

import static game.map.MapHelper.getCountryWithId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import game.pojo.Continent;
import game.pojo.Country;

public class MapValidator {
	
	private List<Country> d_countryList;
	private Map d_mapToValidate;
	
	public MapValidator(Map p_mapToValidate) {
		d_mapToValidate = p_mapToValidate;
		d_countryList = p_mapToValidate.getCountries();
	}
	
	
	public List<Boolean> dfs(Country p_startingCountry) {
		List<Boolean> l_isVisited = new ArrayList<Boolean>();
		for (int i=0; i<d_countryList.size(); i++)
			l_isVisited.add(Boolean.FALSE);
		return dfsStep(p_startingCountry, l_isVisited);
	}

	private List<Boolean> dfsStep(Country p_currentCountry, List<Boolean> p_countryHasBeenSeen) {
		int l_indexCurrentCountry = d_countryList.indexOf(p_currentCountry);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, Boolean.TRUE);
		for (Integer l_neighbourID: p_currentCountry.getNeighbours()) {
			Country l_neighbour = getCountryWithId(d_mapToValidate, l_neighbourID);
			int l_indexNeighbour = d_countryList.indexOf(l_neighbour);
			if (!p_countryHasBeenSeen.get(l_indexNeighbour)) {
				dfsStep(l_neighbour, p_countryHasBeenSeen);
			}
		}
		return p_countryHasBeenSeen;
	}
	
	public boolean mapIsConnected() {
		for(Country l_country: d_countryList) {
			List<Boolean> l_dfsResult = dfs(l_country);
			for (Boolean l_isVisited: l_dfsResult) {
				if (!l_isVisited)
					return false;
			}
		}
		return true;
	}
	
	public List<Boolean> dfs(Integer p_startingCountryID, Continent p_continent) {
		List<Boolean> l_isVisited = new ArrayList<Boolean>();
		for (int i=0; i<p_continent.getCountryIdList().size(); i++)
			l_isVisited.add(Boolean.FALSE);
		return dfsStep(p_startingCountryID, p_continent, l_isVisited);
	}

	private List<Boolean> dfsStep(Integer p_currentCountryID, Continent p_continent, List<Boolean> p_countryHasBeenSeen) {
		int l_indexCurrentCountry = p_continent.getCountryIdList().indexOf(p_currentCountryID);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, Boolean.TRUE);
		for (Integer l_neighbourID: getCountryWithId(d_mapToValidate, p_currentCountryID).getNeighbours()) {
			Country l_neighbour = getCountryWithId(d_mapToValidate, l_neighbourID);
			if (!l_neighbour.getContinent().equals(p_continent))
				continue;
			int l_indexNeighbour = p_continent.getCountryIdList().indexOf(l_neighbourID);
			if (!p_countryHasBeenSeen.get(l_indexNeighbour)) {
				dfsStep(l_neighbourID, p_continent, p_countryHasBeenSeen);
			}
		}
		return p_countryHasBeenSeen;
	}
	
	public boolean continentIsConnected(Continent p_continent) {
		for(Integer l_countryId: p_continent.getCountryIdList()) {
			List<Boolean> l_dfsResult = dfs(l_countryId, p_continent);
			for (Boolean l_isVisited: l_dfsResult) {
				if (!l_isVisited)
					return false;
			}
		}
		return true;
	}
	
	public boolean mapAndContinentsConnected() {
		for (Continent l_continent: d_mapToValidate.getContinents()) {
			if (!continentIsConnected(l_continent))
				return false;
		}
		return (mapIsConnected());
	}
	
}
