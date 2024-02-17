package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Map {

    private final List<Continent> continents;
    private final List<Country> countries;
    private final List<Player> players;

    public Map() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Map(List<Continent> continents, List<Country> countries, List<Player> players) {
        this.continents = continents;
        this.countries = countries;
        this.players = players;
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

    public List<Continent> getContinents() {
        return continents;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Player> getPlayers() {
        return players;
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
                    player.getCountries().addAll(assignedCountries);

                }

                while (Index < countries.size()) {
                    List<Integer> player_Index = IntStream.range(0, players.size()).boxed().collect(toList());
                    Collections.shuffle(player_Index);
                    int idx_In_PlayerIndices = 0;
                    while (Index < countries.size()) {
                        Player random_Player = players.get(player_Index.get(idx_In_PlayerIndices));
                        random_Player.getCountries().add(countries.get(Index));
                        idx_In_PlayerIndices++;
                        Index++;

                    }
                }
            }


    }
