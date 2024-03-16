package game.map;

import static java.util.stream.Collectors.toList;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Map is used for storing the details of the map It contains the details of countries, continents
 * and players associated with the map. All manipulation of the Map is executed on an object of this
 * class
 *
 * @author Shanmukha
 */
@Data
public class Map {

    private final List<Continent> d_continents;
    private final List<Country> d_countries;
    private final List<Player> d_players;

    /** Constructor without arguments for Map */
    public Map() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Constructor with all arguments for Map
     *
     * @param p_continents list of continents
     * @param p_countries list of countries
     * @param p_players list of players
     */
    public Map(List<Continent> p_continents, List<Country> p_countries, List<Player> p_players) {
        this.d_continents = p_continents;
        this.d_countries = p_countries;
        this.d_players = p_players;
    }

    /** This function clears all the data in the map */
    public void clearMap() {
        d_continents.clear();
        d_countries.clear();
        d_players.clear();
    }

    /**
     * This method adds a continent to the list of continents in the map
     *
     * @param p_continent the continent to be added
     * @throws IllegalArgumentException when a continent with same id already exists
     */
    public void addContinent(Continent p_continent) {

        for (Continent thisContinent : d_continents) {
            if (thisContinent.getD_id() == p_continent.getD_id()) {
                throw new IllegalArgumentException("Continent with same id already exists");
            }
        }

        d_continents.add(p_continent);
    }

    /**
     * Removes a continent from the game map.
     *
     * @param p_continent_name The Name of the continent to be removed.
     */
    public void removeContinent(String p_continent_name) {
        List<Integer> l_linked_countries =
                d_continents.stream()
                        .filter(Objects::nonNull)
                        .filter(
                                continent ->
                                        Objects.equals(continent.getD_name(), p_continent_name))
                        .findFirst()
                        .map(continent -> new ArrayList<>(continent.getD_countryIdList()))
                        .orElse(new ArrayList<>());

        boolean l_is_continent_removed =
                d_continents.removeIf(
                        continent -> Objects.equals(continent.getD_name(), p_continent_name));

        if (l_is_continent_removed) {
            l_linked_countries.forEach(this::removeCountry);

            System.out.println("Continent removed successfully!");
        } else {
            System.out.println("No Continent with the given ID exists!");
        }
    }

    /**
     * This method adds a country to the list of countries in the map
     *
     * @param p_country the continent to be added
     * @throws IllegalArgumentException when a country with same id already exists
     */
    public void addCountry(Country p_country) {

        for (Country thisCountry : d_countries) {
            if (thisCountry.getD_id() == p_country.getD_id()) {
                throw new IllegalArgumentException("Country with same id already exists");
            }
        }
        d_countries.add(p_country);
    }

    /**
     * Removes a country from the game map.
     *
     * @param p_country_id The ID of the country to be removed.
     */
    public void removeCountry(Integer p_country_id) {
        Integer l_linked_continent_id =
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(country -> country.getD_id() == p_country_id)
                        .findFirst()
                        .map(Country::getD_continent)
                        .map(Continent::getD_id)
                        .orElse(null);

        boolean l_is_country_removed =
                d_countries.removeIf(country -> country.getD_id() == p_country_id);

        if (l_is_country_removed) {
            d_countries.forEach(country -> country.removeNeighbor(p_country_id));

            if (l_linked_continent_id != null) {
                removeCountryFromContinent(l_linked_continent_id, p_country_id);
            }

            System.out.println("Country removed successfully!");
        } else {
            System.out.println("No Country with the given ID exists!");
        }
    }

    /**
     * Removes a country from the game map.
     *
     * @param p_country_name The Name of the country to be removed.
     */
    public void removeCountry(String p_country_name) {
        Integer l_linked_continent_id =
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(country -> Objects.equals(country.getD_name(), p_country_name))
                        .findFirst()
                        .map(Country::getD_continent)
                        .map(Continent::getD_id)
                        .orElse(null);

        Integer l_country_id_to_be_removed =
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(country -> Objects.equals(country.getD_name(), p_country_name))
                        .findFirst()
                        .map(Country::getD_id)
                        .orElse(null);

        boolean l_is_country_removed =
                d_countries.removeIf(
                        country -> Objects.equals(country.getD_name(), p_country_name));

        if (l_is_country_removed) {
            d_countries.forEach(country -> country.removeNeighbor(l_country_id_to_be_removed));

            if (l_linked_continent_id != null) {
                removeCountryFromContinent(l_linked_continent_id, l_country_id_to_be_removed);
            }

            System.out.println("Country removed successfully!");
        } else {
            System.out.println("No Country with the given ID exists!");
        }
    }

    /**
     * This method adds a player to the list of players
     *
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
     * This method removes a player from the list of players
     *
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
     * Adds a country to a continent.
     *
     * @param p_continent_id The ID of the continent.
     * @param p_country_id The ID of the country to be added to the continent.
     */
    public void addCountryToContinent(Integer p_continent_id, Integer p_country_id) {
        d_continents.stream()
                .filter(c -> c.getD_id() == p_continent_id)
                .forEach(c -> c.getD_countryIdList().add(p_country_id));
    }

