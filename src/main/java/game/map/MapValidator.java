package game.map;

import static game.map.MapHelper.getCountryWithId;

import java.util.ArrayList;
import java.util.List;

import game.pojo.Continent;
import game.pojo.Country;
/**
 * Class responsible for validating the map upon loading/saving and during the editing process
 * We consider that a map is correct when it represents a connected graph, each continent
 * also represents a connected subgraph, there are no empty continents and each country has to
 * have a unique name and id.
 * Validation of a map is done through the method isMapValid.
 * @author Luca Scistri
 */
public class MapValidator {
	

	
	/**
	 * Implementation of the DFS (depth-first search) algorithm.
	 * @param p_startingCountry The first country explored in the DFS
	 * @param p_mapToValidate The map that we want to validate.
	 * @return A list that represents whether or not each country has been visited during the DFS.
	 *  The index of the list corresponds to those in the d_coutries data attribute of the Map object.
	 */
	public static List<Boolean> dfs(Country p_startingCountry, Map p_mapToValidate) {
		List<Boolean> l_isVisited = new ArrayList<Boolean>();
		for (int i=0; i<p_mapToValidate.getD_countries().size(); i++)
			l_isVisited.add(Boolean.FALSE);
		return dfsStep(p_startingCountry, l_isVisited, p_mapToValidate);
	}

	/**
	 * Recursive function for the DFS. The algorithm flags the current country as visited and the function is
	 * called recursively for each neighbor that hasn't been seen yet.
	 * @param p_currentCountry The country being visited.
	 * @param p_countryHasBeenSeen List representing if each country is seen or not
	 * @param p_mapToValidate The map that we want to validate.
	 * @return The list where we can see if a country has been visited or not
	 */
	private static List<Boolean> dfsStep(Country p_currentCountry, List<Boolean> p_countryHasBeenSeen, Map p_mapToValidate) {
		int l_indexCurrentCountry = p_mapToValidate.getD_countries().indexOf(p_currentCountry);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, Boolean.TRUE);
		// We call dfsStep on each neighbor that has not been seen yet
		for (Integer l_neighbourID: p_currentCountry.getD_neighborIdList()) {
			Country l_neighbour = getCountryWithId(p_mapToValidate, l_neighbourID);
			int l_indexNeighbour = p_mapToValidate.getD_countries().indexOf(l_neighbour);
			if (!p_countryHasBeenSeen.get(l_indexNeighbour)) {
				dfsStep(l_neighbour, p_countryHasBeenSeen, p_mapToValidate);
			}
		}
		return p_countryHasBeenSeen;
	}
	
	/**
	 * Checks if the map is a connected graph.
	 * To do so, we apply the DFS to each country. If each country can reach any other country,
	 * then the map is a connected graph.
	 * @param p_mapToValidate The map that we want to validate.
	 * @return true if the map is a connected graph, false otherwise
	 */
	public static boolean mapIsConnected(Map p_mapToValidate) {
		for(Country l_country: p_mapToValidate.getD_countries()) {
			List<Boolean> l_dfsResult = dfs(l_country, p_mapToValidate);
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
	 * @param p_mapToValidate The map that we want to validate.
	 * @return A list that represents whether or not each country has been visited during the DFS.
	 * The index of the list corresponds to those in the d_countryIdList data attribute of the Continent object.
	 */
	public static List<Boolean> dfs(Integer p_startingCountryID, Continent p_continent, Map p_mapToValidate) {
		List<Boolean> l_isVisited = new ArrayList<Boolean>();
		for (int i=0; i<p_continent.getD_countryIdList().size(); i++)
			l_isVisited.add(Boolean.FALSE);
		return dfsStep(p_startingCountryID, p_continent, l_isVisited, p_mapToValidate);
	}

	/**
	 * Recursive function for the DFS. The algorithm flags the current country as visited and the function is
	 * called recursively for each neighbor that hasn't been seen yet.
	 * @param p_currentCountryID Id of the country being visited.
	 * @param p_continent Continent where the DFS is applied
	 * @param p_countryHasBeenSeen List representing if each country is seen or not
	 * @param p_mapToValidate The map that we want to validate.
	 * @return The list where we can see if a country has been visited or not
	 */
	private static List<Boolean> dfsStep(Integer p_currentCountryID, Continent p_continent, List<Boolean> p_countryHasBeenSeen, Map p_mapToValidate) {
		int l_indexCurrentCountry = p_continent.getD_countryIdList().indexOf(p_currentCountryID);
		p_countryHasBeenSeen.set(l_indexCurrentCountry, Boolean.TRUE);
		// We call dfsStep on each neighbor (in the continent) that has not been seen yet
		for (Integer l_neighbourID: getCountryWithId(p_mapToValidate, p_currentCountryID).getD_neighborIdList()) {
			Country l_neighbour = getCountryWithId(p_mapToValidate, l_neighbourID);
			if (!l_neighbour.getD_continent().equals(p_continent))
				continue;
			int l_indexNeighbour = p_continent.getD_countryIdList().indexOf(l_neighbourID);
			if (!p_countryHasBeenSeen.get(l_indexNeighbour)) {
				dfsStep(l_neighbourID, p_continent, p_countryHasBeenSeen, p_mapToValidate);
			}
		}
		return p_countryHasBeenSeen;
	}
	
	/**
	 * Checks whether or not the continent is a connected subgraph, using the same process as in the mapIsConnected() method
	 * @param p_continent The continent we want to evaluate.
	 * @param p_mapToValidate The map that we want to validate.
	 * @return true if the continent is a connected subgraph, false otherwise.
	 */
	public static boolean continentIsConnected(Continent p_continent, Map p_mapToValidate) {
		for(Integer l_countryId: p_continent.getD_countryIdList()) {
			List<Boolean> l_dfsResult = dfs(l_countryId, p_continent, p_mapToValidate);
			for (Boolean l_isVisited: l_dfsResult) {
				if (!l_isVisited)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the map and each continent fulfill the conditions about being connected.
	 * @param p_mapToValidate The map that we want to validate.
	 * @return true if everything is well connected, false otherwise.
	 */
	public static boolean mapAndContinentsConnected(Map p_mapToValidate) {
		for (Continent l_continent: p_mapToValidate.getD_continents()) {
			if (!continentIsConnected(l_continent, p_mapToValidate))
				return false;
		}
		return mapIsConnected(p_mapToValidate);
	}
	
	/**
	 * Verifies if the map is valid.
	 * We consider that a map is correct when it represents a connected graph, each continent
	 * also represents a connected subgraph, there are no empty continents and each country has to
	 * have a unique name and id.
	 * @param p_mapToValidate The map that we want to validate.
	 * @return true if the map is valid, false otherwise.
	 */
	public static boolean isMapValid(Map p_mapToValidate) {
		
		// Each continent has to have at least one country
		for (Continent l_continent: p_mapToValidate.getD_continents()) {
			if (l_continent.getD_countryIdList().size() == 0)
				return false;
		}
		// The name and id of each country has to be unique
		for (int l_i = 0; l_i<p_mapToValidate.getD_countries().size();l_i++) {
			Country l_countryI = p_mapToValidate.getD_countries().get(l_i);
			for (int l_j = l_i+1; l_j<p_mapToValidate.getD_countries().size();l_j++) {
				Country l_countryJ = p_mapToValidate.getD_countries().get(l_j);
				if (l_countryI.getD_id() == l_countryJ.getD_id() || l_countryI.getD_name().equals(l_countryJ.getD_name()))
					return false;
			}
		}
		// Finally, everything has to be well connected
		return mapAndContinentsConnected(p_mapToValidate);
	}
	
}
