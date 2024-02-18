package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    System.out.println("Continent added successfully!");
  }

  public void removeContinent(Integer p_continent_id) {
    List<Integer> l_linked_countries =
        continents.stream()
            .filter(Objects::nonNull)
            .filter(continent -> continent.getId() == p_continent_id)
            .findFirst()
            .map(continent -> new ArrayList<>(continent.getCountryIdList()))
            .orElse(new ArrayList<>());

    Boolean l_is_continent_removed =
        continents.removeIf(continent -> continent.getId() == p_continent_id);

    if (l_is_continent_removed) {
      l_linked_countries.forEach(this::removeCountry);

      System.out.println("Continent removed successfully!");
    } else {
      System.out.println("No Continent with the given ID exists!");
    }
  }

  public void addCountry(Country country) {

    for (Country thisCountry : countries) {
      if (thisCountry.getId() == country.getId()) {
        throw new IllegalArgumentException("Country with same id already exists");
      }
    }
    countries.add(country);
    System.out.println("Country added successfully!");
  }

  public void removeCountry(Integer p_country_id) {
    Integer l_linked_continent_id =
        countries.stream()
            .filter(Objects::nonNull)
            .filter(country -> country.getId() == p_country_id)
            .findFirst()
            .map(Country::getContinent)
            .map(Continent::getId)
            .orElse(null);

    Boolean l_is_country_removed = countries.removeIf(country -> country.getId() == p_country_id);

    if (l_is_country_removed) {
      countries.forEach(country -> country.removeNeighbor(p_country_id));

      if (l_linked_continent_id != null) {
        removeCountryFromContinent(l_linked_continent_id, p_country_id);
      }

      System.out.println("Country removed successfully!");
    } else {
      System.out.println("No Country with the given ID exists!");
    }
  }

  public void removeCountryFromContinent(Integer p_continent_id, Integer p_country_id) {
    continents.stream()
        .filter(c -> c.getId() == p_continent_id)
        .forEach(c -> c.getCountryIdList().removeIf(id -> Objects.equals(id, p_country_id)));
  }

  public void addCountryToContinent(Integer p_continent_id, Integer p_country_id) {
    continents.stream()
            .filter(c -> c.getId() == p_continent_id)
            .forEach(c -> c.getCountryIdList().add(p_country_id));
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

  public void addNeighborToCountry(Integer p_country_id, Integer p_neighbor_country_id) {
    Optional<Country> l_country =
        countries.stream()
            .filter(Objects::nonNull)
            .filter(c -> c.getId() == p_country_id)
            .findFirst();

    Optional<Country> l_neighbor_country =
        countries.stream()
            .filter(Objects::nonNull)
            .filter(c -> c.getId() == p_neighbor_country_id)
            .findFirst();

    if (l_country.isEmpty()) {
      System.out.println("Country with input ID does not exist!");
      return;
    } else if (l_neighbor_country.isEmpty()) {
      System.out.println("Neighbor Country with input ID does not exist!");
      return;
    }

    l_country.get().addNeighbor(p_neighbor_country_id);
    System.out.println("Neighbor Country added successfully!");
  }

  public void removeNeighborFromCountry(Integer p_country_id, Integer p_neighbor_country_id) {
    Optional<Country> l_country =
        countries.stream()
            .filter(Objects::nonNull)
            .filter(c -> c.getId() == p_country_id)
            .findFirst();

    Optional<Country> l_neighbor_country =
        countries.stream()
            .filter(Objects::nonNull)
            .filter(c -> c.getId() == p_neighbor_country_id)
            .findFirst();

    if (l_country.isEmpty()) {
      System.out.println("Country with input ID does not exist!");
      return;
    } else if (l_neighbor_country.isEmpty()) {
      System.out.println("Neighbor Country with input ID does not exist!");
      return;
    }

    l_country.get().removeNeighbor(p_neighbor_country_id);
    System.out.println("Neighbor Country removed successfully!");
  }
}
