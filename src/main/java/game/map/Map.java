package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.List;

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

}
