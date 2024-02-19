package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Country is a POJO representing the Country in a map
 * @author Shanmukha
 */
public class Country {

    private int d_id;
    private String d_name;
    private Continent d_continent;
    private List<Integer> d_neighborIdList;
    private int d_armyCount;


    /**
     * <p>Constructor without arguments for Country</p>
     */
    public Country() {}

    /**
     * <p>Constructor with all arguments for Country</p>
     * @param p_id id of the continent
     * @param p_name name of the continent
     * @param p_continent continent in which this country is present
     * @param p_neighborIdList list of the country ids of neighbours
     * @param p_armyCount army count present in the country
     */
    public Country(int p_id, String p_name, Continent p_continent, List<Integer> p_neighborIdList, int p_armyCount) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_continent = p_continent;
        this.d_neighborIdList = p_neighborIdList;
        this.d_armyCount = p_armyCount;
    }

    /**
     * <p>Constructor with some arguments for Country</p>
     * @param p_id id of the continent
     * @param p_name name of the continent
     * @param p_continent continent in which this country is present
     */
    public Country(int p_id, String p_name, Continent p_continent) {
        this(p_id, p_name, p_continent, new ArrayList<>(), 0);
    }

    /**
     * <p>Adds a neighbor to the list of neighbors</p>
     * @param p_neighborId id of the neighbor
     */
    public void addNeighbor(Integer p_neighborId) {
        this.getD_neighborIdList().add(p_neighborId);
    }

    /**
     * <p>Adds multiple neighbor to the list of neighbors</p>
     * @param p_neighborIds ids of the neighbors
     */
    public void addNeighbors(List<Integer> p_neighborIds) {
        this.getD_neighborIdList().addAll(p_neighborIds);
    }

    /**
     * <p>Getter for country id</p>
     * @return ID of the country
     */
    public int getD_id() {
        return d_id;
    }

    /**
     * <p>Removes a neighbor from the list of neighbors</p>
     * @param p_neighbor_id id of the neighbor to be removed
     */
    public void removeNeighbor(Integer p_neighbor_id) {
        this.getD_neighborIdList().removeIf(id -> Objects.equals(id, p_neighbor_id));
    }


    /**
     * <p>Getter for country name</p>
     * @return name of the country
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * <p>Getter for the continent in which this country is located</p>
     * @return continent in which this country is present
     */
    public Continent getD_continent() {
        return d_continent;
    }

    /**
     * <p>Getter for the neighbors list of this country</p>
     * @return list of neighbors to this country
     */
    public List<Integer> getD_neighborIdList() {
        return d_neighborIdList;
    }

    /**
     * <p>Getter for the army count in this country</p>
     * @return army count present in country
     */
    public int getD_armyCount() {
        return d_armyCount;
    }

    /**
     * <p>Setter for army count</p>
     * @param d_armyCount army count to set in this country
     */
    public void setD_armyCount(int d_armyCount) {
        this.d_armyCount = d_armyCount;
    }

    
    /**
     * <p>Equals method to check the equality between two country objects</p>
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
     * <p>Gives the hashcode of a country object</p>
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_id, d_name, d_continent, d_neighborIdList, d_armyCount);
    }
}
