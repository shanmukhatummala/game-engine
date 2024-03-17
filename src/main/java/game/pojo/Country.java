package game.pojo;

import game.map.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Country is a POJO representing the Country in a map
 *
 * @author Shanmukha
 */
@Builder
@Getter
@Setter
public class Country {

    private int d_id;
    private String d_name;
    private Continent d_continent;
    private Set<Integer> d_neighborIdList;
    private int d_armyCount;

    /** Constructor without arguments for Country */
    public Country() {}

    /**
     * Constructor with all arguments for Country
     *
     * @param p_id id of the continent
     * @param p_name name of the continent
     * @param p_continent continent in which this country is present
     * @param p_neighborIdList Set of the country ids of neighbours
     * @param p_armyCount army count present in the country
     */
    public Country(
            int p_id,
            String p_name,
            Continent p_continent,
            Set<Integer> p_neighborIdList,
            int p_armyCount) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_continent = p_continent;
        this.d_neighborIdList = p_neighborIdList;
        this.d_armyCount = p_armyCount;
    }

    /**
     * Constructor with some arguments for Country
     *
     * @param p_id id of the continent
     * @param p_name name of the continent
     * @param p_continent continent in which this country is present
     */
    public Country(int p_id, String p_name, Continent p_continent) {
        this(p_id, p_name, p_continent, new HashSet<>(), 0);
    }

    /**
     * Adds a neighbor to the list of neighbors
     *
     * @param p_neighborId id of the neighbor
     */
    public void addNeighbor(Integer p_neighborId) {
        addNeighbor(p_neighborId, null);
    }

    /**
     * Adds a neighbor to the list of neighbors
     *
     * @param p_neighborId id of the neighbor
     * @param p_map Map object whose d_neighborsGraph to modify if !=null
     */
    public void addNeighbor(Integer p_neighborId, Map p_map) {
        this.getD_neighborIdList().add(p_neighborId);

        if (p_map != null) {
            p_map.addNeighborToNeighborsGraph(this.getD_id(), p_neighborId);
        }
    }

    /**
     * Adds multiple neighbor to the list of neighbors
     *
     * @param p_neighborIds ids of the neighbors
     */
    public void addNeighbors(List<Integer> p_neighborIds) {
        addNeighbors(p_neighborIds, null);
    }

    /**
     * Adds multiple neighbor to the list of neighbors
     *
     * @param p_neighborIds ids of the neighbors
     * @param p_map Map object whose d_neighborsGraph to modify if !=null
     */
    public void addNeighbors(List<Integer> p_neighborIds, Map p_map) {
        if (this.getD_neighborIdList() == null) {
            this.setD_neighborIdList(new HashSet<>());
        }

        this.getD_neighborIdList().addAll(p_neighborIds);

        if (p_map != null) {
            p_map.addNeighborsToNeighborsGraph(this.getD_id(), p_neighborIds);
        }
    }

    /**
     * Getter for country id
     *
     * @return ID of the country
     */
    public int getD_id() {
        return d_id;
    }

    /**
     * Removes a neighbor from the list of neighbors
     *
     * @param p_neighbor_id id of the neighbor to be removed
     */
    public void removeNeighbor(Integer p_neighbor_id) {
        removeNeighbor(p_neighbor_id, null);
    }

    /**
     * Removes a neighbor from the list of neighbors
     *
     * @param p_neighbor_id id of the neighbor to be removed
     * @param p_map Map object whose d_neighborsGraph to modify if !=null
     */
    public void removeNeighbor(Integer p_neighbor_id, Map p_map) {
        this.getD_neighborIdList().removeIf(id -> Objects.equals(id, p_neighbor_id));

        if (p_map != null) {
            p_map.removeNeighborFromNeighborsGraph(this.getD_id(), p_neighbor_id);
        }
    }

    /**
     * Getter for country name
     *
     * @return name of the country
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * Getter for the continent in which this country is located
     *
     * @return continent in which this country is present
     */
    public Continent getD_continent() {
        return d_continent;
    }

    /**
     * Getter for the neighbors list of this country
     *
     * @return list of neighbors to this country
     */
    public Set<Integer> getD_neighborIdList() {
        return d_neighborIdList;
    }

    /**
     * Getter for the army count in this country
     *
     * @return army count present in country
     */
    public int getD_armyCount() {
        return d_armyCount;
    }

    /**
     * Setter for army count
     *
     * @param d_armyCount army count to set in this country
     */
    public void setD_armyCount(int d_armyCount) {
        this.d_armyCount = d_armyCount;
    }

    /**
     * Equals method to check the equality between two country objects
     *
     * @param p_other the object to which this object is compared
     * @return true if the contents of the objects are equal
     */
    @Override
    public boolean equals(Object p_other) {

        if (p_other == this) {
            return true;
        }

        if (!(p_other instanceof Country)) {
            return false;
        }

        Country l_otherCountry = (Country) p_other;

        return Objects.equals(l_otherCountry.d_id, this.d_id)
                && Objects.equals(l_otherCountry.d_name, this.d_name)
                && Objects.equals(l_otherCountry.d_continent, this.d_continent)
                && Objects.equals(l_otherCountry.d_neighborIdList, this.d_neighborIdList)
                && Objects.equals(l_otherCountry.d_armyCount, this.d_armyCount);
    }

    /**
     * Gives the hashcode of a country object
     *
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_id, d_name, d_continent, d_neighborIdList, d_armyCount);
    }
}
