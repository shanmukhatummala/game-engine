package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Continent is a POJO representing the Continent in a map
 * @author Shanmukha
 */
public class Continent {

    int d_id;
    String d_name;
    List<Integer> d_countryIdList;
    int d_bonus;

    public Continent() {}

    public Continent(int p_id, String p_name, List<Integer> p_countryIdList, int p_bonus) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_countryIdList = p_countryIdList;
        this.d_bonus = p_bonus;
    }

    public Continent(int p_id, String p_name, int p_bonus) {
        this(p_id, p_name, new ArrayList<>(), p_bonus);
    }

    public int getD_id() {
        return d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public List<Integer> getD_countryIdList() {
        return d_countryIdList;
    }

    public int getD_bonus() {
        return d_bonus;
    }

    public void addCountryId(Integer p_countryId) {
        this.getD_countryIdList().add(p_countryId);
    }

    @Override
    public boolean equals(Object p_other) {

        if (p_other == this) {
            return true;
        }

        if (!(p_other instanceof Continent)) {
            return false;
        }

        Continent l_otherContinent = (Continent) p_other;

        return Objects.equals(l_otherContinent.d_id, this.d_id)
                && Objects.equals(l_otherContinent.d_name, this.d_name)
                && Objects.equals(l_otherContinent.d_countryIdList, this.d_countryIdList)
                && Objects.equals(l_otherContinent.d_bonus, this.d_bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_id, d_name, d_countryIdList, d_bonus);
    }
}
