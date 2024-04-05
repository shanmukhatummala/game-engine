package strategy;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

public class Aggressive extends PlayerStrategy {

    private boolean d_deployed = false;
    private boolean d_attacked = false;
    private boolean d_moved = false;

    private static Aggressive d_aggressiveStrategy;

    public static Aggressive getAggressiveStrategy() {
        if (d_aggressiveStrategy == null) {
            Aggressive.d_aggressiveStrategy = new Aggressive();
        }
        return d_aggressiveStrategy;
    }

    private Aggressive() {}

    @Override
    public Command createOrder(Map p_map, Player p_player) {
        if (!d_deployed) {
            d_deployed = true;
            return deployCommandOnStrongest(p_player);
        } else if (!d_attacked) {
            d_attacked = true;
            return attackCommandOnStrongest(p_map, p_player);
        } else if (!d_moved) {
            d_moved = true;
            return moveCommandToCentralize(p_map, p_player);
        } else {
            // Reset flags for the next round
            d_deployed = false;
            d_attacked = false;
            d_moved = false;
            return new Command("commit");
        }
    }

    private Command deployCommandOnStrongest(Player p_player) {
        String l_countryName = findStrongestCountry(p_player.getD_countries()).getD_name();
        int l_reinforcementsToDeploy = p_player.getD_reinforcements();
        return CommandParser.parse("deploy " + l_countryName + " " + l_reinforcementsToDeploy)
                .get(0);
    }

    private Command attackCommandOnStrongest(Map p_map, Player p_player) {
        Country l_strongestCountry = findStrongestCountry(p_player.getD_countries());
        Country l_targetCountry = findStrongestCountry(p_map.getD_countries());
        if (l_strongestCountry.equals(l_targetCountry)) {
            // If the strongest country is already the target, skip attacking
            return new Command("commit");
        }
        return CommandParser.parse("attack " + l_strongestCountry.getD_name() + " " + l_targetCountry.getD_name())
                .get(0);
    }

    private Command moveCommandToCentralize(Map p_map, Player p_player) {
        String l_targetCountryName;
        Country l_strongestCountry = findStrongestCountry(p_player.getD_countries());
        List<Integer> l_neighbors = new ArrayList<>(l_strongestCountry.getD_neighborIdList());
        for (int l_neighborId : l_neighbors) {
            Country l_neighbor = getCountryById(p_map, l_neighborId);
            if (l_neighbor != null && getCountryOwner(l_neighbor, Collections.singletonList(p_player)).equals(p_player)) {
                int totalArmies = l_strongestCountry.getD_armyCount() + l_neighbor.getD_armyCount();
                l_targetCountryName =l_neighbor.getD_name();
                return CommandParser.parse("move " + l_strongestCountry.getD_name() + " " + l_targetCountryName+ " "+ totalArmies)
                        .get(0);
            }
        }
        // Placeholder for handling the case where no move is made
        return new Command("commit");

    }

    private Country findStrongestCountry(List<Country> p_countries) {
        return Collections.max(p_countries, Comparator.comparingInt(Country::getD_armyCount));
    }
}
