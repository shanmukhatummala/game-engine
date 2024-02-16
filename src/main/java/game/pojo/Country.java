package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Country {

    int id;
    String name;
    Continent continent;
    List<Integer> neighborIdList;
    Player player;
    int armyCount;

    public Country() {}

    public Country(int id, String name, Continent continent, List<Integer> neighborIdList, Player player, int armyCount) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.neighborIdList = neighborIdList;
        this.player = player;
        this.armyCount = armyCount;
    }

    public Country(int id, String name, Continent continent, Player player) {
        this(id, name, continent, new ArrayList<>(), player, 0);
    }

    public Country(int id, String name, Continent continent) {
        this(id, name, continent, null);
    }

    public void addNeighbor(Integer neighborId) {
        this.getNeighborIdList().add(neighborId);
    }

    public void addNeighbors(List<Integer> neighborIds) {
        this.getNeighborIdList().addAll(neighborIds);
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

    public List<Integer> getNeighborIdList() {
        return neighborIdList;
    }

    public Player getPlayer() {
        return player;
    }

    public int getArmyCount() {
        return armyCount;
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof Country)) {
            return false;
        }

        Country otherCountry = (Country) other;

        return Objects.equals(otherCountry.id, this.id)
                && Objects.equals(otherCountry.name, this.name)
                && Objects.equals(otherCountry.continent, this.continent)
                && Objects.equals(otherCountry.neighborIdList, this.neighborIdList)
                && Objects.equals(otherCountry.player, this.player)
                && Objects.equals(otherCountry.armyCount, this.armyCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, continent, neighborIdList, player, armyCount);
    }
}
