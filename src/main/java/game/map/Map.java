package game.map;

import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.List;

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

    public void addContinent(Continent p_continent) {

        for (Continent l_thisContinent : d_continents) {
            if (l_thisContinent.getD_id() == p_continent.getD_id()) {
                throw new IllegalArgumentException("Continent with same id already exists");
            }
        }

        d_continents.add(p_continent);
    }

    public void addCountry(Country p_country) {

        for (Country l_thisCountry : d_countries) {
            if (l_thisCountry.getD_id() == p_country.getD_id()) {
                throw new IllegalArgumentException("Country with same id already exists");
            }
        }
        d_countries.add(p_country);
    }

    public void addPlayer(String p_playerName) {

        for (Player l_thisPlayer : d_players) {
            if (l_thisPlayer.getD_name().equals(p_playerName)) {
                throw new IllegalArgumentException("Player with same name already exists");
            }
        }
        d_players.add(new Player(p_playerName));
    }

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

}
