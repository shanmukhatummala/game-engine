package game.pojo;

import java.util.*;

public class Player {

    String name;
    List<Country> countries;
    int totalArmyCount;
    Queue<Order> d_orderList;

    public Player() {}

    public Player(String name, List<Country> countries, int totalArmyCount) {
        this.name = name;
        this.countries = countries;
        this.totalArmyCount = totalArmyCount;
        this.d_orderList = new LinkedList<>();
    }

    public Player(String name) {
        this(name, new ArrayList<>(), 0);
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




    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) other;

        return Objects.equals(otherPlayer.name, this.name)
                && Objects.equals(otherPlayer.countries, this.countries)
                && Objects.equals(otherPlayer.totalArmyCount, this.totalArmyCount);
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}
