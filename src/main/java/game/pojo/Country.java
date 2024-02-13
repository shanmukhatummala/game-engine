package game.pojo;

import java.util.ArrayList;
import java.util.List;

public class Country {

    int id;
    String name;
    Continent continent;
    List<Country> neighbors;
    Player player;
    int armyCount;

    public Country() {}

    public Country(int id, String name, Continent continent, List<Country> neighbors, Player player, int armyCount) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.neighbors = neighbors;
        this.armyCount = armyCount;
    }

    public Country(int id, String name, Continent continent, Player player) {
        this(id, name, continent, new ArrayList<>(), player, 0);
    }

    public Country(int id, String name, Continent continent) {
        this(id, name, continent, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public List<Country> getNeighbors() {
        return neighbors;
    }

    public Player getPlayer() {
        return player;
    }

    public int getArmyCount() {
        return armyCount;
    }

    public void setNeighbors(List<Country> neighbors) { //siva
        this.neighbors = neighbors;
    }
}
