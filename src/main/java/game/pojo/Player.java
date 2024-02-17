package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        this(name, new ArrayList<>(), 0);

    }

    public String getName() {
        return name;
    }

    public List<Country> getCountries() {
        if (countries == null) {
            countries = new ArrayList<>();
        }
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
        return Objects.hash(name, countries, totalArmyCount);
    }



}
