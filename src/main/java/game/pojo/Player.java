package game.pojo;

import java.util.ArrayList;
import java.util.List;

public class Player {

    String name;
    List<Country> countries;
    int totalArmyCount;

    public Player() {}

    public Player(String name, List<Country> countries, int totalArmyCount) {
        this.name = name;
        this.countries = countries;
        this.totalArmyCount = totalArmyCount;
    }

    public Player(String name) {
        this.name = name;
        this.countries = new ArrayList<>();
        this.totalArmyCount = 0;
    }

    public String getName() {
        return name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public int getTotalArmyCount() {
        return totalArmyCount;
    }
}
