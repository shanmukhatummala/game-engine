package game.pojo;

import java.util.ArrayList;
import java.util.List;

public class Country {

    int id;
    String name;
    Continent continent;
    List<Country> neighbours;
    Player player;
    int armyCount;
    List<Integer> borderIds; //siva

    public Country() {}

    public Country(int id, String name, Continent continent, List<Country> neighbours, Player player, int armyCount, List<Integer> borderIds) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.neighbours = neighbours;
        this.armyCount = armyCount;
        this.borderIds = borderIds; //siva
    }

    public Country(int id, String name, Continent continent, Player player) {
        this(id, name, continent, new ArrayList<>(), player, 0, new ArrayList<>());
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

    public List<Country> getNeighbours() {
        return neighbours;
    }

    public Player getPlayer() {
        return player;
    }

    public int getArmyCount() {
        return armyCount;
    }

    public List<Integer> getBorderIds() { return borderIds; }

    public void setBorderIds(List<Integer> borderIds) { //siva
        this.borderIds = borderIds;
    }

    public void setNeighbours(List<Country> neighbours) { //siva
        this.neighbours = neighbours;
    }
}
