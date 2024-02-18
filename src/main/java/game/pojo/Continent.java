package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Continent {

    int id;
    String name;
    List<Integer> countryIdList;
    int bonus;

    public Continent() {}

    public Continent(int id, String name, List<Integer> countryIdList, int bonus) {
        this.id = id;
        this.name = name;
        this.countryIdList = countryIdList;
        this.bonus = bonus;
    }

    public Continent(int id, String name, int bonus) {
        this(id, name, new ArrayList<>(), bonus);
    }

    public Continent(int id, String name) {
        this(id, name, new ArrayList<>(), 0);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getCountryIdList() {
        return countryIdList;
    }

    public int getBonus() {
        return bonus;
    }

    public void addCountryId(Integer countryId) {
        this.getCountryIdList().add(countryId);
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof Continent)) {
            return false;
        }

        Continent otherContinent = (Continent) other;

        return Objects.equals(otherContinent.id, this.id)
                && Objects.equals(otherContinent.name, this.name)
                && Objects.equals(otherContinent.countryIdList, this.countryIdList)
                && Objects.equals(otherContinent.bonus, this.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryIdList, bonus);
    }
}
