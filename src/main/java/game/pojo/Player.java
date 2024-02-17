package game.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    String d_name;
    List<Country> d_countries;
    int d_totalArmyCount;

    public Player() {}

    public Player(String p_name, List<Country> p_countries, int p_totalArmyCount) {
        this.d_name = p_name;
        this.d_countries = p_countries;
        this.d_totalArmyCount = p_totalArmyCount;
    }

    public Player(String p_name) {
        this(p_name, new ArrayList<>(), 0);
    }

    public String getD_name() {
        return d_name;
    }

    public List<Country> getD_countries() {
        return d_countries;
    }

    public int getD_totalArmyCount() {
        return d_totalArmyCount;
    }

    @Override
    public boolean equals(Object p_other) {

        if (p_other == this) {
            return true;
        }

        if (!(p_other instanceof Player)) {
            return false;
        }

        Player l_otherPlayer = (Player) p_other;

        return Objects.equals(l_otherPlayer.d_name, this.d_name)
                && Objects.equals(l_otherPlayer.d_countries, this.d_countries)
                && Objects.equals(l_otherPlayer.d_totalArmyCount, this.d_totalArmyCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_name, d_countries, d_totalArmyCount);
    }
}
