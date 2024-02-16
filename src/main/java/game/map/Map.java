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
    public static void assignCountries(List<Player> players, List<Country> countries) {

            HashMap<Player, List<Country>> playerCountryMap = new HashMap<>();

            // Check if the countries list is empty
            if (countries.isEmpty()) {
                System.out.println("No countries available to assign.");

            }

            // Shuffle the countries
            Collections.shuffle(countries);

            // Calculate the number of countries each player should have

                int numCountriesPerPlayer = (countries.size()) / (players.size());
                // Assign countries to players
                int currentIndex = 0;
                for (Player player : players) {
                    List<Country> assignedCountries = new ArrayList<>();
                    for (int i = 0; i < numCountriesPerPlayer; i++) {
                        assignedCountries.add(countries.get(currentIndex));
                        currentIndex++;
                    }
                    player.setAssignedCountries(assignedCountries);
                    playerCountryMap.put(player, assignedCountries);
                }

                // Assign remaining countries to players randomly
                while (currentIndex < countries.size()) {
                    List<Integer> playerIndices = IntStream.range(0, players.size()).boxed().collect(toList());
                    Collections.shuffle(playerIndices);
                    int idxInPlayerIndices = 0;
                    while (currentIndex < countries.size()) {
                        Player randomPlayer = players.get(playerIndices.get(idxInPlayerIndices));
                        randomPlayer.getCountries().add(countries.get(currentIndex));
                        idxInPlayerIndices++;
                        currentIndex++;
                        System.out.println(playerCountryMap);
                    }
                }
            }


    }
