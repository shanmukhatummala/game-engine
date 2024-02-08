package game.pojo;

import java.util.ArrayList;
import java.util.List;

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
}
