package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Map is used for storing the details of the map
 * It contains the details of countries, continents and players associated with the map
 * @author Shanmukha
 */
public class Map {

    private final List<Continent> d_continents;
    private final List<Country> d_countries;
    private final List<Player> d_players;


    public Map() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

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

    public List<Continent> getD_continents() {
        return d_continents;
    }

    public List<Country> getD_countries() {
        return d_countries;
    }

    public List<Player> getD_players() {
        return d_players;
    }
    /**
     * <p>This method assigns countries to each player</p>
     * @param P_player represent list of players.
     * @param P_countries represent list of countries.
     * @throws IllegalArgumentException when a player with that name does not exist
     * @author Naveen
     */
    public void assignCountries(List<Player> P_player, List<Country> P_countries) {

        // This snippet checks if there are any countries available to assign.
        // If the list of countries (P_countries) is empty, it prints a message and exits the function.
                if (P_countries.isEmpty()) {
                    System.out.println("No countries available to assign.");
                    return;
                }
        // This line shuffles the list of countries randomly.
                Collections.shuffle(P_countries);

        // This determines how many countries each player ought to obtain.
        // The entire number of countries is divided by the total number of players.




        int l_CountriesPerPlayer = (P_countries.size()) / (P_player.size());

        // This loop assigns countries to each player.
        // It iterates through each player (P_player) and assigns a number of countries equal to l_CountriesPerPlayer.
        // It creates a sublist of countries (l_assignedCountries) and adds them to the player's list of assigned countries.

                int l_Index = 0;
                for (Player l_player : P_player) {
                    List<Country> l_assignedCountries = new ArrayList<>();
                    for (int i = 0; i < l_CountriesPerPlayer; i++) {
                        l_assignedCountries.add(P_countries.get(l_Index));
                        l_Index++;
                    }
                    l_player.getD_countries().addAll(l_assignedCountries);

                }

        // Players are assigned to any remaining countries by this loop.
        // Until every country has been assigned, iterations continue.
        // The player indices list (l_player_Index) is shuffled in order to vary the assignment order.
        // After then, a random player is assigned to each remaining countries.

                while (l_Index < P_countries.size()) {
                    List<Integer> l_player_Index = IntStream.range(0, P_player.size()).boxed().collect(toList());
                    Collections.shuffle(l_player_Index);
                    int l_idx_In_PlayerIndices = 0;
                    while (l_Index < P_countries.size()) {
                        Player l_random_Player = P_player.get(l_player_Index.get(l_idx_In_PlayerIndices));
                        l_random_Player.getD_countries().add(P_countries.get(l_Index));
                        l_idx_In_PlayerIndices++;
                        l_Index++;

                    }
                }
    }


}