    /**
     * Removes a country from a continent.
     *
     * @param p_continent_id The ID of the continent.
     * @param p_country_id The ID of the country to be removed from the continent.
     */
    public void removeCountryFromContinent(Integer p_continent_id, Integer p_country_id) {
        d_continents.stream()
                .filter(c -> c.getD_id() == p_continent_id)
                .forEach(
                        c ->
                                c.getD_countryIdList()
                                        .removeIf(id -> Objects.equals(id, p_country_id)));
    }

    /**
     * This method assigns countries to each player
     *
     * @param p_players represent list of players.
     * @param p_countries represent list of countries.
     * @throws IllegalArgumentException when a player with that name does not exist
     * @return true if countries assigned, else false
     * @author Naveen
     */
    public boolean assignCountries(List<Player> p_players, List<Country> p_countries) {

        // This snippet checks if there are any countries available to assign.
        // If the list of countries (P_countries) is empty, it prints a message and exits the
        // function.
        if (p_countries.isEmpty()) {
            System.out.println("No countries available to assign.");
            return false;
        }

        if (p_players.isEmpty()) {
            System.out.println("No players exist.");
            return false;
        }

        if (p_players.size() > p_countries.size()) {
            System.out.println(
                    "Player count is more than number of countries present in the map -- Edit your map and add more to play or choose a different map");
            return false;
        }

        // This line shuffles the list of countries randomly.
        Collections.shuffle(p_countries);

        // This determines how many countries each player ought to obtain.
        // The entire number of countries is divided by the total number of players.

        int l_CountriesPerPlayer = (p_countries.size()) / (p_players.size());

        // This loop assigns countries to each player.
        // It iterates through each player (P_player) and assigns a number of countries equal to
        // l_CountriesPerPlayer.
        // It creates a sublist of countries (l_assignedCountries) and adds them to the player's
        // list of
        // assigned countries.

        int l_Index = 0;
        for (Player l_player : p_players) {
            List<Country> l_assignedCountries = new ArrayList<>();
            for (int i = 0; i < l_CountriesPerPlayer; i++) {
                l_assignedCountries.add(p_countries.get(l_Index));
                l_Index++;
            }
            l_player.getD_countries().addAll(l_assignedCountries);
        }

        // Players are assigned to any remaining countries by this loop.
        // Until every country has been assigned, iterations continue.
        // The player indices list (l_player_Index) is shuffled in order to vary the assignment
        // order.
        // After then, a random player is assigned to each remaining countries.

        while (l_Index < p_countries.size()) {
            List<Integer> l_player_Index =
                    IntStream.range(0, p_players.size()).boxed().collect(toList());
            Collections.shuffle(l_player_Index);
            int l_idx_In_PlayerIndices = 0;
            while (l_Index < p_countries.size()) {
                Player l_random_Player = p_players.get(l_player_Index.get(l_idx_In_PlayerIndices));
                l_random_Player.getD_countries().add(p_countries.get(l_Index));
                l_idx_In_PlayerIndices++;
                l_Index++;
            }
        }

        return true;
    }

    /**
     * Adds a neighbor to a country.
     *
     * @param p_country_id The ID of the country.
     * @param p_neighbor_country_id The ID of the neighbor country to be added.
     */
    public void addNeighborToCountry(Integer p_country_id, Integer p_neighbor_country_id) {
        Optional<Country> l_country =
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(c -> c.getD_id() == p_country_id)
                        .findFirst();

        Optional<Country> l_neighbor_country =
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(c -> c.getD_id() == p_neighbor_country_id)
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
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(c -> c.getD_id() == p_country_id)
                        .findFirst();

        Optional<Country> l_neighbor_country =
                d_countries.stream()
                        .filter(Objects::nonNull)
                        .filter(c -> c.getD_id() == p_neighbor_country_id)
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

    /** Returns the Highest existing Continent ID */
    public Integer getMaxContinentId() {
        return this.d_continents.stream()
                .filter(Objects::nonNull)
                .map(Continent::getD_id)
                .mapToInt(i -> i)
                .max()
                .orElse(0);
    }

    /** Returns the Highest existing Country ID */
    public Integer getMaxCountryId() {
        return this.d_countries.stream()
                .filter(Objects::nonNull)
                .map(Country::getD_id)
                .mapToInt(i -> i)
                .max()
                .orElse(0);
    }

    /**
     * Retrieves the ID of a country given its name.
     *
     * @param p_country_name The name of the country to search for.
     * @return The ID of the country if found, otherwise returns null.
     */
    public Integer getCountryIdForCountryName(String p_country_name) {
        return this.d_countries.stream()
                .filter(Objects::nonNull)
                .filter(country -> Objects.equals(country.getD_name(), p_country_name))
                .findFirst()
                .map(Country::getD_id)
                .orElse(null);
    }

    /**
     * Retrieves the Country object given its name.
     *
     * @param p_country_name The name of the country to search for.
     * @return The Country object if found, otherwise returns null.
     */
    public Country getCountryForCountryName(String p_country_name) {
        return this.d_countries.stream()
                .filter(Objects::nonNull)
                .filter(country -> Objects.equals(country.getD_name(), p_country_name))
                .findFirst()
                .orElse(null);
    }
}
