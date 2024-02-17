package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Java Model for the Game Map. All manipulation of the Map is executed on an object of this class
 */
@AllArgsConstructor
@Data
public class Map {

  private List<String> d_comments;
  private List<String> d_files;
    private final List<Continent> continents;
    private final List<Country> countries;
    private final List<Player> players;
  private TreeMap<Integer, TreeSet<Integer>> d_borders;


  public Map() {
    this(
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>(),
        new TreeMap<>());
  }

    public void addContinent(Continent continent) {

        for (Continent thisContinent : continents) {
            if (thisContinent.getId() == continent.getId()) {
                throw new IllegalArgumentException("Continent with same id already exists");
            }
        }

        continents.add(continent);
    }

    public void addCountry(Country country) {

        for (Country thisCountry : countries) {
            if (thisCountry.getId() == country.getId()) {
                throw new IllegalArgumentException("Country with same id already exists");
            }
        }
        countries.add(country);
    }

    public void addPlayer(String playerName) {

        for (Player thisPlayer : players) {
            if (thisPlayer.getName().equals(playerName)) {
                throw new IllegalArgumentException("Player with same name already exists");
            }
        }
        players.add(new Player(playerName));
    }

    public void removePlayer(String playerName) {

        for (Player thisPlayer : players) {
            if (thisPlayer.getName().equals(playerName)) {
                players.remove(thisPlayer);
                return;
            }
        }
        throw new IllegalArgumentException("No player exists with this name");
    }

    public void addBorder(Integer p_country_1, Integer p_country_2) {
        if (this.d_borders == null) {
            this.d_borders = new TreeMap<>();
        }
          this.d_borders.computeIfAbsent(p_country_1, k -> new TreeSet<>());
          this.d_borders.get(p_country_1).add(p_country_2);
    }

}
