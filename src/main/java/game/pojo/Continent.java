package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Continent {

    int id;
    String name;
    List<Country> countries;
    int bonus;

    public Continent() {}

    public Continent(int id, String name, List<Country> countries, int bonus) {
        this.id = id;
        this.name = name;
        this.countries = countries;
        this.bonus = bonus;
    }

    public Continent(int id, String name, int bonus) {
        this(id, name, new ArrayList<>(), bonus);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public int getBonus() {
        return bonus;
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
                && Objects.equals(otherContinent.countries, this.countries)
                && Objects.equals(otherContinent.bonus, this.bonus);
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
