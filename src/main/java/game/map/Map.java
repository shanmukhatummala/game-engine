package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.*;


import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Map is used for storing the details of the map
 * It contains the details of countries, continents and players associated with the map
 * @author Shanmukha
 * Java Model for the Game Map. All manipulation of the Map is executed on an object of this class
 */
//@AllArgsConstructor
@Data
public class Map {

    private final List<Continent> d_continents;
    private final List<Country> d_countries;
    private final List<Player> d_players;

    /**
     * <p>Constructor without arguments for Map</p>
     */
    public Map() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * <p>Constructor with all arguments for Map</p>
     * @param p_continents list of continents
     * @param p_countries list of countries
     * @param p_players list of players
     */
    public Map(List<Continent> p_continents, List<Country> p_countries, List<Player> p_players) {
        this.d_continents = p_continents;
        this.d_countries = p_countries;
        this.d_players = p_players;
    }

    /**
     * <p>This method adds a continent to the list of continents in the map</p>
     * @param p_continent the continent to be added
     * @throws IllegalArgumentException when a continent with same id already exists
     */
    public void addContinent(Continent p_continent) {

        for (Continent l_thisContinent : d_continents) {
            if (l_thisContinent.getD_id() == p_continent.getD_id()) {
                throw new IllegalArgumentException("Continent with same id already exists");
            }
        }

        d_continents.add(p_continent);
    }

    /**
     * <p>This method adds a country to the list of countries in the map</p>
     * @param p_country the continent to be added
     * @throws IllegalArgumentException when a country with same id already exists
     */
    public void addCountry(Country p_country) {

        for (Country l_thisCountry : d_countries) {
            if (l_thisCountry.getD_id() == p_country.getD_id()) {
                throw new IllegalArgumentException("Country with same id already exists");
            }
        }
        d_countries.add(p_country);
    }


  /**
   * Adds a continent to the game map.
   *
   * @param continent The continent to be added.
   * @throws IllegalArgumentException If a continent with the same ID already exists.
   */
  public void addContinent(Continent continent) {

    for (Continent thisContinent : d_continents) {
      if (thisContinent.getD_id() == continent.getD_id()) {
        throw new IllegalArgumentException("Continent with same id already exists");
      }
    }

      d_continents.add(continent);
    System.out.println("Continent added successfully!");
  }

  /**
   * Removes a continent from the game map.
   *
   * @param p_continent_id The ID of the continent to be removed.
   */
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

  /**
   * <p>This method adds a player to the list of players</p>
   * @param p_playerName the player name to be added
   * @throws IllegalArgumentException when a player with same name already exists
   */
  public void addPlayer(String p_playerName) {

    for (Player l_thisPlayer : d_players) {
      if (l_thisPlayer.getD_name().equals(p_playerName)) {
        throw new IllegalArgumentException("Player with same name already exists");
      }
    }
    d_players.add(new Player(p_playerName));
  }

/**
   * Adds a country to the game map.
   *
   * @param country The country to be added.
   * @throws IllegalArgumentException If a country with the same ID already exists.
   */
  public void addCountry(Country country) {

    for (Country thisCountry : countries) {
      if (thisCountry.getId() == country.getId()) {
        throw new IllegalArgumentException("Country with same id already exists");
      }
    }
    countries.add(country);
    System.out.println("Country added successfully!");
  }

    /**
     * <p>This method removes a player from the list of players</p>
     * @param p_playerName the player name to be removed
     * @throws IllegalArgumentException when a player with that name does not exist
     */
    public void removePlayer(String p_playerName) {

        for (Player l_thisPlayer : d_players) {
            if (l_thisPlayer.getD_name().equals(p_playerName)) {
                d_players.remove(l_thisPlayer);
                return;
            }
        }
        throw new IllegalArgumentException("No player exists with this name");
    }


  /**
   * Removes a country from the game map.
   *
   * @param p_country_id The ID of the country to be removed.
   */
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



    public List<Country> getD_countries() {
        return d_countries;
    }


