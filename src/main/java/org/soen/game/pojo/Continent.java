package org.soen.game.pojo;

import java.util.ArrayList;
import java.util.List;

public class Continent {

    int id;
    String name;
    List<Country> countries;

    public Continent() {}

    public Continent(int id, String name, List<Country> countries) {
        this.id = id;
        this.name = name;
        this.countries = countries;
    }

    public Continent(int id, String name) {
        this.id = id;
        this.name = name;
        this.countries = new ArrayList<>();
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
}
