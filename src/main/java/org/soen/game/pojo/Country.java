package org.soen.game.pojo;

import java.util.ArrayList;
import java.util.List;

public class Country {

    int id;
    String name;
    Continent continent;
    List<Country> neighbours;
    Player player;
    int armyCount;

    public Country() {}

    public Country(int id, String name, Continent continent, List<Country> neighbours, Player player, int armyCount) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.neighbours = neighbours;
        this.armyCount = armyCount;
    }

    public Country(int id, String name, Continent continent, Player player) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.neighbours = new ArrayList<>();
        this.player = player;
        this.armyCount = 0;
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
}
