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
    public void assignCountries(List<Player> players, List<Country> countries) {



                if (countries.isEmpty()) {
                    System.out.println("No countries available to assign.");
                    return;
                }
                Collections.shuffle(countries);

                int CountriesPerPlayer = (countries.size()) / (players.size());

                int Index = 0;
                for (Player player : players) {
                    List<Country> assignedCountries = new ArrayList<>();
                    for (int i = 0; i < CountriesPerPlayer; i++) {
                        assignedCountries.add(countries.get(Index));
                        Index++;
                    }
                    player.getD_countries().addAll(assignedCountries);

                }

                while (Index < countries.size()) {
                    List<Integer> player_Index = IntStream.range(0, players.size()).boxed().collect(toList());
                    Collections.shuffle(player_Index);
                    int idx_In_PlayerIndices = 0;
                    while (Index < countries.size()) {
                        Player random_Player = players.get(player_Index.get(idx_In_PlayerIndices));
                        random_Player.getD_countries().add(countries.get(Index));
                        idx_In_PlayerIndices++;
                        Index++;

                    }
                }
            }


    }
