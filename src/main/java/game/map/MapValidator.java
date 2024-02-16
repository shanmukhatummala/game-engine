package game.map;

import static game.map.MapHelper.getCountryWithId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import game.pojo.Continent;
import game.pojo.Country;
/**
 * Class responsible for validating the map upon loading/saving and during the editing process </br>
 * We consider that a map is correct when it represents a connected graph, each continent
 * also represents a connected subgraph, there are no empty continents and each country has to
 * have a unique name and id.
 */
public class MapValidator {
	
	private Map d_mapToValidate;
	
	/**
	 * Initializes the MapValidator object with the map we want to validate.
	 * Since the reference to the map won't change, the map can be edited without changing the MapValidator object.
	 * @param p_mapToValidate The map we wish to validate.
	 */
	public MapValidator(Map p_mapToValidate) {
		d_mapToValidate = p_mapToValidate;
	}
	
	/**
	 * Implementation of the DFS (depth-first search) algorithm.
	 * @param p_startingCountry The first country explored in the DFS
	 * @return A list that represents whether or not each country has been visited during the DFS.
	 *  The index of the list corresponds to those in the d_coutries data attribute of the Map object.
	 */
	public List<Boolean> dfs(Country p_startingCountry) {
		List<Boolean> l_isVisited = new ArrayList<Boolean>();
		for (int i=0; i<d_mapToValidate.getCountries().size(); i++)
			l_isVisited.add(Boolean.FALSE);
		return dfsStep(p_startingCountry, l_isVisited);
	}

	/**
	 * Recursive function for the DFS. The algorithm flags the current country as visited and the function is
	 * called recursively for each neighbor that hasn't been seen yet.
	 * @param p_currentCountry The country being visited.
	 * @param p_countryHasBeenSeen List representing if each country is seen or not
	 * @return The list where we can see if a country has been visited or not
	 */
	private List<Boolean> dfsStep(Country p_currentCountry, List<Boolean> p_countryHasBeenSeen) {
		int l_indexCurrentCountry = d_mapToValidate.getCountries().indexOf(p_currentCountry);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, Boolean.TRUE);
		// We call dfsStep on each neighbor that has not been seen yet
		for (Integer l_neighbourID: p_currentCountry.getNeighborIdList()) {
			Country l_neighbour = getCountryWithId(d_mapToValidate, l_neighbourID);
			int l_indexNeighbour = d_mapToValidate.getCountries().indexOf(l_neighbour);
			if (!p_countryHasBeenSeen.get(l_indexNeighbour)) {
				dfsStep(l_neighbour, p_countryHasBeenSeen);
			}
		}
		return p_countryHasBeenSeen;
	}
	
	/**
	 * Checks if the map is a connected graph. </br>
	 * To do so, we apply the DFS to each country. If each country can reach any other country,
	 * then the map is a connected graph.
	 * @return true if the map is a connected graph, false otherwise
	 */
	public boolean mapIsConnected() {
		for(Country l_country: d_mapToValidate.getCountries()) {
			List<Boolean> l_dfsResult = dfs(l_country);
			for (Boolean l_isVisited: l_dfsResult) {
				if (!l_isVisited)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Implementation of the DFS, but restricted to the countries that belong to the given continent.
	 * @param p_startingCountryID Id of the first country explored. The country is assumed to belong to the given continent.
	 * @param p_continent Continent on which we want to apply the DFS.
	 * @return A list that represents whether or not each country has been visited during the DFS.
	 * The index of the list corresponds to those in the d_countryIdList data attribute of the Continent object.
	 */
	public List<Boolean> dfs(Integer p_startingCountryID, Continent p_continent) {
		List<Boolean> l_isVisited = new ArrayList<Boolean>();
		for (int i=0; i<p_continent.getCountryIdList().size(); i++)
			l_isVisited.add(Boolean.FALSE);
		return dfsStep(p_startingCountryID, p_continent, l_isVisited);
	}

	/**
	 * Recursive function for the DFS. The algorithm flags the current country as visited and the function is
	 * called recursively for each neighbor that hasn't been seen yet.
	 * @param p_currentCountryID Id of the country being visited.
	 * @param p_continent Continent where the DFS is applied
	 * @param p_countryHasBeenSeen List representing if each country is seen or not
	 * @return The list where we can see if a country has been visited or not
	 */
	private List<Boolean> dfsStep(Integer p_currentCountryID, Continent p_continent, List<Boolean> p_countryHasBeenSeen) {
		int l_indexCurrentCountry = p_continent.getCountryIdList().indexOf(p_currentCountryID);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, Boolean.TRUE);
		// We call dfsStep on each neighbor (in the continent) that has not been seen yet
		for (Integer l_neighbourID: getCountryWithId(d_mapToValidate, p_currentCountryID).getNeighborIdList()) {
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
	
	/**
	 * Checks whether or not the continent is a connected subgraph, using the same process as in the mapIsConnected() method
	 * @param p_continent The continent we want to evaluate.
	 * @return true if the continent is a connected subgraph, false otherwise.
	 */
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
	
	/**
	 * Checks if the map and each continent fulfill the conditions about being connected.
	 * @return true if everything is well connected, false otherwise.
	 */
	public boolean mapAndContinentsConnected() {
		for (Continent l_continent: d_mapToValidate.getContinents()) {
			if (!continentIsConnected(l_continent))
				return false;
		}
		return mapIsConnected();
	}
	
	/**
	 * Verifies if the map is valid. </br>
	 * We consider that a map is correct when it represents a connected graph, each continent
	 * also represents a connected subgraph, there are no empty continents and each country has to
	 * have a unique name and id.
	 * @return true if the map is valid, false otherwise.
	 */
	public boolean isMapValid() {
		
		// Each continent has to have at least one country
		for (Continent l_continent: d_mapToValidate.getContinents()) {
			if (l_continent.getCountryIdList().size() == 0)
				return false;
		}
		// The name and id of each country has to be unique
		for (int l_i = 0; l_i<d_mapToValidate.getCountries().size();l_i++) {
			for (int l_j = l_i+1; l_j<d_mapToValidate.getCountries().size();l_j++) {
				Country l_countryI = d_mapToValidate.getCountries().get(l_i);
				Country l_countryJ = d_mapToValidate.getCountries().get(l_j);
				if (l_countryI.getId() == l_countryJ.getId() || l_countryI.getName().equals(l_countryJ.getName()))
					return false;
			}
		}
		// Finally, everything has to be well connected
		return mapAndContinentsConnected();
	}
	
}
