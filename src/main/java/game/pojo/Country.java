package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Country is a POJO representing the Country in a map
 * @author Shanmukha
 */
public class Country {

    int d_id;
    String d_name;
    Continent d_continent;
    List<Integer> d_neighborIdList;
    Player d_player;
    int d_armyCount;

    public Country() {}

    public Country(int p_id, String p_name, Continent p_continent, List<Integer> p_neighborIdList, Player p_player, int p_armyCount) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_continent = p_continent;
        this.d_neighborIdList = p_neighborIdList;
        this.d_player = p_player;
        this.d_armyCount = p_armyCount;
    }

    public Country(int p_id, String p_name, Continent p_continent, Player p_player) {
        this(p_id, p_name, p_continent, new ArrayList<>(), p_player, 0);
    }

    public Country(int p_id, String p_name, Continent p_continent) {
        this(p_id, p_name, p_continent, null);
    }

    public void addNeighbor(Integer p_neighborId) {
        this.getD_neighborIdList().add(p_neighborId);
    }

    public void addNeighbors(List<Integer> p_neighborIds) {
        this.getD_neighborIdList().addAll(p_neighborIds);
    }

    public int getD_id() {
        return d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public Continent getD_continent() {
        return d_continent;
    }

    public List<Integer> getD_neighborIdList() {
        return d_neighborIdList;
    }

    public Player getD_player() {
        return d_player;
    }

    public int getD_armyCount() {
        return d_armyCount;
    }
    public void setD_armyCount(int d_armyCount) {
        this.d_armyCount = d_armyCount;
    }
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
                && Objects.equals(l_otherCountry.d_player, this.d_player)
                && Objects.equals(l_otherCountry.d_armyCount, this.d_armyCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_id, d_name, d_continent, d_neighborIdList, d_player, d_armyCount);
    }
}
