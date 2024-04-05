package strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.*;

import static game.map.MapHelper.getCountryById;

public  class Aggressive extends PlayerStrategy {

    private boolean Deployed;
    private boolean Attacked;
    private boolean Moved;

    private static Aggressive aggressiveStrategy;

    public static Aggressive getAggressiveStrategy() {
        if (aggressiveStrategy == null) {
            aggressiveStrategy = new Aggressive() {};
        }
        return aggressiveStrategy;
    }

    /** Private constructor to make sure that the client calls getAggressiveStrategy() */
    private Aggressive() {
        Deployed = false;
        Attacked = false;
        Moved = false;
    }

    @Override
    public Command createOrder(Map p_map, Player p_player) {
        if (!Deployed) {
            deployStrongestCountry(p_player);
            Deployed = true;
            return new Command("deploy");
        } else if (!Attacked) {
            attackWithStrongestCountry(p_map, p_player);
            Attacked = true;
            return new Command("attack");
        } else if (!Moved) {
            moveArmies(p_map, p_player);
            Moved = true;
            return new Command("move");
        } else {
            // If all actions are completed, reset for the next turn
            Deployed = false;
            Attacked = false;
            Moved = false;
            // No specific command, as turn is completed
            return new Command("end_turn");
        }

        }

        public void deployStrongestCountry(Player p_player) {
            Country strongestCountry = findStrongestCountry(p_player.getD_countries());
            int reinforcements = p_player.getD_reinforcements();
            strongestCountry.setD_armyCount(strongestCountry.getD_armyCount() + reinforcements);
            p_player.setD_reinforcements(0);
        }

    public void attackWithStrongestCountry(Map p_map,Player p_player ) {
        Country strongestCountry = findStrongestCountry(p_player.getD_countries());
        Set<Integer> neighborIds = strongestCountry.getD_neighborIdList();

        for (Integer neighborId : neighborIds) {
            Country neighbor = getCountryById(p_map, neighborId);
            if (neighbor != null && strongestCountry.getD_armyCount() > neighbor.getD_armyCount()) {
                int armiesToAttackWith = strongestCountry.getD_armyCount() - neighbor.getD_armyCount();
                neighbor.setD_armyCount(0); // Attack and conquer neighbor
                strongestCountry.setD_armyCount(armiesToAttackWith);
                break; // Stop after one successful attack
            }
        }

    }

    public void moveArmies(Map p_map,Player p_player) {
        Country strongestCountry = findStrongestCountry(p_player.getD_countries());
        List<Integer> neighbors = new ArrayList<>(strongestCountry.getD_neighborIdList());
        for (int neighborId : neighbors) {
            Country neighbor = getCountryById(p_map, neighborId);
            if (neighbor != null && neighbor.getD_continent().equals(p_player)) {
                int totalArmies = strongestCountry.getD_armyCount() + neighbor.getD_armyCount();
                neighbor.setD_armyCount(totalArmies);
                strongestCountry.setD_armyCount(0);
                break; // Move armies to the first neighboring country in the same continent
            }
        }
    }



    private Country findStrongestCountry(List<Country> countries) {
            return Collections.max(countries, Comparator.comparingInt(Country::getD_armyCount));
        }
}