  /**
   * Removes a country from a continent.
   *
   * @param p_continent_id The ID of the continent.
   * @param p_country_id The ID of the country to be removed from the continent.
   */
  public void removeCountryFromContinent(Integer p_continent_id, Integer p_country_id) {
    continents.stream()
        .filter(c -> c.getId() == p_continent_id)
        .forEach(c -> c.getCountryIdList().removeIf(id -> Objects.equals(id, p_country_id)));
  }

  /**
   * Adds a country to a continent.
   *
   * @param p_continent_id The ID of the continent.
   * @param p_country_id The ID of the country to be added to the continent.
   */
  public void addCountryToContinent(Integer p_continent_id, Integer p_country_id) {
    continents.stream()
        .filter(c -> c.getId() == p_continent_id)
        .forEach(c -> c.getCountryIdList().add(p_country_id));
  }

  /**
   * Adds a player to the game.
   *
   * @param playerName The name of the player to be added.
   * @throws IllegalArgumentException If a player with the same name already exists.
   */
  public void addPlayer(String playerName) {

    for (Player thisPlayer : players) {
      if (thisPlayer.getName().equals(playerName)) {
        throw new IllegalArgumentException("Player with same name already exists");
      }
    }
    players.add(new Player(playerName));
  }


    public List<Player> getD_players() {
        return d_players;
    }


    /**
     * <p>This method assigns countries to each player</p>
     *
     * @param p_player represent list of players.
     * @param p_countries represent list of countries.
     * @throws IllegalArgumentException when a player with that name does not exist
     * @author Naveen
     */
    public void assignCountries(List<Player> p_player, List<Country> p_countries) {

        // This snippet checks if there are any countries available to assign.
        // If the list of countries (P_countries) is empty, it prints a message and exits the function.
                if (p_countries.isEmpty()) {
                    System.out.println("No countries available to assign.");
                    return;
                }
        // This line shuffles the list of countries randomly.
                Collections.shuffle(p_countries);

        // This determines how many countries each player ought to obtain.
        // The entire number of countries is divided by the total number of players.




        int l_CountriesPerPlayer = (p_countries.size()) / (p_player.size());

        // This loop assigns countries to each player.
        // It iterates through each player (P_player) and assigns a number of countries equal to l_CountriesPerPlayer.
        // It creates a sublist of countries (l_assignedCountries) and adds them to the player's list of assigned countries.

                int l_Index = 0;
                for (Player l_player : p_player) {
                    List<Country> l_assignedCountries = new ArrayList<>();
                    for (int i = 0; i < l_CountriesPerPlayer; i++) {
                        l_assignedCountries.add(p_countries.get(l_Index));
                        l_Index++;
                    }
                    l_player.getD_countries().addAll(l_assignedCountries);

                }

        // Players are assigned to any remaining countries by this loop.
        // Until every country has been assigned, iterations continue.
        // The player indices list (l_player_Index) is shuffled in order to vary the assignment order.
        // After then, a random player is assigned to each remaining countries.

                while (l_Index < p_countries.size()) {
                    List<Integer> l_player_Index = IntStream.range(0, p_player.size()).boxed().collect(toList());
                    Collections.shuffle(l_player_Index);
                    int l_idx_In_PlayerIndices = 0;
                    while (l_Index < p_countries.size()) {
                        Player l_random_Player = p_player.get(l_player_Index.get(l_idx_In_PlayerIndices));
                        l_random_Player.getD_countries().add(p_countries.get(l_Index));
                        l_idx_In_PlayerIndices++;
                        l_Index++;

                    }
                }
    }


  /**
   * Removes a player from the game.
   *
   * @param playerName The name of the player to be removed.
   * @throws IllegalArgumentException If no player exists with the given name.
   */
  public void removePlayer(String playerName) {
    for (Player thisPlayer : players) {
      if (thisPlayer.getName().equals(playerName)) {
        players.remove(thisPlayer);
        return;
      }
    }
    throw new IllegalArgumentException("No player exists with this name");
  }

  /**
   * Adds a neighbor to a country.
   *
   * @param p_country_id The ID of the country.
   * @param p_neighbor_country_id The ID of the neighbor country to be added.
   */
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

  /**
   * Removes a neighbor from a country.
   *
   * @param p_country_id The ID of the country.
   * @param p_neighbor_country_id The ID of the neighbor country to be removed.
   */
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
