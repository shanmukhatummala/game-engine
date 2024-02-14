package game.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Country {

    int id;
    String name;
    Continent continent;
    List<Country> neighbourIdList;
    Player player;
    int armyCount;

    public Country() {}

    public Country(int id, String name, Continent continent, List<Country> neighbourIdList, Player player, int armyCount) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.neighbourIdList = neighbourIdList;
        this.player = player;
        this.armyCount = armyCount;
    }

    public Country(int id, String name, Continent continent, Player player) {
        this(id, name, continent, new ArrayList<>(), player, 0);
    }

    public Country(int id, String name, Continent continent) {
        this(id, name, continent, null);
    }

    public void addNeighbour(Country neighbourId) {
        this.getNeighbours().add(neighbourId);
    }

    public void addNeighbours(List<Country> neighbourIds) {
        this.getNeighbours().addAll(neighbourIds);
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
        return neighbourIdList;
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
                && Objects.equals(otherCountry.neighbourIdList, this.neighbourIdList)
                && Objects.equals(otherCountry.player, this.player)
                && Objects.equals(otherCountry.armyCount, this.armyCount);
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
