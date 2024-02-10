package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.List;

public class Map {

    public List<Continent> continents;
    public List<Country> countries;
    public List<Player> players;

    public Map() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Map(List<Continent> continents, List<Country> countries, List<Player> players) {
        this.continents = continents;
        this.countries = countries;
        this.players = players;
    }

    public void addContinent(Continent continent) {
        continents.add(continent);
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void addPlayer(Player player) {
        players.add(player);
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
